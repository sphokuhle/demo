package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Mark", schema="APP")
public class Mark {

    @Id
    @SequenceGenerator(name="MARK_MARK_ID_GENERATOR", sequenceName="MARK_SEQ", schema = "APP", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MARK_MARK_ID_GENERATOR")
    @Column(name = "MARK_ID")
    private long streamId;

    @Column(name = "TOTAL_MARK")
    private BigDecimal totalMark;

    @Column(name = "STUDENT_MARK")
    private BigDecimal studentMark;

    @Column(name = "ASSESSMENT_TYPE")
    private String assessmentType;

    @Column(name = "ASSESSMENT_NAME")
    private String assessmentName;

}
