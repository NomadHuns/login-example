package shop.mtcoding.loginexam.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.loginexam.dto.UserReq.JoinReqDto;
import shop.mtcoding.loginexam.model.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    
    @Transactional
    public void join(JoinReqDto joinReqDto) {
    }
}
