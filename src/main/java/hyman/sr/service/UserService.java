package hyman.sr.service;

import java.util.List;

import hyman.sr.model.User;

/**
 * user表的操作接口
 */
public interface UserService {

    /**
     * 通过id查询user接口方法
     * @param userId
     * @return
     */
     public User getUserById(int userId);  
     
     /**
      * 查询所有的user
      * @return 返回userList
      */
     public List<User> getAllUser();
     
     /**
      * 添加一个user
      * @param user
      */
     public void insertUser(User user);
     
     /**
      * 通过ID删除用户
      * @param id
      */
     public void deleteUser(int id);
     
     /**
      * 通过关键字查询用户
      * @param keyWords
      * @return
      */
     public List<User> findUsers(String keyWords);
     
     /**
      * 更新用户
      * @param user
      */
     public void editUser(User user);
}