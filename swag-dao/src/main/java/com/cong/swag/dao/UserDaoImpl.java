package com.cong.swag.dao;

import com.cong.swag.common.VO.UserVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhengcong on 2018/5/27.
 */
@Component("userDao")
public class UserDaoImpl implements UserDao {

    @Autowired
    SqlSessionTemplate sqlSession;

    @Override
    public UserVO getUserById(Integer id) {
        return this.sqlSession.selectOne("userServiceNameSpace.getUserById",id);
    }
}
