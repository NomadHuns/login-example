package shop.mtcoding.loginexam.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

@MybatisTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByUsernameAndPassword_test() throws Exception {
        // given
        ObjectMapper om = new ObjectMapper();
        User userDummy = new User("ssar", "1234");

        // when
        User userPS = userRepository.findByUsernameAndPassword(userDummy);
        String responseBody = om.writeValueAsString(userPS);
        System.out.println("테스트 : " + responseBody);
        // verify
        assertThat(userPS.getUsername()).isEqualTo("ssar");
        assertThat(userPS.getEmail()).isEqualTo("ssar@nate.com");
    }
}
