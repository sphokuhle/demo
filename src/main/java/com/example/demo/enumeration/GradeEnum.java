package com.example.demo.enumeration;

public enum GradeEnum {
    GRADE_8("Grade 8"), GRADE_9("Grade 9");
    private final String description;
    GradeEnum(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
