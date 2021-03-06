package cn.edu.hzvtc.controller;

import cn.edu.hzvtc.entity.ReturnMsg;
import cn.edu.hzvtc.entity.UserInfo;
import cn.edu.hzvtc.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: XuYi
 * @date: 2021/6/16 15:45
 * @description:
 */
@Controller
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;


    @RequestMapping("/checkuserName")
    @ResponseBody
    public ReturnMsg checkuserName(@RequestParam("userName") String userName) {
        /*验证用户名非空，符合验证规则*/
        /*验证用户名是否重复*/
        if (userInfoService.checkUserName(userName)) {
            return ReturnMsg.success().add("msg", "用户名可用");
        } else {
            return ReturnMsg.fail().add("msg", "该用户名太抢手了，换一个试试！");
        }
    }


    @RequestMapping("registerInfo")
    public String registerInfo() {
        return "register";
    }

    @RequestMapping("loginInfo")
    public String loginInfo() {
        return "login";
    }

    @RequestMapping("passwordInfo")
    public String editpassword() {
        return "editPassword";
    }
    /**
     * 注册请求
     *
     * @param userInfo
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public ReturnMsg register(UserInfo userInfo, BindingResult bindingResult) {
        //ReturnMsg returnMsg = new ReturnMsg();
        if (bindingResult.hasErrors()) {
            Map<String, Object> map = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ReturnMsg.fail().add("fieldError", map);
        } else {
            if (userInfoService.save(userInfo) == 1) {
                /*保存成功*/
                return ReturnMsg.success().add("msg", "注册成功，即将跳转登录...");
            } else {
                return ReturnMsg.fail().add("msg", "注册失败！");
            }
        }
    }

    /**
     * 登录请求
     * @param
     * @param userName
     * @param userPassword
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public ReturnMsg login(HttpSession session,@RequestParam("userName")String userName, @RequestParam("userPassword")String userPassword){
        System.out.println("controoler中的用户名："+userName+"密码："+userPassword);
        boolean login = userInfoService.login(userName, userPassword);
        System.out.println("login"+login);
        if (userInfoService.login(userName, userPassword)){
//            System.out.println();
            session.setAttribute("user",userName);
            return ReturnMsg.success().add("user",session.getAttribute("user"));
            //return ReturnMsg.success().add("msg", "登录成功!");
        }else {
            return ReturnMsg.fail().add("msg", "用户名或密码错误！");
        }
    }

    /**
     * 退出登录功能
     * @param session
     */
    @RequestMapping("/loginout")
    @ResponseBody
    public ReturnMsg loginOut(HttpSession session){
        if(session.getAttribute("user")!=null){
            session.removeAttribute("user");
            return ReturnMsg.success().add("msg", "成功退出！");
        }else {
            return ReturnMsg.fail().add("msg","用户session为空");
        }
    }

    @RequestMapping("/sendcode")
    @ResponseBody
    public ReturnMsg sendSMSCode(@RequestParam("userTelephone")String userTelephone,HttpSession session){
        System.out.println("控制层手机号："+userTelephone);
        if(userInfoService.sendCodeMsg(userTelephone,session)){
            return ReturnMsg.success().add("msg","发送成功！");
        }else {
            return ReturnMsg.fail().add("msg","发送失败，请稍候再试！");
        }
    }


    /**
     * 修改密码前的手机号检查
     * @param userTelephone
     * @return
     */
    @RequestMapping("/checkuserTelephone")
    @ResponseBody
    public ReturnMsg checkuserTelephone(@RequestParam("userTelephone") String userTelephone) {
        /*验证用户名非空，符合验证规则*/
        /*验证用户名是否重复*/
        if (userInfoService.checkUserTelephone(userTelephone)) {
            return ReturnMsg.success().add("msg", "手机号可用！");
        }else {
            return ReturnMsg.fail().add("msg", "手机号不存在，请检查！");
        }
    }

    @RequestMapping("/editUserPassword")
    @ResponseBody
    public ReturnMsg editUserPassword( @RequestParam("userPassword") String userPassword,@RequestParam("userTelephone")String userTelephone,@RequestParam("userCode")String userCode,HttpSession session){
        String code = (String)session.getAttribute("code");
        if(!userCode.equals(code) && code!=null){
            return ReturnMsg.fail().add("msg", "验证码不正确！");
        }else if (code==null){
            return ReturnMsg.fail().add("msg", "验证码已过期，请重新操作！");
        }
        if (userCode.equals(code)){
            if(userInfoService.editPassword(userPassword, userTelephone, userCode)){
                return ReturnMsg.success().add("msg","修改成功!");
            }
        }
        return ReturnMsg.fail().add("msg","修改失败！");
    }
}