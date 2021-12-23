package com.example.demo.repository;

import com.example.demo.dto.StudentDto;
import com.example.demo.dto.TemporalTestDto;
import com.example.demo.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.LockModeType;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StudentRepository  extends PagingAndSortingRepository<Student, Long>, QuerydslPredicateExecutor<Student> {
    Student findFirstByStudentIdAndActive(long studentId, boolean active);
    @Query(value = "Select new com.example.demo.dto.StudentDto(s) From Student s Order By s.studentId")
    List<StudentDto> getAllStudents();

    @Query(value = "Select Count(s.student_id) as students from app.student s where s.registration_date >= ?1 and s.registration_date < ?2", nativeQuery = true)
    Map<String, BigInteger> findByRegistrationDateFiltering(Date from, Date to);

    @Query(value = "Select (s.studentId), s.registrationDate from Student s order by s.registrationDate asc")
    List<Object[]> findAllRegistrationDates();

    @Query(value = "Select new com.example.demo.dto.TemporalTestDto(s.studentId, s.registrationDate) from Student s order by s.registrationDate asc")
    List<TemporalTestDto> findAllRegistrationDatesDto();

   //Student findFirstByStartDateLessThanEqualAndEndDateGreaterThanEqual(Date endDate, Date startDate);
   //List<StudentDto> findBy

}
