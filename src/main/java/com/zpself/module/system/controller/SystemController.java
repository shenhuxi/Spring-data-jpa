package com.zpself.module.system.controller;

import com.zpself.jpa.utils.MD;
import com.zpself.jpa.utils.ResultObject;
import com.zpself.module.common.BaseController;
import com.zpself.module.system.entity.User;
import com.zpself.module.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/sys")
@Api(tags = "系统管理", description = "系统管理的接口")
public class SystemController extends BaseController {
    private final UserService userService;

    @Autowired
    public SystemController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/testEntityManage")
    @ApiOperation(value = "用户退出登录")
    public ResultObject<?> testEntityManage(){
        userService.testEntityManage();
        return ResultObject.ok("执行完毕！");
    }
	@GetMapping("/getCurrentTime")
    @ApiOperation(value = "获取系统时间", notes = "获取系统毫秒数")
    public ResultObject<?> getCurrentTime() {
        return ResultObject.ok(System.currentTimeMillis());
    }

    @GetMapping("/unAuth")
    @ApiOperation(value = "响应无权限")
    public ResponseEntity<String> unAuth() {
        return new ResponseEntity<>("无权限操作", HttpStatus.FORBIDDEN);
    }
    @GetMapping("/unLogin")
    @ApiOperation(value = "响应未登录")
    public ResponseEntity<String> unLogin() {
        return new ResponseEntity<>("请重新登录", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({ 	@ApiImplicitParam(name = "account", value = "账号", required = true, dataType = "String" ),
    						@ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")})
    public ResultObject<?> login(String account,String password){

        return ResultObject.ok(account+password);
    }
    
    @PostMapping("/loginOut")
    @ApiOperation(value = "用户退出登录")
    public ResultObject<?> loginOut(){
    	return ResultObject.ok("已注销");
    }
    @PostMapping("/add")
    @ApiOperation(value= "用户新增")
    @ApiImplicitParam(name = "user", value = "具体业务对象json", required = true, dataType="User" ,paramType = "body")
    public ResultObject<?> add(@Valid @RequestBody User user, BindingResult br){
        if (br.hasErrors()) {
            //getValidErrorMsg(br)
            return ResultObject.error("参数错误");
        }
        String initPassword = "f883ee10adc3949ba59abbe56e057f20";
        user.setPassWord(MD.md5(initPassword +user.getUserName()));
        User findByUserName1 = userService.findByUserName(user.getUserName());
        User findByUserName = userService.findOne(0L);
        if(findByUserName!=null||findByUserName1!=null) {
            return ResultObject.error("账号已存在！");
        }
        User saveUser = userService.save(user);
        return ResultObject.ok(saveUser);
    }

    
}
