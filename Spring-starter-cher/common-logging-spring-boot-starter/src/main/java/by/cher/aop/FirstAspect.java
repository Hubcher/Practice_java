package by.cher.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Aspect
public class FirstAspect {

    // @ - с аннотацией
    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void isControllerLayer() {
    }

    // Без аннотации, лишь имя с постфиксом
    @Pointcut("within(by.cher.*.service.*Service)")
    public void isServiceLayer() {
    }

    /*
     this - анализирует Proxy class
     target - анализирует объект, который ссылается на класс (обращается к оригинальному объекту
     @Pointcut("target(org.springframework.stereotype.Repository)")
     */
    @Pointcut("this(org.springframework.stereotype.Repository)")
    public void isRepositoryLayer() {
    }

    @Pointcut("isControllerLayer() && @annotation(org.springframework.*.bind.annotation.GetMapping)")
    public void hasGetMapping(){
    }

    @Pointcut("isControllerLayer() && args(org.springframework.ui.Model,..)")
    public void hasModelArg(){
    }

    @Pointcut("isControllerLayer() && @args(by.cher.*.validator.UserInfo,..)")
    public void hasUserInfoParamAnnotation(){}

    @Pointcut("bean(userService)")
    public void isUserServiceBean() {

    }

    @Pointcut("bean(*Service)")
    public void isServiceLayerBean() {

    }

    @Pointcut("execution(public * by.cher.*.service.*Service.findById(*))")
    public void anyServiceFindByIdMethod() {}


    @Pointcut("execution(public * findById(*))")
    public void anyFindByIdMethod() {}

//     Advice

    @Before(value = "anyServiceFindByIdMethod() " +
            "&& args(id) " +
            "&& target(service) " +
            "&& this(serviceProxy) " +
            "&& @within(transactional)", argNames = "joinPoint,id,service,serviceProxy,transactional")
    public void addLogging(JoinPoint joinPoint,
                           Object id,
                           Object service,
                           Object serviceProxy,
                           Transactional transactional) {
        log.info("Before invoke findById method in class {}, with id {}", service, id);
    }

    @AfterReturning(value = "anyServiceFindByIdMethod() " +
            "&& target(service)",
            returning = "result", argNames = "result,service")
    public void addLoggingAfterReturning(Object result, Object service) {
        log.info("AfterReturning invoke findById method in class {}, with result {}", service, result);
    }

    @AfterThrowing(value = "anyServiceFindByIdMethod() " +
            "&& target(service)",
            throwing = "ex")
    public void addLoggingAfterThrowing(Throwable ex, Object service) {
        log.info("Throwing invoked findById method in class {}, with throwing {}", service, ex);
    }

    @After("anyServiceFindByIdMethod() && target(service)")
    public void addLoggingAfter(Object service) {
        log.info("After invoked findById method in class {}", service);
    }



}
