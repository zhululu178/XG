package com.letsup.habit.app.backend.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController<T> {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected String getResponse(int code, String message, Object data) {
        JSONObject result = new JSONObject();
        Map<String, Object> meta = new HashMap<String, Object>();
        result.put("code", code);
        result.put("message", message);
        if (data != null) {
            result.put("data", data);
        } else {
            result.put("data", "");
        }
        return JSON.toJSONString(result,  SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullListAsEmpty,//List字段如果为null,输出为[],而非null
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.SkipTransientField);//是否输出值为null的字段,默认为false
    }

    protected PageVo<T> getPageVo(String pageIndex, int pageCount, int count) {
        if ("".equals(pageIndex)) {
            pageIndex = "0";
        }
        PageVo<T> pageVo = new PageVo<T>(Integer.parseInt(pageIndex), count, pageCount);
        pageVo.init();
        return pageVo;
    }

    protected String getResponse(GlobalException globalException) {
        return getResponse(globalException.getCode(), globalException.getMessage(), null);
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     *
     * @return ip
     */
    protected String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
