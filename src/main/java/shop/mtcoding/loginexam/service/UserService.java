package shop.mtcoding.loginexam.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.loginexam.dto.UserReq.JoinReqDto;
import shop.mtcoding.loginexam.dto.UserReq.LoginReqDto;
import shop.mtcoding.loginexam.ex.CustomException;
import shop.mtcoding.loginexam.model.User;
import shop.mtcoding.loginexam.model.UserRepository;
import shop.mtcoding.loginexam.util.Hash;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void join(JoinReqDto joinReqDto) {
        User userPS = userRepository.findByUsername(joinReqDto.getUsername());
        if (userPS != null) {
            throw new CustomException("동일한 유저네임이 존재합니다.");
        }
        try {
            userRepository.insert(new User(joinReqDto.getUsername(), Hash.encode(joinReqDto.getPassword()), joinReqDto.getEmail()));
        } catch (Exception e) {
            throw new CustomException("회원가입 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public User login(LoginReqDto loginReqDto) {
        User userPS = null;
        try {
            userPS = userRepository.findByUsernameAndPassword(new User(loginReqDto.getUsername(), 
                    Hash.encode(loginReqDto.getPassword())));
        } catch (Exception e) {
            throw new CustomException("로그인 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (userPS == null) {
            throw new CustomException("유저네임이나 패스워드를 확인하세요");
        }
        return userPS;
    }
}
