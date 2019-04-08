package com.cong.swag.dao;

import com.cong.swag.common.VO.UserVO;

/**
 * Created by zhengcong on 2018/5/27.
 */
public interface UserDao {

    UserVO getUserByUserId(Integer userId);

    int insertUser(UserVO userVO);

}
