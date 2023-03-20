package com.patandmat.otmz.global.interceptor;

import com.patandmat.otmz.domain.auth.application.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class TokenCheckInterceptor implements HandlerInterceptor {
    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        System.out.println("token check interceptor");

        final Map<String, String> variableValueMap = (Map<String, String>) request
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        String idString = variableValueMap == null ? null : variableValueMap.get("id");

        Long id = idString == null ? -1 : Long.valueOf(idString);

        final String token = request.getHeader("access_token");
        if (token != null && jwtService.checkToken(token, id)) {
            // logger.info("토큰 사용 가능 : {}", token);
            System.out.println("successful");
            return true;
        } else {
            // logger.info("토큰 사용 불가능 : {}", token);
            System.out.println("not successful");
            response.sendError(401);
            return false;
            // throw new UnAuthorizedException();
        }
    }
}
