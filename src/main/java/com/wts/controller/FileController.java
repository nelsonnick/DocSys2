package com.wts.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wts.entity.model.Department;
import com.wts.entity.model.File;
import com.wts.entity.model.User;
import com.wts.interceptor.LoginInterceptor;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;


/**
 * FileController class
 *
 * @author wts
 * @date 2019/3/27
 */
public class FileController extends Controller {
    private static Logger logger = Logger.getLogger(File.class);

    public void query() {
        getResponse().addHeader("Access-Control-Allow-Origin", "http://localhost:8080");
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
        getResponse().addHeader("Access-Control-Allow-Origin", "*");
        Long count = Db.queryLong("SELECT COUNT(*) FROM user LEFT JOIN department ON user.department_id = department.id " +
                "WHERE user.name LIKE '%" + getPara("keyword") + "%' " +
                "OR user.login LIKE '%" + getPara("keyword") + "%' " +
                "OR department.name LIKE '%" + getPara("keyword") + "%' ");
        renderText(count.toString());
    }
    @Before(LoginInterceptor.class)
    public void get() {
        getResponse().addHeader("Access-Control-Allow-Origin", "*");
        renderJson(File.dao.findById(getPara("id")));
    }

    @Before({Tx.class, LoginInterceptor.class})
    public void delete() {
        getResponse().addHeader("Access-Control-Allow-Origin", "*");
        User user = User.dao.findById(getPara("id"));
        user.delete();
        logger.warn("function:" + this.getClass().getSimpleName() + "/Delete;" + ";time:" + new Date() + ";");
        renderText("OK");
    }

    @Before({Tx.class, LoginInterceptor.class})
    public void add() {
        getResponse().addHeader("Access-Control-Allow-Origin", "*");
        User user = new User();
        user.set("name",get("name")).set("login",get("login")).set("password",get("login")).set("state","1").set("department_id",get("departmentId")).save();
        logger.warn("function:" + this.getClass().getSimpleName() + "/Add;" + ";time:" + new Date() + ";");
        renderText("OK");
    }
    @Before({Tx.class, LoginInterceptor.class})
    public void edit() {
        getResponse().addHeader("Access-Control-Allow-Origin", "*");
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
}
