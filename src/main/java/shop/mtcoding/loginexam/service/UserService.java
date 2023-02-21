package shop.mtcoding.loginexam.service;

import java.security.NoSuchAlgorithmException;

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
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void join(JoinReqDto joinReqDto) {
        User userPS = userRepository.findByUsername(joinReqDto.getUsername());
        checkObjNotnull(userPS, "동일한 유저네임이 존재합니다.");
        try {
            String salt = Hash.makeSalt();
            userRepository.insert(makeUserObj(joinReqDto.getUsername(), joinReqDto.getPassword(), salt, joinReqDto.getEmail()));
        } catch (Exception e) {
            throw new CustomException("회원가입 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Transactional(readOnly = true)
    public User login(LoginReqDto loginReqDto) {
        User userPS;
        try {
            userPS = userRepository.findByUsernameAndPassword(makeUserObj(loginReqDto.getUsername(), loginReqDto.getPassword(),
                    userRepository.findByUsername(loginReqDto.getUsername()).getSalt()));
        } catch (Exception e) {
            throw new CustomException("로그인 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (userPS == null) {
            throw new CustomException("유저네임이나 패스워드를 확인하세요");
        }
        return userPS;
    }

    private void checkObjNotnull(Object obj, String msg){
        if (obj != null) {
            throw new CustomException(msg);
        }
    }

    private User makeUserObj(String username, String password, String salt, String email) throws NoSuchAlgorithmException {
        return new User(username, Hash.encode(password + salt), salt, email);
    }

    private User makeUserObj(String username, String password, String salt)
            throws NoSuchAlgorithmException {
        return new User(username, Hash.encode(password + salt), salt);
    }
}
