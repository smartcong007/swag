package com.cong.swag.dao;

import com.cong.swag.common.VO.UserVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by zhengcong on 2018/5/27.
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao {

    @Autowired
    SqlSessionTemplate sqlSession;

    @Override
    public UserVO getUserByUserId(Integer userId) {
        return this.sqlSession.selectOne("userServiceNameSpace.getUserById",userId);
    }

    @Override
    public int insertUser(UserVO userVO) {
        return this.sqlSession.insert("userServiceNameSpace.insertUser", userVO);
    }
}
