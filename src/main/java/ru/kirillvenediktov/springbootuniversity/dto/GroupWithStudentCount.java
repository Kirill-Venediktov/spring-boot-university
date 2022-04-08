package ru.kirillvenediktov.springbootuniversity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GroupWithStudentCount {

    private Long id;
    private String groupName;
    private int studentCount;

}
