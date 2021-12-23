package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "STUDENT_MARK", schema="APP")
public class StudentMark {
    @Id
    @SequenceGenerator(name="STUDENT_MARK_STUDENT_MARK_ID_GENERATOR", sequenceName="STUDENT_MARK_SEQ", schema = "APP", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="STUDENT_MARK_STUDENT_MARK_ID_GENERATOR")
    @Column(name = "STUDENT_MARK_ID")
    private long studentMarkId;

    @ManyToOne
    @JoinColumn(name = "SUBJECT_ID")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "STUDENT_ID")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "MARK_ID")
    private Mark mark;
}
