package com.wts.entity.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BasePower<M extends BasePower<M>> extends Model<M> implements IBean {

	public void setUserId(java.lang.Integer userId) {
		set("user_id", userId);
	}
	
	public java.lang.Integer getUserId() {
		return getInt("user_id");
	}

	public void setDepartmentId(java.lang.Integer departmentId) {
		set("department_id", departmentId);
	}
	
	public java.lang.Integer getDepartmentId() {
		return getInt("department_id");
	}

	public void setFuncId(java.lang.Integer funcId) {
		set("func_id", funcId);
	}
	
	public java.lang.Integer getFuncId() {
		return getInt("func_id");
	}

}
