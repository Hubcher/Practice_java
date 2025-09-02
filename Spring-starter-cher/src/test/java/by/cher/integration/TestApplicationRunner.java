package by.cher.integration;


import by.cher.spring.database.repository.pool.ConnectionPool;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class TestApplicationRunner {
//    @SpyBean(name = "connectionPool2")
    private ConnectionPool pool1;
}
