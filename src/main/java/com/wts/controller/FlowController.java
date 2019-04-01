package com.wts.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wts.entity.model.*;
import com.wts.interceptor.LoginInterceptor;
import com.wts.util.IDNumber;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;


/**
 * FlowController class
 *
 * @author wts
 * @date 2019/3/27
 */
public class FlowController extends Controller {
    private static Logger logger = Logger.getLogger(Flow.class);
    @Before({Tx.class, LoginInterceptor.class})
    public void query() {
        renderJson(Db.paginate(
                getParaToInt("pageCurrent"),
                getParaToInt("pageSize"),
                "SELECT flow.id,file.code,person.name,person.number,person.phone,flow.time,department.name AS department,user.name AS user," +
                " CASE flow.type WHEN '1' THEN '存档' WHEN '2' THEN '提档' WHEN '3' THEN '借档' WHEN '4' THEN '还档' WHEN '5' THEN '重存' ELSE '状态错误' END AS type",
                "FROM flow LEFT JOIN file ON flow.file_id = file.id LEFT JOIN user ON flow.user_id = user.id LEFT JOIN department ON file.department_id = department.id LEFT JOIN person ON file.person_id = person.id " +
                        "WHERE user.name LIKE '%" + getPara("keyword") + "%' " +
                        "OR person.name LIKE '%" + getPara("keyword") + "%' " +
                        "OR department.name LIKE '%" + getPara("keyword") + "%' " +
                        "OR person.number LIKE '%" + getPara("keyword") + "%' " +
                        "OR person.phone LIKE '%" + getPara("keyword") + "%' " +
                        "OR file.code LIKE '%" + getPara("keyword") + "%' " +
                        "ORDER BY flow.id DESC").getList());
    }
    @Before({Tx.class, LoginInterceptor.class})
    public void total() {
        Long count = Db.queryLong("SELECT COUNT(*) FROM flow LEFT JOIN file ON flow.file_id = file.id LEFT JOIN user ON flow.user_id = user.id LEFT JOIN department ON file.department_id = department.id LEFT JOIN person ON file.person_id = person.id " +
                "WHERE user.name LIKE '%" + getPara("keyword") + "%' " +
                "OR person.name LIKE '%" + getPara("keyword") + "%' " +
                "OR department.name LIKE '%" + getPara("keyword") + "%' " +
                "OR person.number LIKE '%" + getPara("keyword") + "%' " +
                "OR person.phone LIKE '%" + getPara("keyword") + "%' " +
                "OR file.code LIKE '%" + getPara("keyword") + "%' ");
        renderText(count.toString());
    }
    @Before({Tx.class, LoginInterceptor.class})
    public void get() {
        renderJson(Db.findFirst("SELECT " +
                "file.code," +
                "file.remark," +
                "file.inside," +
                "file.retire," +
                "file.age," +
                "file.check," +
                "person.name," +
                "person.number," +
                "person.phone," +
                "person.address," +
                "flow.type," +
                "flow.time," +
                "flow.source,"+
                "flow.reason,"+
                "flow.delivery,"+
                "department.name AS department," +
                "user.name AS user " +
                "FROM flow " +
                "LEFT JOIN file ON flow.file_id = file.id " +
                "LEFT JOIN user ON flow.user_id = user.id " +
                "LEFT JOIN department ON file.department_id = department.id " +
                "LEFT JOIN person ON file.person_id = person.id " +
                "WHERE flow.id="+get("id")));
    }
    @Before({Tx.class, LoginInterceptor.class})
    public void edit(){
        Flow flow = Flow.dao.findById(get("id"));
        if (flow == null) {
            renderText("要流转的信息不存在！");
        } else {
            flow.set("reason",get("reason"))
                    .set("source",get("source"))
                    .set("delivery",get("delivery"))
                    .update();
            renderText("OK");
        }
    }
    @Before({Tx.class, LoginInterceptor.class})
    public void printOut() {
        Flow flow = Flow.dao.findById(get("id"));
        User user = User.dao.findById(flow.get("user_id"));
        File file = File.dao.findById(flow.get("file_id"));
        Person person = Person.dao.findById(file.get("person_id"));
        Department department = Department.dao.findById(file.get("department_id"));
        setAttr("code", file.get("code"));
        setAttr("user", user.get("name"));
        setAttr("name", person.get("name"));
        setAttr("number", person.get("number"));
        setAttr("department", department.get("name"));
        setAttr("phone", department.getStr("phone"));
        setAttr("source", flow.getStr("source"));
        setAttr("address", department.getStr("address"));
        SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
        SimpleDateFormat MM = new SimpleDateFormat("MM");
        SimpleDateFormat dd = new SimpleDateFormat("dd");
        setAttr("yyyy", yyyy.format(flow.get("time")));
        setAttr("mm", MM.format(flow.get("time")));
        setAttr("dd", dd.format(flow.get("time")));
        render("/print/printOut.html");
    }
    @Before({Tx.class, LoginInterceptor.class})
    public void printIn() {
        Flow flow = Flow.dao.findById(get("id"));
        User user = User.dao.findById(flow.get("user_id"));
        File file = File.dao.findById(flow.get("file_id"));
        Person person = Person.dao.findById(file.get("person_id"));
        Department department = Department.dao.findById(file.get("department_id"));
        setAttr("code", file.get("code"));
        setAttr("retire", file.get("retire"));
        if (file.getInt("check")==1){
            setAttr("check", "已完成");
        } else if (file.getInt("age")==2){
            setAttr("check", "未完成");
        } else if (file.getInt("age")==3){
            setAttr("check", "整理中");
        } else {
            setAttr("check", "未知");
        }
        setAttr("remark", file.get("remark"));
        if (file.getInt("age")==1){
            setAttr("age", "与身份证一致");
        } else if (file.getInt("age")==2){
            setAttr("age", "早于身份证");
        } else if (file.getInt("age")==3){
            setAttr("age", "晚于身份证");
        } else {
            setAttr("age", "未知");
        }
        if (flow.getInt("delivery")==1){
            setAttr("delivery", "个人");
        } else if (flow.getInt("age")==2){
            setAttr("delivery", "专人");
        } else if (flow.getInt("age")==3){
            setAttr("delivery", "邮寄");
        } else if (flow.getInt("age")==3){
            setAttr("delivery", "其他");
        } else {
            setAttr("delivery", "未知");
        }
        setAttr("user", user.get("name"));
        setAttr("name", person.get("name"));
        setAttr("number", person.get("number"));
        setAttr("sex", IDNumber.get(person.get("number")));
        setAttr("birth", person.get("number").toString().substring(6,12));
        setAttr("department", department.get("name"));
        setAttr("phone", person.getStr("phone"));
        setAttr("source", flow.getStr("source"));
        setAttr("reason", flow.getStr("reason"));
        setAttr("address", person.getStr("address"));
        SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
        SimpleDateFormat MM = new SimpleDateFormat("MM");
        SimpleDateFormat dd = new SimpleDateFormat("dd");
        setAttr("yyyy", yyyy.format(flow.get("time")));
        setAttr("mm", MM.format(flow.get("time")));
        setAttr("dd", dd.format(flow.get("time")));
        render("/print/printIn.html");
    }
    @Before({Tx.class, LoginInterceptor.class})
    public void printBorrow() {
        Flow flow = Flow.dao.findById(get("id"));
        User user = User.dao.findById(flow.get("user_id"));
        File file = File.dao.findById(flow.get("file_id"));
        Person person = Person.dao.findById(file.get("person_id"));
        Department department = Department.dao.findById(file.get("department_id"));
        setAttr("code", file.get("code"));
        setAttr("retire", file.get("retire"));
        setAttr("remark", file.get("remark"));
        if (file.getInt("check")==1){
            setAttr("check", "已完成");
        } else if (file.getInt("age")==2){
            setAttr("check", "未完成");
        } else if (file.getInt("age")==3){
            setAttr("check", "整理中");
        } else {
            setAttr("check", "未知");
        }
        if (file.getInt("age")==1){
            setAttr("age", "与身份证一致");
        } else if (file.getInt("age")==2){
            setAttr("age", "早于身份证");
        } else if (file.getInt("age")==3){
            setAttr("age", "晚于身份证");
        } else {
            setAttr("age", "未知");
        }
        if (flow.getInt("delivery")==1){
            setAttr("delivery", "个人");
        } else if (flow.getInt("age")==2){
            setAttr("delivery", "专人");
        } else if (flow.getInt("age")==3){
            setAttr("delivery", "邮寄");
        } else if (flow.getInt("age")==3){
            setAttr("delivery", "其他");
        } else {
            setAttr("delivery", "未知");
        }
        setAttr("user", user.get("name"));
        setAttr("name", person.get("name"));
        setAttr("number", person.get("number"));
        setAttr("sex", IDNumber.get(person.get("number")));
        setAttr("birth", person.get("number").toString().substring(6,12));
        setAttr("department", department.get("name"));
        setAttr("phone", person.getStr("phone"));
        setAttr("source", flow.getStr("source"));
        setAttr("reason", flow.getStr("reason"));
        setAttr("address", person.getStr("address"));
        SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
        SimpleDateFormat MM = new SimpleDateFormat("MM");
        SimpleDateFormat dd = new SimpleDateFormat("dd");
        setAttr("yyyy", yyyy.format(flow.get("time")));
        setAttr("mm", MM.format(flow.get("time")));
        setAttr("dd", dd.format(flow.get("time")));
        render("/print/printBorrow.html");
    }
}
