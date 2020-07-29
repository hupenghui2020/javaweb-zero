package org.smart4j.framework.proxy;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.smart4j.framework.annotation.Aspect;

import java.lang.reflect.Method;

/**
 * 切面代理（用在类上）
 * @author 10499
 */
public abstract class AspectProxy implements Proxy{

    private static final Logger logger = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {

        Object result;
        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();
        begin();
        try {
            if(intercept(cls, method, params)) {
                before(cls, method, params);
                result = proxyChain.doProxyChain();
                after(cls, method, params, result);
            }else {
                result = proxyChain.doProxyChain();
            }
        }catch (Exception e) {
            logger.error("proxy failure", e);
            error(cls, method, params, e);
            throw e;
        }finally {
            end();
        }
        return result;
    }

    private void after(Class<?> cls, Method method, Object[] params, Object result) {

    }

    private void end() {

    }

    private void error(Class<?> cls, Method method, Object[] params, Exception e) {

    }

    private boolean intercept(Class<?> cls, Method method, Object[] params) {
        return true;
    }

    private void before(Class<?> cls, Method method, Object[] params) {

    }

    private void begin() {
    }
}
