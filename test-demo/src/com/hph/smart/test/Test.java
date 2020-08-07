package com.hph.smart.test;

import java.lang.reflect.Proxy;

/**
 * @author hph
 */
public class Test {

    public static void main(String[] args) {

        User user = new User("hph", 18);

        ProxyHandler handler = new ProxyHandler(user);

        User proxyUser = (User)Proxy
                .newProxyInstance(user.getClass().getClassLoader(), user.getClass().getInterfaces(), handler);

        System.out.println(proxyUser.getAge());
    }
}
