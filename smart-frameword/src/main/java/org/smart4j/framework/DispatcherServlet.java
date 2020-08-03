package org.smart4j.framework;


import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;
import org.smart4j.framework.helper.*;
import org.smart4j.framework.util.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 请求转发器
 * @author 10499
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    private final static String ICO_URL = "/favicon.ico";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 初始化 ServletHelper，将 Servlet 相关的对象放入当前请求线程中
        ServletHelper.init(req, resp);
        try {
            // 获取请求方法与请求路径
            String requestMethod = req.getMethod().toLowerCase();
            String requestPath = req.getPathInfo();
            if(ICO_URL.equals(requestPath)) {
                return;
            }
            // 获取 Action 处理器
            Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
            if(handler != null) {
                // 获取 Controller 类及其 Bean 实例
                Class<?> controllerClass = handler.getControllerClass();
                Object controllerBean = BeanHelper.getBean(controllerClass);
                // 请求参数封装
                Param param;
                // Content-Type 为 multipart/ 开头的请求
                if(UploadHelper.isMultipart(req)) {
                    param = UploadHelper.createParam(req);
                }else {
                    param = RequestHelper.createParam(req);
                }
                Object result;
                // 调用 Action 方法
                Method actionMethod = handler.getActionMethod();
                if(param.isEmpty()) {
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
                }else {
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
                }
                // 处理 Action 方法返回值
                if(result instanceof View) {
                    // view 类型数据的处理
                    handleViewResult((View)result, req, resp);
                }else if(result instanceof Data) {
                    // data 类型数据的处理
                    handleDataResult((Data)result, resp);
                }
            }
        }finally {
            // 销毁
            ServletHelper.destroy();
        }
    }

    private void handleViewResult(View view, HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException{

        // 返回 JSP 页面
        String path = view.getPath();
        if(StringUtil.isNotEmpty(path)) {
            if(path.startsWith("/")) {
                resp.sendRedirect(req.getContextPath() + path);
            }else {
                Map<String, Object> model = view.getModel();
                for(Map.Entry<String, Object> entry: model.entrySet()) {
                    req.setAttribute(entry.getKey(), entry.getValue());
                }
                req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
            }
        }
    }

    private void handleDataResult(Data data, HttpServletResponse resp)
            throws IOException{

        // 返回 JSON 数据
        Object model = data.getModel();
        if(model != null) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter writer = resp.getWriter();
            String json = JsonUtil.toJson(model);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        // 初始化相关 Helper 类
        HelperLoader.init();
        //获取 ServletContext 对象（用于注册 Servlet）
        ServletContext servletContext = config.getServletContext();
        // 注册处理 Jsp 的 Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        // 注册处理静态资源的默认 Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
        // 初始化上传 Helper 类
        UploadHelper.init(servletContext);
    }
}