package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "SUBJECT", schema="APP")
public class Subject {

    @Id
    @SequenceGenerator(name="SUBJECT_SUBJECT_ID_GENERATOR", sequenceName="SUBJECT_SEQ", schema = "APP", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SUBJECT_SUBJECT_ID_GENERATOR")
    @Column(name = "SUBJECT_ID")
    private long subjectId;

    @Column(name = "SUBJECT_NAME")
    private String subjectName;

    @Column(name = "SUBJECT_CODE")
    private String subjectCode;

    @Column(name = "STREAM_ID")
    private Long streamId;
}
