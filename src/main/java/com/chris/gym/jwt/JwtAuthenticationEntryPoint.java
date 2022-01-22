package com.chris.gym.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

   @Override
   public void commence(HttpServletRequest request,
                        HttpServletResponse response,
                        AuthenticationException authException) throws IOException {
      // 没有提供认证，只返回一个401错误，因为没有登录页面。
      // 可以在这替换其他需要返回的错误信息。
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
   }
}
