package by.cher.spring.mapper;


import by.cher.spring.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@ToString
public class UserMapper {
    @Autowired
    private  UserDto userDto;
}
