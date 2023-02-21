package shop.mtcoding.loginexam.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.loginexam.dto.UserReq.JoinReqDto;
import shop.mtcoding.loginexam.dto.UserReq.LoginReqDto;
import shop.mtcoding.loginexam.ex.CustomException;
import shop.mtcoding.loginexam.model.User;
import shop.mtcoding.loginexam.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final HttpSession session;

    @GetMapping("/")
    public String home() {
        return "home/home";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @PostMapping("/join")
    public String join(JoinReqDto joinReqDto) {
        verifyString(joinReqDto.getUsername(), "유저네임을 입력하세요.");
        verifyString(joinReqDto.getPassword(), "패스워드를 입력하세요.");
        verifyString(joinReqDto.getEmail(), "이메일을 입력하세요.");
        userService.join(joinReqDto);
        return "redirect:/loginForm";
    }

    @PostMapping("/login")
    public String login(LoginReqDto loginReqDto) {
        verifyString(loginReqDto.getUsername(), "유저네임을 입력하세요.");
        verifyString(loginReqDto.getPassword(), "패스워드를 입력하세요.");
        User userPS = userService.login(loginReqDto);
        session.setAttribute("principal", userPS);
        return "redirect:/loginForm";
    }

    private void verifyString(String target, String msg) {
        if (target == null || target.isEmpty()) {
            throw new CustomException(msg);
        }
    }
}
