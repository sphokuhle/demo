package com.example.demo.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MarkDto {

    private long streamId;

    private BigDecimal totalMark;

    private BigDecimal studentMark;

    private String assessmentType;

    private String assessmentName;

}
