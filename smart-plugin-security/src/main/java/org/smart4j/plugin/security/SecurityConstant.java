package org.smart4j.plugin.security;

/**
 * @author hph
 */
public interface SecurityConstant {

    String REALMS_CUSTOM = "custom";

    String REALMS_JDBC = "jdbc";

    /**
     *
     */
    String REALMS = "smart.plugin.security.realms";

    /**
     * smart security 类名
     */
    String SMART_SECURITY = "smart.plugin.security.custom.class";

    /**
     * roles 查询 sql
     */
    String JDBC_ROLES_QUERY = "smart.plugin.security.jdbc.roles_query";

    /**
     * permissions 查询 sql
     */
    String JDBC_PERMISSIONS_QUERY = "smart.plugin.security.jdbc.permissions_query";

    /**
     * 是否激活缓存
     */
    String CACHEABLE = "";

    String JDBC_AUTHC_QUERY = "smart.plugin.security.jdbc.authc_query";
}
