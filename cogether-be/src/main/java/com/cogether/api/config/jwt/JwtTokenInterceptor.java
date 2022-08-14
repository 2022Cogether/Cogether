package com.cogether.api.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenInterceptor implements HandlerInterceptor {

    //인터셉터(Interceptor)는 스프링의 Spring Context(ApplicationContext) 기능으로 임의의 URI를 호출시
// DispatcherServlet에서 해당 Controller가 처리되기 전과 후에 발생하는 이벤트이다.
    private final TokenUtils tokenUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {

        System.out.println("JwtToken 호출");
        String accessToken = request.getHeader("ACCESS_TOKEN");
        System.out.println("AccessToken:" + accessToken);
        String refreshToken = request.getHeader("REFRESH_TOKEN");
        System.out.println("RefreshToken:" + refreshToken);

        if(request.getMethod().equals("OPTIONS"))
        {
            return true;
        }
        if (accessToken != null ) {
            if (tokenUtils.isValidToken(accessToken)) {
                System.out.println("컨트롤러 진입");
                return true;  // 컨트롤러 진입
            }
        }
        System.out.println("setStatus401");
        response.setStatus(401);
        response.setHeader("ACCESS_TOKEN", accessToken);
        response.setHeader("REFRESH_TOKEN", refreshToken);
        response.setHeader("msg", "Check the tokens.");
        return false;   //컨트롤러 진입x
    }
}