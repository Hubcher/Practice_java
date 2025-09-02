package by.cher.spring.database.repository;

import by.cher.spring.database.entity.Role;
import by.cher.spring.database.entity.User;
import by.cher.spring.dto.IPersonalInfo;
import by.cher.spring.dto.PersonalInfo;
import by.cher.spring.dto.UserFilter;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long>,
        FilterUserRepository, QuerydslPredicateExecutor<User> {

    Page<User> findAllBy(Pageable pageable);

    Optional<User> findFirstByCompanyIsNotNullOrderByIdDesc();

    @Query(value = "select firstname, lastname, birth_date from users " +
            "where company_id = :companyId",
            nativeQuery = true)
    List <IPersonalInfo> findAllByCompanyId(Integer companyId);

//    Page<User> findAllBy(Pageable pageable);
    List<User> findFirst3By(Sort sort);

    @Query("select u from User u " +
            "where u.firstname like %:firstname% " +
            "and u.lastname like %:lastname%")
    List<User> findByFirstnameContainingAndLastnameContaining(String firstname, String lastname);

    @NativeQuery("select * from users where username = :username")
    List<User> findAllByUsername(String username);

    @Modifying(clearAutomatically = true)
    @Query("update User u set u.role = :role " +
            "where u.id in (:ids)")
    int updateRole(Role role, long... ids);


}
