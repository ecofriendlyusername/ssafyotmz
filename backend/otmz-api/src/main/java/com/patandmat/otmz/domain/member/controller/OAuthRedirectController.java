package com.patandmat.otmz.domain.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/oauthredirect")
@PropertySource("classpath:application.properties")
public class OAuthRedirectController {
    private Environment env;
    @Autowired
    public OAuthRedirectController(Environment env) {
        this.env = env;
    }
    @GetMapping("/google")
    public void redirect(HttpServletRequest request, HttpServletResponse response) {
        String authServer = request.getRequestURI().split("/")[2];
        String baseUrl = env.getProperty("oauth."+authServer+".authcode.baseurl");
        Map<String,String> map = new HashMap<>();
        map.put("client_id",env.getProperty("oauth."+authServer+".clientid"));
        map.put("access_type","offline");
        map.put("redirect_uri",env.getProperty("oauth."+authServer+".redirecturi"));
        map.put("scope",env.getProperty("oauth."+authServer+".scope"));
        map.put("response_type","code");
        String url = getMeUrl(baseUrl,map);
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            System.out.println("input or output exception occurred");
            throw new RuntimeException(e);
        }
    }
    private String getMeUrl(String baseUrl, Map<String,String> map) {
        String url = baseUrl + "?";
        for (String key : map.keySet()) {
            url += key + "=" + map.get(key) + "&";
        }
        System.out.println(url);
        return url;
    }
}