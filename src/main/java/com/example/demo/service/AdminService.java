package com.example.demo.service;

import com.example.demo.dto.StudentDto;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Service
public class AdminService {
    @Value("${keycloak.auth-server-url}")
    private String KEYCLOAK_URL;

    @Value("${keycloak.realm}")
    private String REALM;

    @Value("${keycloak.resource}")
    private String CLIENT_ID;

    @Value("${keycloak.credentials.secret}")
    private String SECRET;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    GradeRepository gradeRepository;

    @Autowired
    StudentRepository studentRepository;

    @Transactional
    public void registerUser(String token, StudentDto studentDto) throws Exception {
        token = token.replaceFirst("Bearer ","");
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(KEYCLOAK_URL)
                .realm(REALM)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(CLIENT_ID)
                .clientSecret(SECRET)
                .authorization(token)
                .build();

        if(checkMandatoryFields(studentDto)) {
            // Define user
            UserRepresentation user = new UserRepresentation();
            user.setEnabled(true);
            user.setUsername(studentDto.getUsername());
            user.setFirstName(studentDto.getName());
            user.setLastName(studentDto.getSurname());
            user.setEmail(studentDto.getEmail());
            //user.setAttributes(Collections.singletonMap("origin", Arrays.asList("demo")));
            user.setRealmRoles(Arrays.asList("student_authorization")); //"offline_access",

            Role role = addUserToDB(studentDto);

            // Get realm
            RealmResource realmResource = keycloak.realm(REALM);
            UsersResource usersResource = realmResource.users();

            // Create user (requires manage-users role)
            Response response = usersResource.create(user);
            System.out.printf("Repsonse: %s %s%n", response.getStatus(), response.getStatusInfo());
            System.out.println(response.getLocation());
            System.out.println(response);
            String userId = CreatedResponseUtil.getCreatedId(response);

            System.out.printf("User created with userId: %s%n", userId);

            // Define password credential
            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setTemporary(false);
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue(studentDto.getPassword());

            UserResource userResource = usersResource.get(userId);

            // Set password credential
            userResource.resetPassword(passwordCred);

//        // Get realm role "student_authorization" (requires view-realm role)
            RoleRepresentation newUserRealmRole = realmResource.roles()//
                    .get(role.getRoleName()).toRepresentation();

        // Assign realm role student_authorization to user
            userResource.roles().realmLevel() //
                    .add(Arrays.asList(newUserRealmRole));

            //We don't need assign client level roles for users with role student_authorization

//        // Get client
//            ClientRepresentation clientRepresentation = realmResource.clients() //
//                    .findByClientId(CLIENT_ID).get(0);
//
//        // Get client level role (requires view-clients role)
//            RoleRepresentation userClientRole = realmResource.clients().get(clientRepresentation.getId()) //
//                    .roles().get(role.getRoleName()).toRepresentation();
//
//        // Assign client level role to user
//            userResource.roles() //
//                    .clientLevel(clientRepresentation.getId()).add(Arrays.asList(userClientRole));
        }
    }

    private boolean checkMandatoryFields(StudentDto student) throws Exception {

        boolean status = student != null && student.getUsername() != null && !student.getUsername().trim().equalsIgnoreCase("")
                && student.getName() != null && !student.getName().trim().equalsIgnoreCase("")
                && student.getSurname() != null && !student.getSurname().trim().equalsIgnoreCase("");

        if(status) {
            Optional<User> user = userRepository.findByUsername(student.getUsername());
            if(user.isPresent()) throw new IllegalArgumentException("User "+ student.getUsername() + " already exist");
            else
                return true;
        }
        throw new BadRequestException("Invalid data found");
    }

    private Role addUserToDB(StudentDto studentDto) {
        User dbUser = new User();
        dbUser.setFirstName(studentDto.getName());
        dbUser.setLastName(studentDto.getSurname());
        dbUser.setUsername(studentDto.getUsername());
        dbUser.setPassword(null);
        dbUser.setEnabled(true);
        dbUser.setPhoneNumber(studentDto.getPhoneNumber());
        dbUser.setEmail(studentDto.getEmail());
        userRepository.save(dbUser);

        UserRole userRole = new UserRole();
        Role role = roleRepository.findById(3L).get();
        userRole.setRole(role);
        userRole.setUser(dbUser);
        userRoleRepository.save(userRole);

        Student student = new Student();
        Grade grade = gradeRepository.findById(studentDto.getGrade().getGradeId()).get();
        student.setUser(dbUser);
        student.setGrade(grade);
        student.setActive(true);
        student.setRegistrationDate(new Date());
        studentRepository.save(student);
        return role;
    }
}
