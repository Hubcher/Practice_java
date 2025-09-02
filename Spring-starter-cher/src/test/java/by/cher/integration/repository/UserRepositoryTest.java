package by.cher.integration.repository;

import by.cher.annotation.IT;
import by.cher.spring.database.entity.Role;
import by.cher.spring.database.entity.User;
import by.cher.spring.database.repository.UserRepository;
import by.cher.spring.dto.PersonalInfo;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
public class UserRepositoryTest {
    private final UserRepository userRepository;

    @Test
    void checkPageable() {
        var pageable = PageRequest.of(1, 2, Sort.by("id"));
        var slice = userRepository.findAllBy(pageable);
        slice.forEach(user -> System.out.println(user.getId()));
        while (slice.hasNext()) {
            slice = userRepository.findAllBy(slice.nextPageable());
            slice.forEach(user -> System.out.println(user.getId()));
            System.out.println(slice.getTotalPages());
        }
    }

    @Test
    void findFirst3By() {
        var users = userRepository.findFirst3By(Sort.by("firstname")
                .and(Sort.by("lastname")).descending());
        assertTrue(!users.isEmpty());
        assertThat(users).hasSize(3);
    }


    @Test
    void checkProjections() {
        var users = userRepository.findAllByCompanyId(1);
        assertThat(users).hasSize(2);
    }

    @Test
    void updateRoleTest() {
        var entity1 = userRepository.findById(1L);
        assertEquals(Role.ADMIN, entity1.get().getRole());
        int answer = userRepository.updateRole(Role.ADMIN, 1L, 5L);
        assertEquals(2, answer);
        var entity2 = userRepository.findById(1L);
        assertEquals(Role.USER, entity2.get().getRole());
    }

    @Test
    void findAllByFirstnameContainingAndLastnameContainingTest() {
        var answer = userRepository.findByFirstnameContainingAndLastnameContaining("a", "a");
        assertFalse(answer.isEmpty());
        assertThat(answer).hasSize(3);
    }
}
