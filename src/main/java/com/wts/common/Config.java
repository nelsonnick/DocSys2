package com.wts.common;

import com.jfinal.config.*;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.tx.TxByMethods;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;
import com.wts.controller.*;
import com.wts.entity.model._MappingKit;

/**
 * Config class
 *
 * @author wts
 * @date 2019/2/20
 */
public class Config extends JFinalConfig {

    public static void main(String[] args) {
        UndertowServer.start(Config.class, 81, false);
    }

    @Override
    public void configConstant(Constants me) {
        PropKit.use("a_little_config.txt");
        me.setDevMode(false);
    }

    @Override
    public void configRoute(Routes me) {
//        调试环境
//        String base = "d";
//        开发环境及上线环境
        String base = "";
        me.add(base + "/", MainController.class);
        me.add(base + "/api/department", DepartmentController.class);
        me.add(base + "/api/user", UserController.class);
        me.add(base + "/api/file", FileController.class);
    }

    @Override
    public void configEngine(Engine me) {
    }

    @Override
    public void configPlugin(Plugins me) {
        DruidPlugin dp = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
        dp.setDriverClass("com.mysql.cj.jdbc.Driver");
        me.add(dp);
        ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
        arp.setShowSql(false);
        me.add(arp);
        _MappingKit.mapping(arp);
    }

    @Override
    public void configInterceptor(Interceptors me) {
        me.add(new TxByMethods("save","update"));
    }

    @Override
    public void configHandler(Handlers me) {
        //设置上下文路径
        me.add(new ContextPathHandler("contextPath"));
    }
}