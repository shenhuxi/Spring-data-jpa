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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/sys")
@Api(tags = "系统管理", description = "")
public class SystemController extends BaseController {
    private static String  initPassword="f883ee10adc3949ba59abbe56e057f20";//123456  前端处理的结果
	@Autowired
    UserService userService;

	@Autowired
    public HttpServletRequest request;
    /**
     * 获取系统毫秒数
     * @return
     */
	@GetMapping("/getCurrentTime")
    @ApiOperation(value = "获取系统时间", notes = "获取系统毫秒数")
    public ResultObject<?> getCurrentTime() {
        return ResultObject.ok(System.currentTimeMillis());
    }

    @GetMapping("/unAuth")
    @ApiOperation(value = "响应无权限", notes = "")
    public ResponseEntity unAuth() {
        return new ResponseEntity("无权限操作", HttpStatus.FORBIDDEN);
    }
    @GetMapping("/unLogin")
    @ApiOperation(value = "响应未登录", notes = "")
    public ResponseEntity unLogin() {
        return new ResponseEntity("请重新登录", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/login")
    @ApiOperation(value = "用户登录", notes = "")
    @ApiImplicitParams({ 	@ApiImplicitParam(name = "account", value = "账号", required = true, dataType = "String" ),
    						@ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")})
    public ResultObject<?> login(String account,String password){

        return ResultObject.ok(null);
    }
    
    @PostMapping("/loginOut")
    @ApiOperation(value = "用户退出登录", notes = "")
    public ResultObject<?> loginOut(){
    	//Subject subject = SecurityUtils.getSubject();
    	//subject.logout();
    	return ResultObject.ok("已注销");
    }
    @PostMapping("/add")
    @ApiOperation(value= "用户新增", notes="" )
    @ApiImplicitParam(name = "user", value = "具体业务对象json", required = true, dataType="User" ,paramType = "body")
    public ResultObject<?> add(@Valid @RequestBody User user, BindingResult br){
        if (br.hasErrors()) {
            return ResultObject.error("参数错误");//getValidErrorMsg(br)
        }
        user.setPassWord(MD.md5(initPassword+user.getUserName()));
        User findByUserName = userService.findByUserName(user.getUserName());
        if(findByUserName!=null) {
            return ResultObject.error("账号已存在！");
        }
        User saveUser = userService.save(user);
        return ResultObject.ok(saveUser);
    }

    
}
