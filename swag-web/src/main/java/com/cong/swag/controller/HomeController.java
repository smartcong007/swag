package com.cong.swag.controller;

import com.cong.swag.common.VO.AuditSnapshotVO;
import com.cong.swag.common.VO.UserVO;
import com.cong.swag.common.exception.CommonException;
import com.cong.swag.dao.AuditSnapshotDao;
import com.cong.swag.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhengcong on 2018/5/27.
 */
@Controller
@Api(value = "homecontroller", tags = "首页控制器")
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    AuditSnapshotDao auditSnapshotDao;

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

    @RequestMapping(value = "/audit/snapshot", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取审批操作记录")
    public List<AuditSnapshotVO> getSnapshots(
        @ApiParam(name = "auditType", value = "审批类型") @RequestParam String auditType,
        @ApiParam(name = "idRef", value = "订单id") @RequestParam String idRef
    ) {
        return auditSnapshotDao.listAuditSnapshot(idRef, auditType);
    }

    @RequestMapping(value = "/app", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取app名称")
    public String getAppName() {
        return userService.getAppName();
    }

}
