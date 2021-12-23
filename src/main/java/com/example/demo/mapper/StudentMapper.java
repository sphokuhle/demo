package com.example.demo.mapper;

import com.example.demo.dto.GradeDto;
import com.example.demo.dto.StudentDto;
import com.example.demo.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.datetime.DateFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@NoArgsConstructor
public class StudentMapper {

    @Value("${spring.security.user.password}")
    private static String DEFAULT_PASSWORD;

    private StudentDto studentDto;
    private Student student;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    public StudentMapper(Student _student){
        studentDto = new StudentDto();
        studentDto.setStudentId(_student.getStudentId());
        studentDto.setGrade(new GradeDto(_student.getGrade()));
        studentDto.setName(_student.getUser().getFirstName());
        studentDto.setSurname(_student.getUser().getLastName());
        studentDto.setActive(_student.getActive());
        studentDto.setRegistrationDate(_student.getRegistrationDate());
        studentDto.setUsername(_student.getUser().getUsername());
        studentDto.setEmail(_student.getUser().getEmail());
        studentDto.setActive(_student.getUser().isEnabled());
        studentDto.setPhoneNumber(_student.getUser().getPhoneNumber());
    }

    public StudentMapper(StudentDto _student) {
        student = new Student();
        if(_student.getStudentId() != 0){
            student.setStudentId(_student.getStudentId());
        }
        student.setGrade(new Grade(_student.getGrade()));
        student.setActive(_student.isActive());
        student.setRegistrationDate(new Date());
        User user = new User();
        user.setUsername(_student.getUsername());
        Set<UserRole> authorities = new HashSet<>();
        user.setAuthorities(authorities);
        user.setEmail(_student.getEmail());
        user.setEnabled(true);
        user.setPhoneNumber(_student.getPhoneNumber());
        if(_student.getUsername() != null && !_student.getUsername().trim().isEmpty()){
            user.setUsername(_student.getUsername());
        }
        if(_student.getPassword() != null && _student.getPassword().trim().length() > 5) {
            user.setPassword(_student.getPassword());
        } else {
            user.setPassword(DEFAULT_PASSWORD);
        }
        student.setUser(user);

    }

    private Date getEnquiryDateTimestamp(String enquiryDate) {
        enquiryDate = getWorkFlowProcessDateFormat(enquiryDate);

        Date currentDate = new Date();
        String strCurrentDate = sdf.format(currentDate);
        String timestamp = strCurrentDate.substring(strCurrentDate.lastIndexOf('T'));
        System.out.println("timestamp of currentDate: "+timestamp);
        String newDateStr = enquiryDate.concat(timestamp);
        System.out.println("newDateStr: "+newDateStr);
        try {
            sdf.setLenient(true);
            return sdf.parse(newDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getWorkFlowProcessDateFormat(String enquiryTimestamp) {
        return enquiryTimestamp.substring(0,enquiryTimestamp.lastIndexOf('T'));
    }

    private Date convertStringToDate(String value) {
        try {
            return (new SimpleDateFormat("yyyyMMdd")).parse(String.valueOf(value));
        } catch (ParseException exception) {
            exception.printStackTrace();
            return null;
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
