package com.wts.entity.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * Generated by JFinal, do not modify this file.
 * <pre>
 * Example:
 * public void configPlugin(Plugins me) {
 *     ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
 *     _MappingKit.mapping(arp);
 *     me.add(arp);
 * }
 * </pre>
 */
public class _MappingKit {
	
	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("department", "id", Department.class);
		arp.addMapping("file", "id", File.class);
		arp.addMapping("flow", "id", Flow.class);
		arp.addMapping("person", "id", Person.class);
		arp.addMapping("personchange", "id", Personchange.class);
		arp.addMapping("user", "id", User.class);
	}
}

