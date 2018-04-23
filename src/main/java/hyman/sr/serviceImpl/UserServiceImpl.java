package hyman.sr.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hyman.sr.mapper.UserMapper;
import hyman.sr.model.User;
import hyman.sr.service.UserService;

/**
 * userService
 * 
 */
@Service("userService")
@Transactional(rollbackFor=Exception.class)  
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper iUserDao;
    
    @Override
    public List<User> getAllUser() {
        return this.iUserDao.selectAllUser();
    }

    @Override
    public void insertUser(User user) {
        this.iUserDao.insertUser(user);
    }

    @Override
    public void deleteUser(int id) {
        this.iUserDao.deleteUser(id);
    }

    @Override
    public List<User> findUsers(String keyWords) {
        return iUserDao.findUsers(keyWords);
    }

    @Override
    public void editUser(User user) {
        this.iUserDao.editUser(user);
    }

	@Override
	public User getUserById(int userId) {
		return null;
	}
}
