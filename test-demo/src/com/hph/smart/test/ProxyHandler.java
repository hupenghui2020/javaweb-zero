package com.hph.smart.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * JDK
 * @author hph
 */
public class ProxyHandler implements InvocationHandler {

    private Object target;

    public ProxyHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        return method.invoke(proxy, args);
    }
}
