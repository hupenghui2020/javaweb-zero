package com.hph.smart.controller;

import com.hph.smart.domain.User;
import com.hph.smart.service.SmartDemoService;
import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;

import java.sql.SQLException;
import java.util.List;

/**
 * @author hph
 */
@Controller
public class SmartDemoController {

    @Inject
    private SmartDemoService smartDemoService;

    @Action("get:/test")
    public View test(Param param) throws SQLException {

        smartDemoService.getUserList();
        List<User> userList = smartDemoService.getUserList();
        return new View("index.jsp").addModel("userList", userList);
    }
}
