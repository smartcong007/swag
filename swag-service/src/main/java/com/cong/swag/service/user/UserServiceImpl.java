package com.cong.swag.service.user;

import com.cong.swag.common.VO.UserVO;
import com.cong.swag.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhengcong on 2018/5/27.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public UserVO getUser(Integer id) {
        return userDao.getUserByUserId(id);
    }

    @Override
    public boolean insert(UserVO userVO) {
        return userDao.insertUser(userVO) == 1;
    }
}
