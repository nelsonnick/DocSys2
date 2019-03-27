package com.wts.controller;

import com.jfinal.core.Controller;
import com.wts.entity.model.Department;


/**
 * DepartmentController class
 *
 * @author wts
 * @date 2019/3/27
 */
public class DepartmentController extends Controller {


    public void add() {
        getResponse().addHeader("Access-Control-Allow-Origin", "*");
        Department department = new Department();
        department.set("name",get("name")).set("phone",get("phone")).set("address",get("address")).save();
        renderText("OK");
    }


}
