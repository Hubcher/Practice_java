package by.cher.spring.service;

import by.cher.spring.database.repository.CompanyRepository;
import by.cher.spring.dto.CompanyReadDto;
import by.cher.spring.listener.AccessType;
import by.cher.spring.listener.EntityEvent;
import by.cher.spring.mapper.CompanyReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyReadMapper companyReadMapper;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final CompanyRepository companyRepository;

    public List<CompanyReadDto> findAll() {
        return companyRepository.findAll().stream()
                .map(companyReadMapper::map)
                .toList();
    }

    public Optional<CompanyReadDto> findById(Integer id) {
        return companyRepository.findById(id).map(entity -> {
            applicationEventPublisher.publishEvent(new EntityEvent(entity, AccessType.READ));
            return new CompanyReadDto(entity.getId(), null);
        });
    }
}
