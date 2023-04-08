package com.lue.rasp.hook;

import com.lue.rasp.context.Context;
import com.lue.rasp.context.ContextManager;
import io.undertow.util.HeaderValues;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 用于hook用户的http请求，并写入上下文中
 */
public class HttpHook implements HookInterface{

    /**
     * 绑定 request http 参数
     * ServletInitialHandler#handleFirstRequest(HttpServerExchange, ServletRequestContext)
     */
    public void storeRequestInfo(Object obj1, Object obj2) {
        io.undertow.server.HttpServerExchange exchange = (io.undertow.server.HttpServerExchange) obj1;

        Context context = ContextManager.getContext();

        context.setResponse(
                ((io.undertow.servlet.handlers.ServletRequestContext) obj2).getOriginalResponse()
        );

        // 本机地址
        String localAddr = exchange.getDestinationAddress().getAddress().getHostAddress();
        context.setLocalAddr(localAddr);

        // http请求类型：get、post
        String method = exchange.getRequestMethod().toString();
        context.setMethod(method);

        // URL
        String requestURL = exchange.getRequestURL();
        context.setRequestURL(requestURL);

        // URI
        String requestURI = exchange.getRequestURI();
        context.setRequestURI(requestURI);

        // http请求协议: HTTP/1.1
        String protocol = exchange.getProtocol().toString();
        context.setProtocol(protocol);

        // 调用主机地址
        String remoteHost = exchange.getSourceAddress().getAddress().getHostAddress();
        context.setRemoteHost(remoteHost);

        String queryString = exchange.getQueryString();
        context.setQueryString(queryString);

        // 请求header
        Map<String, String> header = new HashMap<String, String>(32);
        io.undertow.util.HeaderMap requestHeaders = exchange.getRequestHeaders();
        if (requestHeaders != null) {
            // 获取responseContentType
            io.undertow.util.HeaderValues accept = requestHeaders.get("Accept");
            if (accept != null) {
                context.setResponseContentType(accept.toString());
            }
            Iterator<HeaderValues> iterator = requestHeaders.iterator();
            while (iterator.hasNext()) {
                io.undertow.util.HeaderValues next = iterator.next();
                header.put(next.getHeaderName().toString(), toString(next.toArray()));
            }
        }
        context.setHeader(header);
    }

    private String toString(String[] arrays) {
        final StringBuilder buf = new StringBuilder(64);
        for (int i = 0; i < arrays.length; i++) {
            if (i > 0) {
                buf.append(",");
            }
            if (arrays[i] != null) {
                buf.append(arrays[i]);
            }
        }
        return buf.toString();
    }
}
