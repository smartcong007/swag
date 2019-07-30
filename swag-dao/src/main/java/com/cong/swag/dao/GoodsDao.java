package com.cong.swag.dao;

import com.cong.swag.common.VO.GoodsVO;
import org.apache.ibatis.annotations.Param;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-06-09
 */
public interface  GoodsDao {

    int addGoods(GoodsVO goodsVO);

    int purchaseGoods(@Param("goodsId") Long goodsId, @Param("expectStorage") Integer expectStorage);

    GoodsVO getGoodsById(Long goodsId);

}
