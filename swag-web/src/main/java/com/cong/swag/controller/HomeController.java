package com.cong.swag.controller;

import com.cong.swag.common.VO.GoodsVO;
import com.cong.swag.common.VO.UserVO;
import com.cong.swag.common.exception.CommonException;
import com.cong.swag.common.util.Result;
import com.cong.swag.dao.GoodsDao;
import com.cong.swag.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhengcong on 2018/5/27.
 */
@Controller
@Api(value = "homecontroller", tags = "首页控制器")
public class HomeController {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    UserService userService;

    @Autowired
    GoodsDao goodsDao;

    private Lock lock = new ReentrantLock();

    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ApiOperation(value = "首页")
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据id获取用户")
    public UserVO getUser(@ApiParam(name = "id", value = "用户id", required = true) @PathVariable String id) {
        UserVO userVO = userService.getUser(Integer.valueOf(id));
        if (userVO == null) {
            throw new CommonException(404, "用户不存在");
        }else {
            return userVO;
        }
    }

    @RequestMapping(value = "/app", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取app名称")
    public String getAppName() {
        return userService.getAppName();
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加用户")
    public UserVO add(@ApiParam(name = "user", value = "用户") @RequestBody @Valid UserVO userVO) {

        return userVO;

    }

    @RequestMapping(value = "/goods/order/{id}", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "下单")
    public Result order(@ApiParam(value = "商品Id") @PathVariable("id") Long id) {
        lock.lock();
        GoodsVO goodsVO = goodsDao.getGoodsById(id);
        if (goodsVO == null) {
            throw new CommonException(404, "商品不存在");
        }
        int expect = goodsVO.getStorage();
        if (expect <= 0) {
            throw new CommonException(500, "商品抢光啦");
        }
        int res = goodsDao.purchaseGoods(id, expect);
        lock.unlock();
        return res==1?Result.success("抢购成功"):Result.fail("500", "抢购失败");
    }



}
