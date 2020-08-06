package org.smart4j.plugin.security;

import org.smart4j.framework.helper.ConfigHelper;
import org.smart4j.framework.util.ReflectionUtil;

/**
 * 从配置文件中获取相关属性
 * @author hph
 */
public final class SecurityConfig {

    public static String getJdbcAuthcQuery() {

        return ConfigHelper.getString(SecurityConstant.JDBC_AUTHC_QUERY);
    }

    public static String getJdbcRolesQuery() {

        return ConfigHelper.getString(SecurityConstant.JDBC_ROLES_QUERY);
    }

    public static String getJdbcPermissionsQuery() {

        return ConfigHelper.getString(SecurityConstant.JDBC_PERMISSIONS_QUERY);
    }

    public static SmartSecurity getSmartSecurity() {

        String className = ConfigHelper.getString(SecurityConstant.SMART_SECURITY);
        return (SmartSecurity)ReflectionUtil.newInstance(className);
    }

    public static boolean isCache() {

        return ConfigHelper.getBoolean(SecurityConstant.CACHEABLE);
    }

    public static String getRealms() {

        return ConfigHelper.getString(SecurityConstant.REALMS);
    }
}
