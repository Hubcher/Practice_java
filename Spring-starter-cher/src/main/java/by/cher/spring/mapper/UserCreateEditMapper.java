package by.cher.spring.mapper;

import by.cher.spring.database.entity.Company;
import by.cher.spring.database.entity.User;
import by.cher.spring.database.repository.CompanyRepository;
import by.cher.spring.dto.UserCreateEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {

    private final CompanyRepository companyRepository;

    @Override
    public User map(UserCreateEditDto fromObject, User toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public User map(UserCreateEditDto object) {
        User user = new User();
        copy(object, user);
        return user;
    }

    private void copy(UserCreateEditDto fromObject, User user) {
        user.setUsername(fromObject.getUsername());
        user.setFirstname(fromObject.getFirstname());
        user.setLastname(fromObject.getLastname());
        user.setBirthDate(fromObject.getBirthDate());
        user.setRole(fromObject.getRole());
        user.setCompany(getCompany(fromObject.getCompanyId()));

        Optional.ofNullable(fromObject.getImage())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(image -> user.setImage(image.getOriginalFilename()));

    }

    private Company getCompany(Integer companyId) {
        return Optional.ofNullable(companyId)
                .flatMap(companyRepository::findById)
                .orElse(null);
    }
}
