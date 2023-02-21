package shop.mtcoding.loginexam.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.loginexam.ex.CustomException;
import shop.mtcoding.loginexam.model.User;

@Component
@RequiredArgsConstructor
public class CustomInterceptor implements HandlerInterceptor {
    private final HttpSession session;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 요청 전 처리 로직
        User pincipal = (User) session.getAttribute("principal");
        if (pincipal == null) {
            throw new CustomException("로그인이 필요한 기능입니다", HttpStatus.UNAUTHORIZED, "/loginForm");
        }
        return true; // true를 반환하면 요청 처리가 계속 진행되며, false를 반환하면 요청 처리가 중단됩니다.
    }
}
