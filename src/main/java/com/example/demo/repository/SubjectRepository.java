package com.example.demo.repository;


import com.example.demo.entity.Subject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SubjectRepository extends PagingAndSortingRepository<Subject, Long>, QuerydslPredicateExecutor<Subject> {
    @Query(value = "Select s From Subject s")
    List<Subject> getAllSubjects();
}
