package by.cher.config;


import by.cher.aop.FirstAspect;
import by.cher.aop.SecondAspect;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;


@Slf4j
@Configuration
@EnableAspectJAutoProxy
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnClass(LoggingProperties.class)
@ConditionalOnProperty(prefix = "app.service.logging", name = "enabled", havingValue = "true")
public class LoggingAutoConfiguration {

    @Bean
    @Order(1)
    public FirstAspect firstAspect() {
        return new FirstAspect();
    }

    @Bean
    @Order(2)
    public SecondAspect SecondAspect() {
        return new SecondAspect();
    }

    @PostConstruct
    public void init() {
        log.info("LoggingAutoConfiguration initialized");
    }
}
