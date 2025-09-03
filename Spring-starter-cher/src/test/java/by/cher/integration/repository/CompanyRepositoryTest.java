package by.cher.integration.repository;

import by.cher.annotation.IT;
import by.cher.spring.database.entity.Company;
import by.cher.spring.database.entity.User;
import by.cher.spring.database.repository.CompanyRepository;
import by.cher.spring.database.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@IT

public class CompanyRepositoryTest {

    private static final Integer APPLE_ID = 5;

    @Autowired
    private  EntityManager entityManager;
    
    @Autowired
    private  CompanyRepository companyRepository;


    @Test
    void checkFindByQueries() {
        companyRepository.findByName("Google");
//        var listCompany = companyRepository.findAllByNameContainingIgnoreCase(FRAGMENT);
//        assertThat(listCompany).hasSize(3);
    }

    @Test
    void delete() {
        var maybeCompany = companyRepository.findById(APPLE_ID);
        assertTrue(maybeCompany.isPresent());
        maybeCompany.ifPresent(companyRepository::delete);
        entityManager.flush();
        assertTrue(companyRepository.findById(APPLE_ID).isEmpty());
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
     void findById() {
        var company = entityManager.find(Company.class, 1);
        assertNotNull(company);
        assertThat(company.getLocales()).hasSize(2);
    }

    @Test
    void save() {
        var company = Company.builder()
                .name("Apple1")
                .locales(Map.of(
                        "ru", "Apple описание",
                        "en", "Apple description"
                ))
                .build();
        entityManager.persist(company);
        assertNotNull(company.getId());
    }

}
