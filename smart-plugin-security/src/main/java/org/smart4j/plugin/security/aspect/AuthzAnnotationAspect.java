package org.smart4j.plugin.security.aspect;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.proxy.AspectProxy;
import org.smart4j.plugin.security.annotation.User;
import org.smart4j.plugin.security.exception.AuthzException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 授权注解切面
 * @author hph
 */
@Aspect(Controller.class)
public class AuthzAnnotationAspect extends AspectProxy {

    /**
     * 定义一个基于授权功能的注解类数组
     */
    private static final Class[] ANNOTATION_CLASS_ARRAY = {User.class};

    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {

        // 从目标类与目标方法中获取相应的注解
        Annotation annotation = getAnnotation(cls, method);
        if(annotation != null) {
            // 判断授权注解的类型
            Class<?> annotationType = annotation.annotationType();
            // TODO 完善其他注解的授权判断
            if(annotationType.equals(User.class)) {
                handleUser();
            }
        }
    }

    private void handleUser() throws AuthzException {

        Subject currentUser = SecurityUtils.getSubject();
        PrincipalCollection principals = currentUser.getPrincipals();
        if(principals == null || principals.isEmpty()) {
            throw new AuthzException("当前用户尚未登录");
        }
    }

    @SuppressWarnings("unchecked")
    private Annotation getAnnotation(Class<?> cls, Method method) {

        // 遍历所有的授权注解
        for(Class<? extends Annotation> annotationClass : ANNOTATION_CLASS_ARRAY) {
            // 首先判断目标方法上是否带有授权注解
            if(method.isAnnotationPresent(annotationClass)) {
                return method.getAnnotation(annotationClass);
            }
            // 然后判断目标类上是否带有授权注解
            if(cls.isAnnotationPresent(annotationClass)) {
                return cls.getAnnotation(annotationClass);
            }
        }
        // 若目标方法和目标类上均未带有授权注解，则返回空对像
        return null;
    }
}
