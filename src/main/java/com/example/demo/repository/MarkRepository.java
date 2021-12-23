package com.example.demo.repository;


import com.example.demo.entity.Mark;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MarkRepository extends PagingAndSortingRepository<Mark, Long>, QuerydslPredicateExecutor<Mark>
        /*,QuerydslPredicateExecutor<Mark>, QuerydslBinderCustomizer<QMark>*/ {
    @Query(value = "Select m FROM Mark m")
    List<Mark> getAllMarks();
}
