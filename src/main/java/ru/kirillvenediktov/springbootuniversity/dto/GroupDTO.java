package ru.kirillvenediktov.springbootuniversity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GroupDTO {

    private Long id;
    @NotEmpty(message = "Group name should not empty")
    @Size(min = 2, max = 30, message = "Group name should be between 2 and 30 characters")
    private String groupName;

}
