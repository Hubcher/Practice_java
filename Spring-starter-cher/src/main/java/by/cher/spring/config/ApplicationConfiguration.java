package by.cher.spring.config;


import by.cher.spring.database.repository.pool.ConnectionPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;

@Configuration
public class ApplicationConfiguration {

    @Bean("connectionPool1")
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public ConnectionPool connectionPool(@Value("${db.username}") String username) {
        return new ConnectionPool(username, "123", 20,"url");
    }

    @Bean
    public ConnectionPool connectionPool2() {
        return new ConnectionPool("mySql", "123", 20,"url");
    }

}
