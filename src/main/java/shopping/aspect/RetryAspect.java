package shopping.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import shopping.aspect.annotation.Retry;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;

@Aspect
@Component
public class RetryAspect {

    private static final Logger log = LoggerFactory.getLogger(RetryAspect.class);

    @Around("@annotation(retry)")
    public Object execute(final ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        for (int i = 0; i < retry.maxAttempts(); i++) {
            try {
                return joinPoint.proceed();
            } catch (ShoppingException e) {
                log.warn("{} 번 호출에 실패했습니다.", i + 1, e);
                interval(retry.intervalSecond());
            }
        }
        throw new ShoppingException(ErrorCode.API_RETRY_FAIL);
    }

    private void interval(int intervalTime) throws InterruptedException {
        Thread.sleep(intervalTime * 1000L);
    }
}

