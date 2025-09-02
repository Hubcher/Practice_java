package by.cher.spring.dto;

import by.cher.spring.database.entity.Role;
import lombok.Value;

import java.time.LocalDate;

@Value
public class UserReadDto {
    Integer id;
    String username;
    LocalDate birthDate;
    String firstname;
    String lastname;
    Role role;
    String image;
    CompanyReadDto company;
}
