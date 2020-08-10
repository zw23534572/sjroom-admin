package github.sjroom.admin.code;

public enum YesOrNoEnum {

    YES(1, "是"),
    NO(0, "否");

    private int value;
    private String name;

    private YesOrNoEnum(int value, String name) {
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

    public static YesOrNoEnum get(int value) {
        for (YesOrNoEnum item : YesOrNoEnum.values()) {
            if (value == item.value) {
                return item;
            }
        }
        return null;
    }

}
