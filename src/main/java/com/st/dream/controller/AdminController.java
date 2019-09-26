package com.st.dream.controller;

import com.st.dream.jwt.UserLoginToken;
import com.st.dream.sbmybatis.model.User;
import com.st.dream.service.UserService;
import com.st.dream.utils.TokenUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "admin")
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value="获取user信息", notes = "展示首页信息notes")
    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public User getUser() {
//        UserAAA user = new UserAAA();
//        user.setName("testName");
//        user.setPassword("123456");
//        return user;
//        Integer integer = userService.deleteByPrimaryKey(2);
//        System.out.println("xxxxxxx->"+integer);


//        User user = new User();
//        //user.setId(3);
//        user.setUsername("jxu333");
//        user.setPassword("password33");
//
//        List<UserProperty> propertyList = new ArrayList<UserProperty>();
//        UserProperty property = new UserProperty();
//        //property.setId(5);
//        property.setUserId(3);
//        property.setContent("hello");
//        propertyList.add(property);
//
//        UserProperty property2 = new UserProperty();
//        //property2.setId(6);
//        property2.setUserId(3);
//        property2.setContent("hellox");
//        propertyList.add(property2);
//
//        UserProperty property3 = new UserProperty();
//        //property3.setId(7);
//        property3.setUserId(3);
//        property3.setContent("helloxy");
//        propertyList.add(property3);
//
//        user.setPropertyList(propertyList);
//
//        userService.addUser(user);

        userService.deletePropertyByIds(new Integer[]{7,8,9});

        return userService.getUserWithProperty(3, new Integer[]{5,6,7});
    }

    @ApiOperation(value="获取消息", notes = "测试token用的")
    @GetMapping("/message")
    @ResponseStatus(HttpStatus.OK)
    @UserLoginToken
    public String getMessage() {
        return "已经有token啦";
    }

    @ApiOperation(value="登陆", notes = "用户登陆")
    @PostMapping("/login")
    public Object login(@RequestBody User user){
//        JSONObject jsonObject=new JSONObject();
        User userForBase=userService.getUserByName(user.getUsername());
        if(userForBase==null){
//            jsonObject.put("message","登录失败,用户不存在");
            return "登录失败,用户不存在";
        }else {
            if (!userForBase.getPassword().equals(user.getPassword())){
//                jsonObject.put("message","登录失败,密码错误");
                return "登录失败,密码错误";
            }else {
                String token = TokenUtil.getToken(userForBase);
//                jsonObject.put("token", token);
//                jsonObject.put("user", userForBase);
                return token;
            }
        }
    }
}
