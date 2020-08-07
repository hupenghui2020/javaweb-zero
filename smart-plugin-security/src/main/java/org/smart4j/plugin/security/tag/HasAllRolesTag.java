package org.smart4j.plugin.security.tag;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.tags.RoleTag;

import java.util.Arrays;

/**
 * 自定义标签
 * 判断当前用户是否拥有其中所有的角色（逗号分隔，表示 “与” 的关系）
 * @author hph
 */
public class HasAllRolesTag extends RoleTag {

    private static final String ROLE_NAMES_DELIMITER=",";

    @Override
    protected boolean showTagBody(String roleName) {

        boolean hasAllRole = false;
        Subject subject = getSubject();
        if(subject != null) {
            if(subject.hasAllRoles(Arrays.asList(roleName.split(ROLE_NAMES_DELIMITER)))) {
                hasAllRole = true;
            }
        }
        return hasAllRole;
    }
}
