package com.gomo.ethchain.controller;

import com.gomo.ethchain.service.GomoCoinSDK;
import com.gomo.ethchain.service.UserSDK;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;

@Controller
@RequestMapping("/gomo/user")

public class UserController {

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    public Object creatUser(HttpServletRequest request) {

        JSONObject response_result = null;

        String password = request.getParameter("password");
        String accessKey = request.getParameter("accessKey");
        if (!accessKey.equals("47eae244-e2c7-46b8-857b-9f638a177ec8"))
            return null;
        try {
            JSONObject data=UserSDK.creatUser(password);
            response_result = new JSONObject();
            response_result.element("errcode", 0).element("description", "创建用户成功！").element("data",data).element("From", "GOMO TOKEN");
        }catch (Exception e){
            response_result = new JSONObject();
            response_result.element("errcode", 1001).element("description", e.getMessage()).element("From", "GOMO TOKEN");
            return response_result;
        }

        return response_result;
    }


}