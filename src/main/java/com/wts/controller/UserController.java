package com.wts.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wts.entity.model.Department;
import com.wts.entity.model.User;
import com.wts.interceptor.LoginInterceptor;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;


/**
 * DepartmentController class
 *
 * @author wts
 * @date 2019/3/27
 */
public class UserController extends Controller {
    private static Logger logger = Logger.getLogger(User.class);

    @Before(LoginInterceptor.class)
    public void getDepartment() {
        List<Department> departments = Department.dao.find("SELECT * FROM department");
        String str = "";
        for (Department department : departments) {
            str = str + "{value: '" + department.getId() + "',label:'" + department.getName() + "'},";
        }
        str = str.substring(0, str.length() - 1);
        renderJson("[" + str + "]");
    }
    public void query() {
        renderJson(Db.paginate(
                getParaToInt("pageCurrent"),
                getParaToInt("pageSize"),
                "SELECT user.id, user.name,user.login,department.name AS department," +
                " CASE user.state WHEN '0' THEN '禁用'  WHEN '1' THEN '启用' ELSE '状态错误' END AS state",
                "FROM user LEFT JOIN department ON user.department_id = department.id " +
                        "WHERE user.name LIKE '%" + getPara("keyword") + "%' " +
                        "OR user.login LIKE '%" + getPara("keyword") + "%' " +
                        "OR department.name LIKE '%" + getPara("keyword") + "%' " +
                        "ORDER BY user.id DESC").getList());
    }

    public void total() {
        Long count = Db.queryLong("SELECT COUNT(*) FROM user LEFT JOIN department ON user.department_id = department.id " +
                "WHERE user.name LIKE '%" + getPara("keyword") + "%' " +
                "OR user.login LIKE '%" + getPara("keyword") + "%' " +
                "OR department.name LIKE '%" + getPara("keyword") + "%' ");
        renderText(count.toString());
    }
    @Before(LoginInterceptor.class)
    public void get() {
        renderJson(User.dao.findById(getPara("id")));
    }

    @Before({Tx.class, LoginInterceptor.class})
    public void delete() {
        User user = User.dao.findById(getPara("id"));
        user.delete();
        logger.warn("function:" + this.getClass().getSimpleName() + "/Delete;" + ";time:" + new Date() + ";");
        renderText("OK");
    }

    @Before({Tx.class, LoginInterceptor.class})
    public void add() {
        User user = new User();
        user.set("name",get("name")).set("login",get("login")).set("password",get("login")).set("state","1").set("department_id",get("departmentId")).save();
        logger.warn("function:" + this.getClass().getSimpleName() + "/Add;" + ";time:" + new Date() + ";");
        renderText("OK");
    }
    @Before({Tx.class, LoginInterceptor.class})
    public void edit() {
        User user = User.dao.findById(getPara("id"));
        if (user == null) {
            renderText("要修改的信息不存在！");
        } else {
            user.set("name", get("name"))
                    .set("login", get("login"))
                    .set("department_id", get("departmentId"))
                    .update();
            logger.warn("function:" + this.getClass().getSimpleName() + "/Edit;" + ";time:" + new Date() + ";");
            renderText("OK");
        }
    }
    @Before({Tx.class, LoginInterceptor.class})
    public void active() {
        User user = User.dao.findById(getPara("id"));
        if (user == null) {
            renderText("要修改的信息不存在！");
        } else {
            user.set("state", 1)
                    .update();
            logger.warn("function:" + this.getClass().getSimpleName() + "/Active;" + ";time:" + new Date() + ";");
            renderText("OK");
        }
    }
    @Before({Tx.class, LoginInterceptor.class})
    public void unactive() {
        User user = User.dao.findById(getPara("id"));
        if (user == null) {
            renderText("要修改的信息不存在！");
        } else {
            user.set("state", 0)
                    .update();
            logger.warn("function:" + this.getClass().getSimpleName() + "/Unactive;" + ";time:" + new Date() + ";");
            renderText("OK");
        }
    }
    @Before({Tx.class, LoginInterceptor.class})
    public void reset() {
        User user = User.dao.findById(getPara("id"));
        if (user == null) {
            renderText("要修改的信息不存在！");
        } else {
            user.set("password", user.get("login"))
                    .update();
            logger.warn("function:" + this.getClass().getSimpleName() + "/Reset;" + ";time:" + new Date() + ";");
            renderText("OK");
        }
    }
    @Before(LoginInterceptor.class)
    public void getUser() {
        if (getSessionAttr("user").equals("") || getSessionAttr("user") == null) {
            renderText("无法识别");
        } else {
            renderJson(((User) getSessionAttr("user")));
        }
    }
    @Before(LoginInterceptor.class)
    public void isAdmin() {
        if (((User) getSessionAttr("user")).getLogin().equals("0")){
            renderText("1");
        }else{
            renderText("0");
        }
    }

    @Before(LoginInterceptor.class)
    public void alt() {
        if (getSessionAttr("user").equals("") || getSessionAttr("user") == null) {
            renderText("无法识别用户");
        } else {
            if (!get("pass2").equals(get("pass3"))) {
                renderText("两次输入的密码不一致");
            } else {
                User user = ((User) getSessionAttr("user"));
                if (!user.getPassword().equals(get("pass1"))){
                    renderText("原始密码错误！");
                } else {
                    user.set("password",get("pass2")).update();
                    renderText("OK");
                }
            }
        }

    }
}
