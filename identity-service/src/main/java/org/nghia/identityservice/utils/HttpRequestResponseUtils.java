package org.nghia.identityservice.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HttpRequestResponseUtils {
    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };
    private static final String UNKNOWN = "unknown";
    private static final String NO_IP = "0.0.0.0";
    private static final String SPLITTER = ",";

    public static String getClientIP() {
        if (RequestContextHolder.getRequestAttributes() == null) {
            return NO_IP;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        for (String header : IP_HEADER_CANDIDATES) {
            String ipList = request.getHeader(header);
            if (ipList != null && ipList.length() != 0 && !UNKNOWN.equalsIgnoreCase(ipList)) {
                return ipList.split(SPLITTER)[0];
            }
        }
        return request.getRemoteAddr();
    }
}
