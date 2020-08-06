package org.smart4j.plugin.security.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.smart4j.plugin.security.SecurityConstant;
import org.smart4j.plugin.security.SmartSecurity;
import org.smart4j.plugin.security.password.Md5CredentialsMatcher;

import java.util.HashSet;
import java.util.Set;

/**
 * 基于 Smart 的自定义的 Realm（需要实现 SmartSecurity 接口）
 * @author hph
 */
public class SmartCustomRealm extends AuthorizingRealm {

    private final SmartSecurity smartSecurity;

    public SmartCustomRealm(SmartSecurity smartSecurity) {

        this.smartSecurity = smartSecurity;
        super.setName(SecurityConstant.REALMS_CUSTOM);
        // 使用 MD5 加密算法
        super.setCredentialsMatcher(new Md5CredentialsMatcher());
    }

    /**
     * 获取授权信息
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        if(principals == null) {
            throw new AuthorizationException("parameter principals is null");
        }
        // 获取已认证用户的用户名
        String username = (String)super.getAvailablePrincipal(principals);
        // 通过 SmartSecurity 接口并根据用户名获取角色名集合
        Set<String> roleNameSet = smartSecurity.getRoleNameSet(username);
        // 通过 SmartSecurity 接口并根据角色名获取与其对应的权限名集合
        Set<String> permissionNameSet = new HashSet<>();
        if(roleNameSet != null && roleNameSet.size() > 0) {
            for(String roleName : roleNameSet) {
                Set<String> currentPermissionNameSet = smartSecurity.getPermissionNameSet(roleName);
                permissionNameSet.addAll(currentPermissionNameSet);
            }
        }
        // 将角色名集合与权限名集合放入 AuthorizationInfo 对象中
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roleNameSet);
        authorizationInfo.setStringPermissions(permissionNameSet);
        return authorizationInfo;
    }

    /**
     * 获取认证信息
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        if(token == null) {
            throw new AuthenticationException("parameter token is null");
        }
        // 通过 AuthenticationToken 对象获取从表单中提交过来的用户名
        String username = ((UsernamePasswordToken)token).getUsername();
        // 通过 SmartSecurity 接口并根据用户名获取数据库中存放的密码
        String password = smartSecurity.getPassword(username);
        // 将用户名与密码放入 AuthenticationInfo 对象中，便于后续的认证操作
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo();
        authenticationInfo.setPrincipals(new SimplePrincipalCollection(username, super.getName()));
        authenticationInfo.setCredentials(password);
        return authenticationInfo;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
