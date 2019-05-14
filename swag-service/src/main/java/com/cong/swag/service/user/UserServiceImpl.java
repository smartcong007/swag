package com.cong.swag.service.user;

import com.cong.swag.common.VO.UserVO;
import com.cong.swag.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by zhengcong on 2018/5/27.
 */
@Service("userService")
public class UserServiceImpl implements UserService, InitializingBean {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserDao userDao;

    @Value("${systemInfo.appName}")
    String appName;

    @Override
    public UserVO getUser(Integer id) {
        return userDao.getUserByUserId(id);
    }

    @Override
    public boolean insert(UserVO userVO) {
        return userDao.insertUser(userVO) == 1;
    }

    @Override
    public String getAppName() {
        return appName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.info("userService initing...");
    }
}
