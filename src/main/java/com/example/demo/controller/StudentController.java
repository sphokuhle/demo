package com.example.demo.controller;


import com.example.demo.dto.StudentDto;
import com.example.demo.dto.TemporalTestDto;
import com.example.demo.entity.Grade;
import com.example.demo.entity.QStudent;
import com.example.demo.entity.Student;
import com.example.demo.entity.StudentMark;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.repository.GradeRepository;
import com.example.demo.repository.StudentMarkRepository;
import com.example.demo.repository.StudentRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.util.ArrayIterator;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@RestController
@RequestMapping("/student/")
@Slf4j
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    GradeRepository gradeRepository;

    @Autowired
    StudentMarkRepository studentMarkRepository;

    @GetMapping("all")
    @PreAuthorize("hasAuthority('student_authorization')")
    @Transactional
    public List<StudentDto> getAllStudents() {
        return studentRepository.getAllStudents();
    }

    @GetMapping("paging")
    @PreAuthorize("hasAuthority('student_authorization')")
    public Iterable<Student> getPaged(@RequestParam(name = "page", defaultValue = "1") int page,
                                      @RequestParam(name = "size", defaultValue = "10") int limit) {
        BooleanExpression expression = Expressions.asBoolean(true).isTrue();
        QStudent qStudent = QStudent.student;
        Page<Student> pages = studentRepository.findAll(expression, PageRequest.of(page - 1, limit, Sort.by(Sort.Order.desc("active"), Sort.Order.asc("studentId"))));
        return studentRepository.findAll(expression, PageRequest.of(page - 1, limit, Sort.by(Sort.Order.desc("active"), Sort.Order.asc("studentId"))));
    }

    //findByRegistrationDateFiltering
    @GetMapping("by/dates")
    @PreAuthorize("hasAnyAuthority('student_authorization', 'teacher_authorization')")
    public Map<String, BigInteger> getByDates() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2021, 04, 10);

        Calendar clndr = Calendar.getInstance();
        clndr.set(2021, 05, 04, 19, 45, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date from = sdf.parse(sdf.format(calendar.getTime()));
        Date to = new Date(sdf.parse(sdf.format(clndr.getTime())).getTime() + TimeUnit.HOURS.toMillis(23)+ TimeUnit.MINUTES.toMillis(59) + TimeUnit.SECONDS.toMillis(59));
        log.info("from: [{}] to [{}]", new Object[]{from,to});

        return studentRepository.findByRegistrationDateFiltering(from, to);
    }

    @GetMapping("paging/filter")
    @PreAuthorize("hasAuthority('student_authorization')")
    public Iterable<Student> getPagedFilter(@RequestParam(name = "page", defaultValue = "1") int page,
                                      @RequestParam(name = "size", defaultValue = "10") int limit, @RequestBody StudentDto studentDto) throws JsonProcessingException {
        BooleanExpression expression = Expressions.asBoolean(true).isTrue();
        QStudent qStudent = QStudent.student;
        if(studentDto.getUsername() != null && !studentDto.getUsername().trim().isEmpty()) {
            expression = expression.and(qStudent.user.username.equalsIgnoreCase(studentDto.getUsername()));
        }

        if(studentDto.getGrade() != null && studentDto.getGrade().getGradeId() > 0){
            expression = expression.and(qStudent.grade.gradeId.eq(studentDto.getGrade().getGradeId()));
        }
        Page<Student> pages = studentRepository.findAll(expression, PageRequest.of(page - 1, limit, Sort.by(Sort.Order.desc("active"), Sort.Order.asc("studentId"))));
        if(!pages.hasContent()) {
            return emptyPage();
        }
        return pages;
    }

    @GetMapping("findAllRegistrationDates")
    public List<Object[]> getPaged() {
        return studentRepository.findAllRegistrationDates();
    }

    @GetMapping("getAllRegistrationDates")
    public List<TemporalTestDto> findAllRegistrationDatesDto() {
        return studentRepository.findAllRegistrationDatesDto();
    }

    private Page<Student> emptyPage() {
        return new Page<Student>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super Student, ? extends U> function) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }
            @JsonIgnore
            @Override
            public int getSize() {
                return 10;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }
            @JsonInclude(JsonInclude.Include.ALWAYS)
            @Override
            public List<Student> getContent() {
                return new ArrayList<>();
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return Sort.by("studentId");
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<Student> iterator() {
                return new ArrayIterator<>(getContent().toArray(new Student[0]));
            }
        };
    }

    @PostMapping("register")
    @PreAuthorize("hasAnyAuthority('student_authorization', 'teacher_authorization')")
    public StudentDto registerStudent(@RequestBody StudentDto dto) {
        Optional<Grade> grade = gradeRepository.findById(dto.getGrade().getGradeId());
        StudentMapper studentMapper = new StudentMapper(dto);
        if(studentMapper.getStudent() != null && grade != null && grade.get() != null) {
            StudentMark studentMark = new StudentMark();
            Student student = studentMapper.getStudent();
            student.setGrade(grade.get());
            studentRepository.save(student);
            return new StudentDto(student);
        }
        return null;
    }

    @PostMapping("deregister/{studentId}")
    @PreAuthorize("hasAuthority('teacher_authorization')")
    public StudentDto deregisterStudent(@PathVariable("studentId") long studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        StudentMapper studentMapper = null;
        if(optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            if(student != null) {
                student.setActive(false);
                studentRepository.save(student);
                studentMapper = new StudentMapper(student);
                return studentMapper.getStudentDto();
            }
        }
        return null;
    }

    @GetMapping("findById/{studentId}")
    @PreAuthorize("hasAnyAuthority('student_authorization', 'teacher_authorization')")
    public StudentDto getStudent(@PathVariable("studentId") long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        StudentMapper studentMapper = null;
        if(student != null && student.get() != null) {
            studentMapper = new StudentMapper(student.get());
            return studentMapper.getStudentDto();
        }
        return null;
    }

    @GetMapping("findByIdAndNameAndSurnameAndActive")
    @PreAuthorize("hasAnyAuthority('student_authorization', 'teacher_authorization')")
    public StudentDto getStudentByIdAndNameAndSurnameAndActive() {
        Student student = studentRepository.findFirstByStudentIdAndActive(40L,true);
        StudentMapper studentMapper = null;
        if(student != null) {
            studentMapper = new StudentMapper(student);
            return studentMapper.getStudentDto();
        }
        return null;
    }


}
