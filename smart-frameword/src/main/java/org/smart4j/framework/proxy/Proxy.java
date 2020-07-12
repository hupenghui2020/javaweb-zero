package org.smart4j.framework.proxy;

/**
 * 代理接口
 * @author 10499
 */
public interface Proxy {

    /**
     * 执行链式注解（将多个代理通过一条链子串起来，一个一个地去执行）
     * @param proxyChain
     * @return
     * @throws Throwable
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
