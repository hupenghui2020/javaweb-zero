package org.smart4j.plugin.security;

import java.util.Set;

/**
 * Smart Security 接口
 * 可在应用中实现该接口，并在 smart.properties 文件中提供以下配置项：
 * eg:
 *      smart.plugin.security.realms=custom
 *      smart.plugin.security.custom.class=xxx.xxx.xxxSecurity
 * 或者在 smart.properties 文件中提供以下基于 SQL 的配置项：
 * eg:
 *      smart.plugin.security.jdbc.authc_query:根据用户名获取密码
 *      smart.plugin.security.jdbc.rules_query:根据用户名获取角色名集合
 *      smart.plugin.security.jdbc.permissions_query:根据角色名获取权限名集合
 * @author hph
 */
public interface SmartSecurity {

    /**
     * 根据用户名获取密码
     * @param username
     * @return
     */
    String getPassword(String username);

    /**
     * 根据用户名获取角色名集合
     * @param username
     * @return
     */
    Set<String> getRoleNameSet(String username);

    /**
     * 根据角色名获取权限名集合
     * @param roleName
     * @return
     */
    Set<String> getPermissionNameSet(String roleName);
}
