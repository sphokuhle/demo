package com.example.demo.dto;

import com.example.demo.entity.Grade;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GradeDto {

    private long gradeId;

    private String name;

    private String code;

    public GradeDto(Grade grade) {
        if(grade != null) {
            this.gradeId = grade.getGradeId();
            this.name = grade.getName();
            this.code = grade.getCode();
        }

    }
}
