package by.cher.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
public class  SecondAspect {
    @Around(value = "by.cher.aop.FirstAspect.anyServiceFindByIdMethod() && target(service) && args(id)", argNames = "joinPoint,id,service")
    public Object addLoggingAround(ProceedingJoinPoint joinPoint, Object id, Object service) throws Throwable {
        log.info("AROUND before - invoked findById method in class {}, with id {}", service, id);
        try {
            Object result = joinPoint.proceed();
            log.info("AROUND after returning - invoked findById method in class {}, result {}", service, result);
            return result;
        } catch (Throwable ex) {
            log.info("AROUND after throwing - invoked findById method in class {}, exception {}: {}", service, ex.getClass(), ex.getMessage());
            throw  ex;
        } finally {
            log.info("AROUND after (finally) - invoked findById method in class {}", service);
        }

    }
}
