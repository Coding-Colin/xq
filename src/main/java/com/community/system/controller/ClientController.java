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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ClientController {

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

    public static String LOGINUSER = "";

    /**
     * 初始化客户端首页
     * @return
     */
    @RequestMapping("/clientIndex.do")
    public String index(HttpSession session){
        LOGINUSER = String.valueOf(session.getAttribute("loginUser"));
        return "client/index";
    }


    /**
     * 初始化个人信息
     * @return
     */
    @RequestMapping("/personal.do")
    public String personal(Model model){
        User user = userMapper.getByName(LOGINUSER);
        model.addAttribute("user",user);
        return "client/personal";
    }

    /**
     * 修改密码
     * @return
     */
    @ResponseBody
    @RequestMapping("/updatePsw.do")
    public String updatePsw(@RequestBody String array[]){
        User user = userMapper.getByName(LOGINUSER);
        if (array[0].equals(user.password)){
            user.setPassword(array[1]);
            if (array[2]!=null&&!array[2].equals("")){
                user.setTel(array[2]);
            }
            userMapper.update(user);
            return JsonUtil.toJson("修改成功");
        }
        else {
            return JsonUtil.toJson("密码不匹配");
        }
    }


    /**
     * 初始化家庭成员
     * @return
     */
    @RequestMapping("/family.do")
    public String family(){
        return "client/family";
    }


    /**
     * 添加家庭成员
     * @return
     */
    @ResponseBody
    @RequestMapping("/addOrUpdateFamily.do")
    public String addOrUpdateFamily(@RequestBody String array[]){
        Family family = new Family();
        family.setName(array[0]);
        family.setSex(array[1]);
        family.setIdcard(array[2]);
        family.setAge(Integer.parseInt(array[3]));
        family.setLoginUser(LOGINUSER);
        family.setPos(0);
        familyMapper.add(family);
        return JsonUtil.toJson("添加成功");
    }


    /**
     * 初始化体温登记
     * @return
     */
    @RequestMapping("/bodyregister.do")
    public String bodyregister(Model model){
        List<Family> family = familyMapper.getByLoginUser(LOGINUSER);
        model.addAttribute("family",family);
        return "client/bodyRegister";
    }

    /**
     * 体温登记
     * @return
     */
    @ResponseBody
    @RequestMapping("/addBodyRegister.do")
    public String addBodyRegister(@RequestBody String array[]){
        BodyRegister bodyRegister = new BodyRegister();
        bodyRegister.setFid(Integer.parseInt(array[0]));
        bodyRegister.setLoginUser(LOGINUSER);
        bodyRegister.setTemperature(Float.parseFloat(array[1]));
        bodyRegisterMapper.add(bodyRegister);
        return JsonUtil.toJson("体温登记成功");
    }


    /**
     * 初始化外出登记
     * @return
     */
    @RequestMapping("/outregister.do")
    public String outregister(Model model){
        List<Family> family = familyMapper.getByLoginUser(LOGINUSER);
        model.addAttribute("family",family);
        return "client/outRegister";
    }

    /**
     * 外出登记
     * @return
     */
    @ResponseBody
    @RequestMapping("/addOutRegister.do")
    public String addOutRegister(@RequestBody String array[]){
        OutRegister outRegister = new OutRegister();
        outRegister.setFid(Integer.parseInt(array[0]));
        outRegister.setLoginUser(LOGINUSER);
        outRegister.setContext(array[1]);
        outRegisterMapper.add(outRegister);
        return JsonUtil.toJson("外出登记成功");
    }


    /**
     * 初始化公告
     * @return
     */
    @RequestMapping("/affiche.do")
    public String affiche(){
        return "client/affiche";
    }

    /**
     * 初始化公告
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectAffs.do")
    public String selectAffs(){
        List<Affiche> list = afficheMapper.getAll();
        return JsonUtil.toJson(list);
    }


    /**
     * 初始化留言
     * @return
     */
    @RequestMapping("/leavemessage.do")
    public String leavemessage(){
        return "client/message";
    }


    /**
     * 留言
     * @return
     */
    @ResponseBody
    @RequestMapping("/leavemes.do")
    public String addmes(@RequestBody String array[]){
        Info info = new Info();
        info.setLoginUser(LOGINUSER);
        info.setEmail(array[0]);
        info.setContent(array[1]);
        infoMapper.add(info);
        return JsonUtil.toJson("留言成功");
    }


    /**
     * 初始化个人中心页面
     * @return
     */
    @RequestMapping("/center.do")
    public String center(Model model){
        List<Family> familyList = familyMapper.getByLoginUser(LOGINUSER);
        List<BodyRegister> bodyRegisterList = bodyRegisterMapper.getByLoginUser(LOGINUSER);
        List<Map<String,Object>> result1 = new ArrayList<Map<String, Object>>();
        for (int i=0;i<bodyRegisterList.size();i++){
            Family family = familyMapper.getById(bodyRegisterList.get(i).getFid());
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("bodyregister",bodyRegisterList.get(i));
            map.put("family",family);
            result1.add(map);
        }
        List<OutRegister> outRegisterList = outRegisterMapper.getByLoginUser(LOGINUSER);
        List<Map<String,Object>> result2 = new ArrayList<Map<String, Object>>();
        for (int i=0;i<outRegisterList.size();i++){
            Family family = familyMapper.getById(outRegisterList.get(i).getFid());
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("outregister",outRegisterList.get(i));
            map.put("family",family);
            result2.add(map);
        }
        model.addAttribute("logs1",familyList);
        model.addAttribute("logs2",result1);
        model.addAttribute("logs3",result2);
        return "client/center";
    }


    /**
     * 论坛
     * @return
     */
    @RequestMapping("/luntan.do")
    public String luntan(){
        return "client/luntan";
    }


    /**
     * 初始化论坛
     * @return
     */
    @ResponseBody
    @RequestMapping("/onloadluntan.do")
    public String onloadluntan(){
        List<Luntan> luntans = luntanMapper.getAll();
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        for (Luntan luntan:luntans){
            List<LuntanReply> luntanReplies = luntanReplyMapper.getByRid(luntan.id);
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("luntan",luntan);
            map.put("reply",luntanReplies);
            list.add(map);
        }
        return JsonUtil.toJson(list);
    }

    /**
     * 评论
     * @return
     */
    @ResponseBody
    @RequestMapping("/addchat.do")
    public String addchat(@RequestBody String []array){
        Luntan luntans = new Luntan();
        luntans.setReplyName(LOGINUSER);
        luntans.setContent(array[0]);
        luntans.setAddress("中国");
        luntans.setBrowse("Win10");
        luntanMapper.add(luntans);
        return JsonUtil.toJson("成功");
    }

    /**
     * 回复
     * @return
     */
    @ResponseBody
    @RequestMapping("/replychat.do")
    public String replychat(@RequestBody String []array){
        Luntan luntans = new Luntan();
        luntans.setReplyName(LOGINUSER);
        luntans.setContent(array[1]);
        luntans.setAddress("中国");
        luntans.setBrowse("Win10");
        luntanMapper.update(JsonUtil.toJson(luntans),Integer.parseInt(array[0]));
        return JsonUtil.toJson("回复成功");
    }


    /**
     * 回复论坛
     * @return
     */
    @ResponseBody
    @RequestMapping("/addluntanreply.do")
    public String addluntanreply(@RequestBody String []array){
        LuntanReply luntanReply = new LuntanReply();
        luntanReply.setRid(Integer.parseInt(array[0]));
        luntanReply.setMsg(array[1]);
        luntanReply.setName(LOGINUSER);
        luntanReplyMapper.add(luntanReply);
        return JsonUtil.toJson("评论成功");
    }


}
