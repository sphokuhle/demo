package com.example.demo.dto;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubjectDto {

    private long subjectId;

    private String subjectName;

    private String subjectCode;

    private StreamDto stream;
}
