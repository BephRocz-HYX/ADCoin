package com.gomo.ethchain.controller;

import com.gomo.ethchain.service.EthSDK;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;

@Controller
@RequestMapping("/gomo/eth/query")

public class ETHController {

    @RequestMapping(value = "/block/height", method = RequestMethod.GET)
    @ResponseBody
    public Object getBlockNumber(HttpServletRequest request) {

        JSONObject response_result = null;

        String accessKey = request.getParameter("accessKey");
        if (!accessKey.equals("47eae244-e2c7-46b8-857b-9f638a177ec8"))
            return null;
        try {
            BigInteger blockHeight = EthSDK.getBlockNumber().add(BigInteger.valueOf(1));
            response_result = new JSONObject();
            response_result.element("errcode", 0).element("description", "获取当前区块高度成功！").element("blockHeight", blockHeight).element("From", "GOMO TOKEN");
        } catch (Exception e) {
            response_result = new JSONObject();
            response_result.element("errcode", 1001).element("description", e.getMessage()).element("From", "GOMO TOKEN");
            return response_result;
        }

        return response_result;
    }

    @RequestMapping(value = "/block/data", method = RequestMethod.GET)
    @ResponseBody
    public Object getBlock(HttpServletRequest request) {

        JSONObject response_result = null;

        String accessKey = request.getParameter("accessKey");
        String key = request.getParameter("key");
        if (!accessKey.equals("47eae244-e2c7-46b8-857b-9f638a177ec8"))
            return null;
        try {
            JSONObject result = JSONObject.fromObject(EthSDK.getBlockByNumber(key));
            response_result = new JSONObject();
            response_result.element("errcode", 0).element("description", "获取区块信息成功！").element("blockData", result).element("From", "GOMO TOKEN");
        } catch (Exception e) {
            try {
                JSONObject result = JSONObject.fromObject(EthSDK.getBlockByHash(key));
                response_result = new JSONObject();
                response_result.element("errcode", 0).element("description", "获取区块信息成功！").element("blockData", result).element("From", "GOMO TOKEN");
            } catch (Exception e1) {
                response_result = new JSONObject();
                response_result.element("errcode", 1001).element("description", e1.getMessage()).element("From", "GOMO TOKEN");
                return response_result;
            }
        }

        return response_result;
    }


}