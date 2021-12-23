package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author S'phokuhle on 11/9/2021
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TemporalTestDto {
    private Long studentId;
    private Date registrationDate;
}
