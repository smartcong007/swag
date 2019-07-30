package com.cong.swag.dao;

import com.cong.swag.common.VO.WeiboHotRankItemVO;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-07-25
 */
public interface WeiboHotRankDao {

    int insert(@Param("items") List<WeiboHotRankItemVO> items);

    int deleteAll();

    WeiboHotRankItemVO getById(Integer id);

}
