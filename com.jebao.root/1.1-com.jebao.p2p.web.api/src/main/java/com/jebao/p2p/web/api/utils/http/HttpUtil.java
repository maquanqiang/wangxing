package com.jebao.p2p.web.api.utils.http;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Jack on 2016/12/7.
 */
public class HttpUtil {
    /**
     * 获取客户端真实ip地址
     * @param request HttpServletRequest
     * @return 客户端ip地址
     */
    public String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");

        if(StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(ipAddress.equals("0:0:0:0:0:0:0:1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    //e.printStackTrace();
                }
                ipAddress= inet.getHostAddress();
            }

        }

        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(!StringUtils.isBlank(ipAddress) && ipAddress.indexOf(",")>0){
            ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
        }
        return ipAddress;
    }
}
