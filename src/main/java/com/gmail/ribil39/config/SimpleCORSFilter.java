package com.gmail.ribil39.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class SimpleCORSFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.setHeader("Access-Control-Allow-Headers", "type, uid, content-type");
        //response.setContentType("application/json;charset=utf-8");
        //response.setHeader("Content-Type", "text/html; charset=utf-8");

        chain.doFilter(req, res);
    }
    public void init(FilterConfig filterConfig) {}

    public void destroy() {}

}

    /*SELECT CONCAT(  'ALTER TABLE `', t.`TABLE_SCHEMA` ,  '`.`', t.`TABLE_NAME` ,  '` CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;' ) AS sqlcode FROM  `information_schema`.`TABLES` t WHERE 1 AND t.`TABLE_SCHEMA` = 'chatik' ORDER BY 1 LIMIT 0 , 90;*/