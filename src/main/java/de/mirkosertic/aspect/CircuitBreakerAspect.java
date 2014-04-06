package de.mirkosertic.aspect;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.stereotype.Component;

@Aspect
@Component
public class CircuitBreakerAspect {

    @Around("@annotation(de.mirkosertic.CircuitBreaker)")
    public Object circuitBreakerAround(final ProceedingJoinPoint aJoinPoint) throws Throwable {
        String theShortName = aJoinPoint.getSignature().toShortString();
        HystrixCommand.Setter theSetter =
                HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(theShortName));
        theSetter = theSetter.andCommandKey(HystrixCommandKey.Factory.asKey(theShortName));
        HystrixCommand theCommand = new HystrixCommand(theSetter) {
            @Override
            protected Object run() throws Exception {
                try {
                    return aJoinPoint.proceed();
                } catch (Exception e) {
                    throw e;
                } catch (Throwable e) {
                    throw new Exception(e);
                }
            }
        };
        return theCommand.execute();
    }
}
