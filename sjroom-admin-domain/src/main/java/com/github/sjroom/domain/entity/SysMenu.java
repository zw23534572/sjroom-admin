package com.github.sjroom.domain.entity;



import com.github.sjroom.jdbc.annotation.TableField;
import com.github.sjroom.jdbc.annotation.TableId;
import com.github.sjroom.jdbc.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_menu")
public class SysMenu {

	@TableId
	private Long id;

	/**
	 * 父菜单ID，一级菜单为0
	 */
	@TableField("parent_id")
	private Long parentId;

	/**
	 * 菜单名称
	 */
	@TableField("name")
	private String name;

	/**
	 * 菜单URL
	 */
	@TableField("url")
	private String url;

	/**
	 * 授权(多个用逗号分隔，如：user:list,user:create)
	 */
	@TableField("perms")
	private String perms;

	/**
	 * 类型     0：目录   1：菜单   2：按钮
	 */
	@TableField("type")
	private Integer type;

	/**
	 * 菜单图标
	 */
	@TableField("icon")
	private String icon;

	/**
	 * 排序
	 */
	@TableField("order_num")
	private Integer orderNum;

}
