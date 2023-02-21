package shop.mtcoding.loginexam.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {
    
    public List<User> findAll();

    public User findById(int id);

    public int insert(User user);

    public int deleteById(int id);

    public int updateById(User user);

    public User findByUsernameAndPassword(User user);

    public User findByUsername(String username);
}
