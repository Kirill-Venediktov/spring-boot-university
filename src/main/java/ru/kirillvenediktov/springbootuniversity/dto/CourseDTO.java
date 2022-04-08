package ru.kirillvenediktov.springbootuniversity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseDTO {

    private Long id;
    private String courseName;
    private String courseDescription;

}
