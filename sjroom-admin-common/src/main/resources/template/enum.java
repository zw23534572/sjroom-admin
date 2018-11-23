package ${config.enumPackage};

/**
 * <B>说明：${dbTableInfo.comment}</B><BR>
 *
 * @author ${config.author}
 * @version 1.0.0.
 * @date ${date}
 */
public enum ${upperModelName}${dbTableFieldStatus.property}Enum {
<% for(var item in statusList) { %>
    ${item.fieldName}(${item.code},"${item.msg}")${itemLP.index!=itemLP.size?",":";"}
<% } %>

    private Integer code;
    private String msg;

    ${upperModelName}${dbTableFieldStatus.property}Enum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static String getMsg(Integer code) {
        if (code == null) {
            return null;
        }
        for (${upperModelName}${dbTableFieldStatus.property}Enum en : ${upperModelName}${dbTableFieldStatus.property}Enum.values()) {
            if (code.equals(en.getCode())) {
                return en.getMsg();
            }
        }
        return null;
    }

}
