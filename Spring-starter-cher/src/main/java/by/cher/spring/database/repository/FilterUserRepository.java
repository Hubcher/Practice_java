package by.cher.spring.database.repository;

import by.cher.spring.database.entity.User;
import by.cher.spring.dto.UserFilter;

import java.util.List;

public interface FilterUserRepository {
    List<User> findAllByFilter(UserFilter filter);
}
