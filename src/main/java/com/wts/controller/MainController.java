package com.wts.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.wts.entity.model.User;
import com.wts.interceptor.LoginInterceptor;


/**
 * MainController class
 *
 * @author wts
 * @date 2019/2/20
 */
public class MainController extends Controller {

    public void index() {
        render("/login/index.html");
    }
    public void home() {
        render("/DocSys2/dist/index.html");
    }
    public void login() {
        getResponse().addHeader("Access-Control-Allow-Origin", "*");
        if (get("login").equals("0") && get("pass").equals("0")){
            User user = new User();
            user.setLogin(get("login"));
            user.setPassword(get("pass"));
            setSessionAttr("user", user);
            redirect("/home");
        }else{
            User user = User.dao.findFirst("SELECT * FROM user WHERE login=? AND password=? AND state = 1", get("login"), get("pass"));
            if (user != null) {
                setSessionAttr("user", user);
                redirect("/home");
            } else {
                redirect("/");
            }
        }
    }
    public void logout() {
        setSessionAttr("user", null);
        redirect("/");
    }
}
