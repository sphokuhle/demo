package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@EqualsAndHashCode
@Table(name = "STUDENT", schema="APP")
public class Student {
    @Id
    @SequenceGenerator(name="STUDENT_STUDENT_ID_GENERATOR", sequenceName="STUDENT_SEQ", schema = "APP", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="STUDENT_STUDENT_ID_GENERATOR")
    @Column(name = "STUDENT_ID")
    private Long studentId;

    @Column(name = "ACTIVE")
    private Boolean active;

    @Column(name = "REGISTRATION_DATE")
    private Date registrationDate;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToOne
    @JoinColumn(name = "GRADE_ID")
    private Grade grade;

}
