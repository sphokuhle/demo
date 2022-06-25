package com.example.demo.repository;

import com.example.demo.entity.Stream;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface StreamRepository  extends PagingAndSortingRepository<Stream, Long>, QuerydslPredicateExecutor<Stream>
       /* JpaRepository<Stream, Long>, JpaSpecificationExecutor<Stream>*/ {
    @Query(value = "Select s FROM Stream s")
    List<Stream> getAllStreams();

    @Query(value = "Select s.name, s.code FROM Stream s")
    List<Object[]> getNamesAndCodes();

    @Query(value = "Select Count(DISTINCT(s.stream_id)) AS STREAMS, SUM(case when s.stream_name ='MATHS & SCIENCE' then 1 else 0 end) AS SCIENCE, SUM(case when s.stream_name ='ALL' then 1 else 0 end) AS ALL_STREAMS, SUM(case when sbjct.subject_code = 'ACC' or sbjct.subject_code ='BUSMG' or sbjct.subject_code ='ECO'  then 1 else 0 end) AS COMMERCE_SUBJECTS FROM APP.STREAM s LEFT JOIN APP.SUBJECT sbjct ON s.stream_id = sbjct.stream_id", nativeQuery = true)
    List<Object[]> getResults();

    @Query(value = "Select Count(DISTINCT(s.stream_id)) AS STREAMS, SUM(case when s.stream_name ='MATHS & SCIENCE' then 1 else 0 end) AS SCIENCE, SUM(case when s.stream_name ='ALL' then 1 else 0 end) AS ALL_STREAMS, SUM(case when sbjct.subject_code = 'ACC' or sbjct.subject_code ='BUSMG' or sbjct.subject_code ='ECO'  then 1 else 0 end) AS COMMERCE_SUBJECTS FROM APP.STREAM s LEFT JOIN APP.SUBJECT sbjct ON s.stream_id = sbjct.stream_id", nativeQuery = true)
    Map<String, BigInteger> getMappedResults();

    @Query(value = "Select s FROM APP.Stream s fetch first row only", nativeQuery = true)
    Stream findLast();

}
