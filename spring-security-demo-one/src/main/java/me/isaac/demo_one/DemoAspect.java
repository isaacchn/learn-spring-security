package me.isaac.demo_one;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

@Aspect
@Component
@Slf4j
public class DemoAspect {
    @Pointcut("execution(* org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter.*(..)) " +
            "|| execution(* org.springframework.security.web.access.intercept.FilterSecurityInterceptor.*(..)) " +
            "|| execution(* org.springframework.security.web.access.ExceptionTranslationFilter.*(..))")
    //@Pointcut("execution(public * me.isaac.demo_one.controller.*.*(..))*")
    //@Pointcut("execution(public * org.springframework.security.web.authentication.*.*(..))*")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        log.info("========== Start ==========");
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (!Objects.isNull(servletRequestAttributes)) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            log.info("URL: " + request.getRequestURL().toString());
        }

        //log.info("HTTP_METHOD: " + request.getMethod());
        log.info("CLASS: " + joinPoint.getTarget().getClass());

        if (joinPoint.getSignature() instanceof MethodSignature) {
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            log.info("METHOD: " + method.getDeclaringClass() + "." + method.getName());
        }
//
//        log.info("METHOD: " + ((MethodSignature) joinPoint.getSignature()).getMethod()getName());
//        Class[] parameterTypes = ((CodeSignature) joinPoint.getSignature()).getParameterTypes();
//        String classes = Arrays.stream(parameterTypes).map(Object::toString).collect(Collectors.joining(","));
//        log.info("CLASS: " + classes);
//        log.info("METHOD: " + ((MethodSignature) joinPoint.getSignature()).getMethod().getName());
    }

    @After("webLog()")
    public void doAfter() throws Exception {
        log.info("========== End ==========");
    }

//    @Around("webLog()")
//    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        long startTime = System.currentTimeMillis();
//        Object result = proceedingJoinPoint.proceed();
//        //String resultStr = JSON.toJSONString(result);
//        String resultStr = result.toString();
//        // 打印出参
//        log.info("RESPONSE ARGS  : {}", resultStr);
//        // 执行耗时
//        log.info("TIME-CONSUMING : {} ms", System.currentTimeMillis() - startTime);
//        return result;
//    }

}
