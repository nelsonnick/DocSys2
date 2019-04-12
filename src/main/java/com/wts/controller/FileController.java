package com.wts.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wts.entity.model.*;
import com.wts.interceptor.LoginInterceptor;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * FileController class
 *
 * @author wts
 * @date 2019/3/27
 */
public class FileController extends Controller {
    private static Logger logger = Logger.getLogger(File.class);
    @Before({Tx.class, LoginInterceptor.class})
    public void query() {
        renderJson(Db.paginate(
                getParaToInt("pageCurrent"),
                getParaToInt("pageSize"),
                "SELECT file.id, file.code,file.retire," +
                        "person.id AS person,person.name,person.number,person.phone,person.address," +
                        "department.name AS department,department.id AS did," +
                        "CASE file.age WHEN '1' THEN '一致' WHEN '2' THEN '早于' WHEN '3' THEN '晚于' WHEN '4' THEN '未知' ELSE '状态错误' END AS age," +
                        "CASE file.`check` WHEN '1' THEN '已完成' WHEN '2' THEN '未完成' WHEN '3' THEN '整理中' WHEN '4' THEN '未知' ELSE '状态错误' END AS `check`," +
                        "CASE file.state WHEN '0' THEN '提档' WHEN '1' THEN '在档' WHEN '2' THEN '借档' ELSE '状态错误' END AS state",
                "FROM file LEFT JOIN department ON file.department_id = department.id LEFT JOIN person ON file.person_id = person.id " +
                        "WHERE person.name LIKE '%" + getPara("keyword") + "%' " +
                        "OR person.number LIKE '%" + getPara("keyword") + "%' " +
                        "OR person.phone LIKE '%" + getPara("keyword") + "%' " +
                        "OR file.code LIKE '%" + getPara("keyword") + "%' " +
                        "ORDER BY file.id DESC").getList());
    }
    @Before({Tx.class, LoginInterceptor.class})
    public void total() {
        Long count = Db.queryLong("SELECT COUNT(*) FROM file LEFT JOIN department ON file.department_id = department.id LEFT JOIN person ON file.person_id = person.id " +
                "WHERE person.name LIKE '%" + getPara("keyword") + "%' " +
                "OR person.number LIKE '%" + getPara("keyword") + "%' " +
                "OR person.phone LIKE '%" + getPara("keyword") + "%' " +
                "OR file.code LIKE '%" + getPara("keyword") + "%' ");
        renderText(count.toString());
    }
    @Before({Tx.class, LoginInterceptor.class})
    public void get() {
        renderJson(Db.findFirst("SELECT file.id, file.code,file.age,file.check,file.state,file.inside,file.remark,file.retire," +
                        "person.id AS person,person.name,person.number,person.phone,person.address," +
                        "department.name AS department " +
                "FROM file LEFT JOIN department ON file.department_id = department.id LEFT JOIN person ON file.person_id = person.id " +
                        "WHERE file.id =" + get("id")));
    }
    @Before({Tx.class, LoginInterceptor.class})
    public void add() {
        if (Person.dao.find("SELECT * FROM person WHERE number="+get("number")).size() == 0){
            Person person = new Person();
            person.set("name",get("name"))
                    .set("number",get("number"))
                    .set("phone",get("phone"))
                    .set("address",get("address"));
            if(File.dao.find("SELECT * FROM file WHERE code="+get("code")+" AND department_id=" +((User) getSessionAttr("user")).get("department_id")).size() ==0){
                person.save();
                File file = new File();
                file.set("code",get("code"))
                        .set("age",get("age"))
                        .set("state",1)
                        .set("check",get("check"))
                        .set("inside",get("inside"))
                        .set("retire",get("retire"))
                        .set("remark",get("remark"))
                        .set("person_id",person.get("id").toString())
                        .set("department_id", ((User) getSessionAttr("user")).get("department_id")).save();
                Flow flow = new Flow();
                flow.set("reason",get("reason"))
                        .set("type",1)
                        .set("source",get("source"))
                        .set("delivery",get("delivery"))
                        .set("source",get("source"))
                        .set("time",new Date())
                        .set("file_id",file.get("id"))
                        .set("user_id", ((User) getSessionAttr("user")).get("id")).save();
                renderText("OK");
            }else{
                renderText("该档案编号在当前部门已存在");
            }
        }else{
            Person person = Person.dao.findFirst("SELECT * FROM person WHERE number="+get("number"));
            if(File.dao.find("SELECT * FROM file WHERE code="+get("code")+" AND department_id=" +((User) getSessionAttr("user")).get("department_id")).size() ==0){
                File file = new File();
                file.set("code",get("code"))
                        .set("age",get("age"))
                        .set("state",1)
                        .set("check",get("check"))
                        .set("inside",get("inside"))
                        .set("retire",get("retire"))
                        .set("remark",get("remark"))
                        .set("person_id",person.get("id").toString())
                        .set("department_id", ((User) getSessionAttr("user")).get("department_id")).save();
                Flow flow = new Flow();
                flow.set("reason",get("reason"))
                        .set("type",1)
                        .set("source",get("source"))
                        .set("delivery",get("delivery"))
                        .set("source",get("source"))
                        .set("time",new Date())
                        .set("file_id",file.get("id"))
                        .set("user_id", ((User) getSessionAttr("user")).get("id")).save();
                renderText("OK");
            }else{
                renderText("该档案编号在当前部门已存在");
            }
        }
    }
    @Before({Tx.class, LoginInterceptor.class})
    public void edit() {
        File file = File.dao.findById(get("id"));
        if (file == null) {
            renderText("要修改的档案不存在！");
        } else {
            Person person = Person.dao.findById(file.getPersonId());
            if (person == null) {
                renderText("要修改的人员不存在！");
            }else{
                Personchange pc = new Personchange();
                pc.set("time", new Date())
                        .set("person_id",person.get("id"))
                        .set("user_id",((User) getSessionAttr("user")).get("id"))
                        .set("content",person.toJson())
                        .save();
                person.set("name",get("name"))
                        .set("number",get("number"))
                        .set("phone",get("phone"))
                        .set("address",get("address"))
                        .update();
                file.set("code",get("code"))
                        .set("age",get("age"))
                        .set("check",get("check"))
                        .set("inside",get("inside"))
                        .set("remark",get("remark"))
                        .set("retire",get("retire"))
                        .update();
            }
            logger.warn("function:" + this.getClass().getSimpleName() + "/Edit;" + ";time:" + new Date() + ";");
            renderText("OK");
        }
    }
    @Before({Tx.class, LoginInterceptor.class})
    public void borrow() {
        File file = File.dao.findById(get("id"));
        if (file == null) {
            renderText("要流转的档案不存在！");
        } else {
            file.set("state",2).update();
            Flow flow = new Flow();
            flow.set("reason",get("reason"))
                    .set("type",3)
                    .set("source",get("source"))
                    .set("delivery",get("delivery"))
                    .set("source",get("source"))
                    .set("time",new Date())
                    .set("file_id",file.get("id"))
                    .set("user_id", ((User) getSessionAttr("user")).get("id")).save();
            renderText("OK");
        }
    }
    @Before({Tx.class, LoginInterceptor.class})
    public void back() {
        File file = File.dao.findById(get("id"));
        if (file == null) {
            renderText("要流转的档案不存在！");
        } else {
            file.set("state",1).update();
            Flow flow = new Flow();
            flow.set("reason",get("reason"))
                    .set("type",4)
                    .set("source",get("source"))
                    .set("delivery",get("delivery"))
                    .set("source",get("source"))
                    .set("time",new Date())
                    .set("file_id",file.get("id"))
                    .set("user_id", ((User) getSessionAttr("user")).get("id")).save();
            renderText("OK");
        }
    }
    @Before({Tx.class, LoginInterceptor.class})
    public void out() {
        File file = File.dao.findById(get("id"));
        if (file == null) {
            renderText("要流转的档案不存在！");
        } else {
            file.set("state",0).update();
            Flow flow = new Flow();
            flow.set("reason",get("reason"))
                    .set("type",2)
                    .set("source",get("source"))
                    .set("delivery",get("delivery"))
                    .set("source",get("source"))
                    .set("time",new Date())
                    .set("file_id",file.get("id"))
                    .set("user_id", ((User) getSessionAttr("user")).get("id")).save();
            renderText("OK");
        }
    }
    @Before({Tx.class, LoginInterceptor.class})
    public void resave() {
        File file = File.dao.findById(get("id"));
        if (file == null) {
            renderText("要流转的档案不存在！");
        } else {
            file.set("state",1).update();
            Flow flow = new Flow();
            flow.set("reason",get("reason"))
                    .set("type",5)
                    .set("source",get("source"))
                    .set("delivery",get("delivery"))
                    .set("source",get("source"))
                    .set("time",new Date())
                    .set("file_id",file.get("id"))
                    .set("user_id", ((User) getSessionAttr("user")).get("id")).save();
            renderText("OK");
        }
    }
    @Before({Tx.class, LoginInterceptor.class})
    public void getUser() {
        renderText(Department.dao.findById(((User) getSessionAttr("user")).getDepartmentId()).getName());
    }
    @Before({Tx.class, LoginInterceptor.class})
    public void check() {
        if (Db.find("SELECT person.number FROM file LEFT JOIN person ON file.person_id = person.id WHERE (file.state = 1 OR file.state = 2) AND person.number ="+get("number")).size() == 0){
            renderText("OK");
        }else{
            renderText("EXIST");
        }
    }
}
