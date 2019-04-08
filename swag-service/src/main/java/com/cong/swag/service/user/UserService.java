package com.cong.swag.service.user;

import com.cong.swag.common.VO.UserVO;

/**
 * Created by zhengcong on 2018/5/27.
 */
public interface UserService {

    UserVO getUser(Integer id);

    boolean insert(UserVO userVO);

}
