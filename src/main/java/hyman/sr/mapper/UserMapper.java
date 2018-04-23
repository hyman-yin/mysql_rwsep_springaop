package hyman.sr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import hyman.sr.model.User;

@Mapper
public interface UserMapper {

    public List<User> selectAllUser();

    public void insertUser(User user);

    public void deleteUser(int id);

    public List<User> findUsers(String keyWords);

    public void editUser(User user);
}
