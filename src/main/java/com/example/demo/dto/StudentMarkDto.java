package com.example.demo.dto;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentMarkDto {

    private long studentMarkId;

    private SubjectDto subjectDto;

    private StudentDto student;

    private MarkDto mark;
}
