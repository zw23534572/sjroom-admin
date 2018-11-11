package com.github.sjroom.common;


/**
 * 常量
 *
 * @author manson
 */
public class Constant {
    /**
     * 菜单类型
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        private MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


    /**
     * 模板角色:研发部门开发人员
     */
    public static final Long DEFUALT_ROLE = 0l;
    /**
     * 7天之内过期
     */
    public final static int EXPIRE = 3600 * 24 * 7 * 1000;

    public static final String PROJECT_REDIS_ROLE_MENU = "ROLE:MENU:";

    public static final String PROJECT_REDIS_ROLE_PERMS = "perms:role:";

    public static final String USER_INFO = "USER_INFO";
}
