package org.smart4j.plugin.security;

import org.apache.shiro.web.env.EnvironmentLoaderListener;
import javax.servlet.*;
import java.util.Set;

/**
 * Smart Security 插件
 * 因为集成 Shiro 需要在 web.xml 配置 监听器和过滤器，
 * 这里通过 servlet 3.0 的注册特性来省略 web.xml 文件的相关配置
 * 实现 ServletContainerInitializer 来支持 Web 应用初始化的时候注册
 * @author hph
 */
public class SmartSecurityPlugin implements ServletContainerInitializer {

    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {

        // 设置初始化参数
        ctx.setInitParameter("shiroConfigLocations", "classpath:smart-security.ini");

        // 注册 Listener
        ctx.addListener(EnvironmentLoaderListener.class);

        // 注册 Filter
        FilterRegistration.Dynamic smartSecurityFilter = ctx.
                addFilter("SmartSecurityFilter", SmartSecurityFilter.class);
        smartSecurityFilter.addMappingForUrlPatterns(null, false, "/*");
    }
}
