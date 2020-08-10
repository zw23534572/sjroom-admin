package github.sjroom.admin.code;

public enum MenuTypeEnum {

    /**
     * 目录
     */
    CATALOG(0, "目录"),
    /**
     * 菜单
     */
    MENU(1, "菜单"),
    /**
     * 按钮
     */
    BUTTON(2, "菜单");

    private int value;
    private String name;

    private MenuTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static MenuTypeEnum get(int value) {
        for (MenuTypeEnum item : MenuTypeEnum.values()) {
            if (value == item.value) {
                return item;
            }
        }
        return null;
    }

}
