package by.cher.integration.service;


import by.cher.annotation.IT;
import by.cher.spring.database.entity.Role;
import by.cher.spring.dto.UserCreateEditDto;
import by.cher.spring.dto.UserReadDto;
import by.cher.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@IT
@SpringBootTest
@RequiredArgsConstructor
public class UserServiceIT {

    private final UserService userService;

    private final static Long USER_1 = 1L;
    private final static Integer COMPANY_1 = 1;

    @Test
    void update() {
        UserCreateEditDto userDto = new UserCreateEditDto(
                "test@gmail.com",
                LocalDate.now(),
                "Test",
                "Test",
                Role.ADMIN,
                COMPANY_1
        );

        Optional<UserReadDto> actualResult = userService.update(USER_1, userDto);

        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(userReadDto -> {
            assertEquals(userDto.getUsername(), userReadDto.getUsername());
            assertEquals(userDto.getBirthDate(), userReadDto.getBirthDate());
            assertEquals(userDto.getFirstname(), userReadDto.getFirstname());
            assertEquals(userDto.getLastname(), userReadDto.getLastname());
            assertEquals(userDto.getCompanyId(), userReadDto.getCompany().id());
            assertSame(userDto.getRole(), userReadDto.getRole());
        });

    }

    @Test
    void create() {
        UserCreateEditDto userDto = new UserCreateEditDto(
                "test@gmail.com",
                LocalDate.now(),
                "Test",
                "Test",
                Role.ADMIN,
                COMPANY_1
        );
        UserReadDto actualResult = userService.create(userDto);

        assertEquals(userDto.getUsername(), actualResult.getUsername());
        assertEquals(userDto.getBirthDate(), actualResult.getBirthDate());
        assertEquals(userDto.getFirstname(), actualResult.getFirstname());
        assertEquals(userDto.getLastname(), actualResult.getLastname());
        assertEquals(userDto.getCompanyId(), actualResult.getCompany().id());
        assertSame(userDto.getRole(), actualResult.getRole());

    }

    @Test
    void delete() {
        assertFalse(userService.delete(-124L));
        assertTrue(userService.delete(USER_1));
    }

    @Test
    void findAll() {
        List<UserReadDto> result = userService.findAll();
        assertThat(result).hasSize(5);

    }

    @Test
    void findById() {
        Optional<UserReadDto> maybeUser = userService.findById(USER_1);
        assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(user->assertEquals("ivan@gmail.com", user.getUsername()));
    }
}
