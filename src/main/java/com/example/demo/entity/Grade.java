package com.example.demo.entity;

import com.example.demo.dto.GradeDto;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "GRADE", schema="APP")
public class Grade {

    @Id
    @Column(name = "GRADE_ID")
    private long gradeId;

    @Column(name = "GRADE_NAME")
    private String name;

    @Column(name = "GRADE_CODE")
    private String code;

    public Grade(GradeDto gradeDto){
        this.gradeId = gradeDto.getGradeId();
        this.name = gradeDto.getName();
        this.code = gradeDto.getCode();
    }
}
