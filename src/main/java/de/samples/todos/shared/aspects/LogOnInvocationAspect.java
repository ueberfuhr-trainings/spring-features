package de.samples.todos.shared.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.function.Function;

@Aspect
@Component
public class LogOnInvocationAspect {

    @Around("@annotation(LogOnInvocation)")
    public Object logExecutionTimeForAnnotatedMethod(ProceedingJoinPoint call) throws Throwable {
        return logExecutionTime(
          call,
          m -> AnnotationUtils.findAnnotation(m, LogOnInvocation.class)
        );
    }

    @Around("@within(LogOnInvocation)")
    public Object logExecutionTimeForMethodOfAnnotatedClass(ProceedingJoinPoint call) throws Throwable {
        return logExecutionTime(
          call,
          m -> AnnotationUtils.findAnnotation(m.getDeclaringClass(), LogOnInvocation.class)
        );
    }

    private Object logExecutionTime(ProceedingJoinPoint call, Function<Method, LogOnInvocation> annotationFn)
      throws Throwable {
        if (call.getSignature() instanceof MethodSignature ms) {
            final var method = ms.getMethod();
            final var annotation = annotationFn.apply(method);
            if (null != annotation) {
                final var logger = LoggerFactory.getLogger(method.getDeclaringClass());
                logger
                  .atLevel(annotation.level())
                  .log(annotation.value());
            }
        }
        return call.proceed();
    }

}
