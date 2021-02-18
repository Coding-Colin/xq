package com.community.system.controller;

import com.community.system.bean.*;
import com.community.system.mapper.*;
import com.community.system.util.JsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class AdminController {

    @Resource
    public AfficheMapper afficheMapper;

    @Resource
    public BodyRegisterMapper bodyRegisterMapper;

    @Resource
    public FamilyMapper familyMapper;

    @Resource
    public InfoMapper infoMapper;

    @Resource
    public LuntanMapper luntanMapper;

    @Resource
    public LuntanReplyMapper luntanReplyMapper;

    @Resource
    public OutRegisterMapper outRegisterMapper;

    @Resource
    public UserMapper userMapper;




    /**
     * 跳转用户管理
     * @return
     */
    @RequestMapping("/userHtml.do")
    public String userHtml(Model model){
        List<User> list = userMapper.getAll();
        model.addAttribute("logs",list);
        return "admin/userList";
    }

    /**
     * 查询用户
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectuser.do")
    public String getUser(@RequestBody Integer id, HttpSession session){
        session.setAttribute("id",id);
        User user = userMapper.getById(id);
        return JsonUtil.toJson(user);
    }

    /**
     * 模糊查询用户
     * @return
     */
    @ResponseBody
    @RequestMapping("/userbyselect.do")
    public String selectUser(@RequestBody String s){
        List<User> list = userMapper.getuserBySelect(s);
        return JsonUtil.toJson(list);
    }

    /**
     * 添加用户
     * @return
     */
    @ResponseBody
    @RequestMapping("/adduser.do")
    public String addUser(@RequestBody String array[]){
        User user1 = userMapper.getByName(array[0]);//判断该用户名是否已经注册过
        if(user1!=null){
            return JsonUtil.toJson("该用户已注册请重新注册");//如果已经注册过直接返回错误
        }
        else {
            User user = new User();
            user.setName(array[0]);
            user.setTel(array[1]);
            user.setPassword(array[2]);
            user.setPos(Integer.parseInt(array[3]));
            user.setAddress(array[4]);
            userMapper.add(user);
            return JsonUtil.toJson("添加成功");
        }
    }

    /**
     * 更新用户
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateUser.do")
    public String updateUser(@RequestBody String array[],HttpSession session){
        Integer id = Integer.parseInt(String.valueOf(session.getAttribute("id")));
        User user = userMapper.getById(id);
        user.setName(array[1]);
        user.setTel(array[2]);
        user.setPassword(array[3]);
        user.setPos(Integer.parseInt(array[4]));//设置为普通用户
        user.setAddress(array[5]);
        userMapper.update(user);
        return JsonUtil.toJson("更新成功");//返回登陆页面
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteuser.do")
    public String deleteUser(@RequestBody Integer id){
        userMapper.delete(id);
        return JsonUtil.toJson("删除成功");
    }

    /**
     * 跳转家庭成员管理
     * @return
     */
    @RequestMapping("/familyHtml.do")
    public String familyHtml(Model model){
        List<Family> list = familyMapper.getAll();
        model.addAttribute("logs",list);
        return "admin/familyList";
    }



    /**
     * 体温管理
     * @return
     */
    @RequestMapping("/bodyregisterHtml.do")
    public String bodyregisterHtml(Model model){
        List<BodyRegister> list = bodyRegisterMapper.getAll();
        List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();
        for (int i=0;i<list.size();i++){
            Family family = familyMapper.getById(list.get(i).getFid());
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("bodyregister",list.get(i));
            map.put("family",family);
            result.add(map);
        }
        model.addAttribute("logs",result);
        return "admin/bodyRegister";
    }


     /**
     * 外出管理
     * @return
     */
    @RequestMapping("/outregisterHtml.do")
    public String outregisterHtml(Model model){
        List<OutRegister> list = outRegisterMapper.getAll();
        List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();
        for (int i=0;i<list.size();i++){
            Family family = familyMapper.getById(list.get(i).getFid());
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("outregister",list.get(i));
            map.put("family",family);
            result.add(map);
        }
        model.addAttribute("logs",result);
        return "admin/outRegister";
    }


    /**
     * 跳转公告管理
     * @return
     */
    @RequestMapping("/afficheHtml.do")
    public String afficheHtml(Model model){
        List<Affiche> list = afficheMapper.getAll();
        model.addAttribute("logs",list);
        return "admin/afficheList";
    }


    /**
     * 添加公告
     * @return
     */
    @ResponseBody
    @RequestMapping("/addAff.do")
    public String addInformation(@RequestBody String array[]){
        Affiche affiche = new Affiche();
        affiche.setTitle(array[0]);
        affiche.setContent(array[1]);
        afficheMapper.add(affiche);
        return JsonUtil.toJson("添加成功");
    }


    /**
     * 查询公告
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectAff.do")
    public String getInformation(@RequestBody Integer id){
        Affiche information = afficheMapper.getById(id);
        return JsonUtil.toJson(information);
    }
    /**
     * 更新公告
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateAff.do")
    public String updateInformation(@RequestBody String array[]){
        Affiche information = afficheMapper.getById(Integer.parseInt(array[0]));
        information.setTitle(array[1]);
        information.setContent(array[2]);
        afficheMapper.update(information);
        return JsonUtil.toJson("更新成功");//返回登陆页面
    }

    /**
     * 删除公告
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteAff.do")
    public String deleteinformation(@RequestBody Integer id){
        afficheMapper.delete(id);
        return JsonUtil.toJson("删除成功");
    }


    /**
     * 跳转意见收集
     * @return
     */
    @RequestMapping("/infoHtml.do")
    public String infoHtml(Model model){
        List<Info> list = infoMapper.getAll();
        model.addAttribute("logs",list);
        return "admin/infoList";
    }

    /**
     * 跳转溯源
     * @return
     */
    @RequestMapping("/traceHtml.do")
    public String traceHtml(){
        return "admin/trace";
    }

    /**
     * 跳转意见收集
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectTrace.do")
    public String selectTrace(@RequestBody String value[]){
        if (null == value[0] || value[0].length() == 0){
           return JsonUtil.toJson(outRegisterMapper.getByLoginUser(""));
        }
        List<Map<String,Object>> list = outRegisterMapper.getByLoginUser14(value[0]);
        return JsonUtil.toJson(list);
    }
}
