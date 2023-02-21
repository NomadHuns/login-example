package shop.mtcoding.loginexam.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.loginexam.dto.UserReq.JoinReqDto;
import shop.mtcoding.loginexam.ex.CustomException;
import shop.mtcoding.loginexam.model.User;
import shop.mtcoding.loginexam.model.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void join(JoinReqDto joinReqDto) {
        User principal = userRepository.findByUsername(joinReqDto.getUsername());
        if (principal != null) {
            throw new CustomException("동일한 유저네임이 존재합니다.");
        }
        try {
            userRepository.insert(new User(joinReqDto.getUsername(), joinReqDto.getPassword(), joinReqDto.getEmail()));
        } catch (Exception e) {
            throw new CustomException("회원가입 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
