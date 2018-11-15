package com.gomo.ethchain.controller;

import com.gomo.ethchain.service.GomoCoinSDK;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;

@Controller
@RequestMapping("/gomo/token")

public class TokenController {

    @RequestMapping(value = "/transfer", method = RequestMethod.GET)
    @ResponseBody
    public Object transfer(HttpServletRequest request) {

        JSONObject response_result = null;
        String password = "1234";
        String keyPath = "/home/huangyixuan/blockchain/eth/test/keystore/UTC--2018-05-03T08-52-28.407217173Z--2a385d78f43bb41f819b8ae76e25ba4523f9f123";

        String accountTo = request.getParameter("accountTo");
        String number = request.getParameter("number");
        String accessKey = request.getParameter("accessKey");
        if (!accessKey.equals("47eae244-e2c7-46b8-857b-9f638a177ec8"))
            return null;
        try {
            GomoCoinSDK.transfer(accountTo, number, password, keyPath);//管理员转账给用户
        } catch (Exception e) {
            response_result = new JSONObject();
            response_result.element("errcode", 1001).element("description", e.getMessage()).element("From", "GOMO TOKEN");
            return response_result;
        }

        response_result = new JSONObject();
        response_result.element("errcode", 0).element("description", "转账成功！").element("From", "GOMO TOKEN");
        return response_result;
    }

    @RequestMapping(value = "/transferFrom", method = RequestMethod.GET)
    @ResponseBody
    public Object transferFrom(HttpServletRequest request) {

        JSONObject response_result = null;

        String accountFrom = request.getParameter("accountFrom");
        String accountTo = request.getParameter("accountTo");
        String number = request.getParameter("number");
        String password = request.getParameter("password");
        String keyPath = request.getParameter("keyPath");
        String accessKey = request.getParameter("accessKey");
        if (!accessKey.equals("47eae244-e2c7-46b8-857b-9f638a177ec8"))
            return null;
        try {
            GomoCoinSDK.transferFrom(accountFrom, accountTo, number, password, keyPath);
        } catch (Exception e) {
            response_result = new JSONObject();
            response_result.element("errcode", 1001).element("description", e.getMessage()).element("From", "GOMO TOKEN");
            return response_result;
        }

        response_result = new JSONObject();
        response_result.element("errcode", 0).element("description", "转账成功！").element("From", "GOMO TOKEN");
        return response_result;
    }

    @RequestMapping(value = "/balanceOf", method = RequestMethod.GET)
    @ResponseBody
    public Object balanceOf(HttpServletRequest request) {

        JSONObject response_result = null;

        String account = request.getParameter("account");
        String accessKey = request.getParameter("accessKey");
        if (!accessKey.equals("47eae244-e2c7-46b8-857b-9f638a177ec8"))
            return null;
        try {
            BigInteger num = GomoCoinSDK.balanceOf(account);
            response_result = new JSONObject();
            response_result.element("errcode", 0).element("description", "查询余额成功！").element("num", num).element("From", "GOMO TOKEN");
            return response_result;
        } catch (Exception e) {
            response_result = new JSONObject();
            response_result.element("errcode", 1001).element("description", e.getMessage()).element("From", "GOMO TOKEN");
            return response_result;
        }


    }

}