package cn.stylefeng.roses.core.aop;

import cn.stylefeng.roses.core.context.RequestDataHolder;
import cn.stylefeng.roses.core.reqres.request.RequestData;
import org.aspectj.lang.ProceedingJoinPoint;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static junit.framework.TestCase.assertSame;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.*;

public class RequestDataAopTest {

    @Test
    public void testNoArgNoReturn() throws Throwable {
        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        when(joinPoint.getArgs()).thenReturn(new Object[0]);
        when(joinPoint.proceed()).thenReturn(null);

        RequestDataAop rda = new RequestDataAop();
        Object result = rda.sessionKit(joinPoint);

        assertThat(result).isNull();
        assertThat(RequestDataHolder.get()).isNull();
    }

    @Test
    public void testRequestDataArg() throws Throwable {
        RequestData requestData = new RequestData();
        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        when(joinPoint.getArgs()).thenReturn(new Object[] {requestData});

        when(joinPoint.proceed()).then(invocationOnMock -> {
            assertSame(requestData, RequestDataHolder.get());  // 确认参数放到 RequestDataHolder 里面
            return null;
        });

        RequestDataAop rda = new RequestDataAop();
        Object result = rda.sessionKit(joinPoint);

        assertThat(result).isNull();
        assertThat(RequestDataHolder.get()).isNull(); // 确认参数清除了
    }

    @Test
    public void testException() throws Throwable {
        RequestData requestData = new RequestData();
        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        when(joinPoint.getArgs()).thenReturn(new Object[] {requestData});

        Exception exception = new Exception();
        when(joinPoint.proceed()).then(invocationOnMock -> {
            assertSame(requestData, RequestDataHolder.get());  // 确认参数放到 RequestDataHolder 里面
            throw exception;
        });

        RequestDataAop rda = new RequestDataAop();
        Object result = null;

        try {
            result = rda.sessionKit(joinPoint);
        } catch (Throwable throwable) {
            assertSame(throwable, exception);
        }

        assertThat(result).isNull();
        assertSame(requestData, RequestDataHolder.get()); // 确认参数没有清除
    }
}