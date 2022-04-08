package ru.kirillvenediktov.springbootuniversity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentDTO {

    private Long id;
    private Long groupId;
    private String firstName;
    private String lastName;

}
