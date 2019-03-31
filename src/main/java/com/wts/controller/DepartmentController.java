package com.wts.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wts.entity.model.Department;
import com.wts.interceptor.LoginInterceptor;
import org.apache.log4j.Logger;

import java.util.Date;


/**
 * DepartmentController class
 *
 * @author wts
 * @date 2019/3/27
 */
public class DepartmentController extends Controller {
    private static Logger logger = Logger.getLogger(Department.class);

    public void query() {
        renderJson(Db.paginate(
                getParaToInt("pageCurrent"),
                getParaToInt("pageSize"),
                "SELECT department.id, department.name,department.phone,department.address",
                "FROM department " +
                        "WHERE department.name LIKE '%" + getPara("keyword") + "%' " +
                        "OR department.phone LIKE '%" + getPara("keyword") + "%' " +
                        "OR department.address LIKE '%" + getPara("keyword") + "%' " +
                        "ORDER BY department.id DESC").getList());
    }

    public void total() {
        Long count = Db.queryLong("SELECT COUNT(*) FROM department " +
                "WHERE department.name LIKE '%" + getPara("keyword") + "%' " +
                "OR department.phone LIKE '%" + getPara("keyword") + "%' " +
                "OR department.address LIKE '%" + getPara("keyword") + "%' ");
        renderText(count.toString());
    }
    @Before(LoginInterceptor.class)
    public void get() {
        renderJson(Department.dao.findById(getPara("id")));
    }

    @Before({Tx.class, LoginInterceptor.class})
    public void delete() {
        Department d = Department.dao.findById(getPara("id"));
        d.delete();
        logger.warn("function:" + this.getClass().getSimpleName() + "/Delete;" + ";time:" + new Date() + ";");
        renderText("OK");
    }

    @Before({Tx.class, LoginInterceptor.class})
    public void add() {
        Department department = new Department();
        department.set("name",get("name")).set("phone",get("phone")).set("address",get("address")).save();
        logger.warn("function:" + this.getClass().getSimpleName() + "/Add;" + ";time:" + new Date() + ";");
        renderText("OK");
    }
    @Before({Tx.class, LoginInterceptor.class})
    public void edit() {
        Department department = Department.dao.findById(getPara("id"));
        if (department == null) {
            renderText("要修改的信息不存在！");
        } else {
            department.set("name", get("name"))
                    .set("phone", get("phone"))
                    .set("address", get("address"))
                    .update();
            logger.warn("function:" + this.getClass().getSimpleName() + "/Edit;" + ";time:" + new Date() + ";");
            renderText("OK");
        }
    }
}
