package com.community.system.controller;

import com.community.system.bean.User;
import com.community.system.mapper.UserMapper;
import com.community.system.util.JsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class LoginAndRegistController {


    @Resource
    public UserMapper userMapper;


    /**
     * 登陆页面
     * @return
     */
    @RequestMapping("/loginHtml.do")
    public String login(){
        return "login";
    }


    /**
     * 登陆验证
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/login.do")
    public String login(@RequestBody String array[],
                        HttpSession session){
        User user = userMapper.getByName(array[0]);//根据用户name查询用户
        if (user==null){
            return JsonUtil.toJson("当前用户没注册");
        }
        String pas = user.password;
        if(pas.equals(array[1])){
            session.setAttribute("loginUser",array[0]);//把登陆用户设置到session中
            Integer pos = user.pos;
            if(pos==1){
                return JsonUtil.toJson("欢迎管理员登陆");//返回管理端
            }
            else {
                return JsonUtil.toJson("欢迎用户登陆");//返回首页
            }
        }
        else {
            return JsonUtil.toJson("密码错误");
        }
    }


    /**
     * 注册页面
     * @return
     */
    @RequestMapping("/registHtml.do")
    public String regist(){
        return "regist";
    }

    /**
     * 注册
     * @return
     */
    @Transactional
    @ResponseBody
    @RequestMapping("/regist.do")
    public String addUser(@RequestBody String array[]){
        User user1 = userMapper.getByName(array[0]);//判断该用户名是否已经注册过
        if (user1!=null){
            return JsonUtil.toJson("该用户已注册请重新注册");//如果已经注册过直接返回错误
        }
        else {
            User user = new User();
            user.setName(array[0]);
            user.setTel(array[1]);
            user.setPassword(array[2]);
            user.setPos(Integer.parseInt(array[3]));//设置为普通用户
            userMapper.add(user);
            return JsonUtil.toJson("注册成功");//返回登陆页面
        }
    }

    /**
     * 退出登陆
     * @param session
     * @return
     */
    @RequestMapping("/quit.do")
    public String back(HttpSession session){
        session.removeAttribute("loginUser");
        return "login";

    }

}
