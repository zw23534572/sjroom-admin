package github.sjroom.core.mybatis.mapper;

import github.sjroom.core.mybatis.generatecode.EnumGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * <B>说明：</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-02-24 16-08
 */
public class EnumGenerateCode {


    public static void main(String[] args) {
        // 代码生成器--生成器
        //genCode();
        // 枚举类--生成器
        //genEnum();

        // github.sjroom.core.mybatis.mapper.SystemCode 请更改为需要转换的枚举

        List<EnumGenerator.OptionalEnum> codeList = new ArrayList<>();
        for (SystemCode typeEnum : SystemCode.values()) {
            EnumGenerator.OptionalEnum optionalEnum = new EnumGenerator.OptionalEnum();
            optionalEnum.setCode(typeEnum.getCode());
            optionalEnum.setMsg(typeEnum.getMsg());
            codeList.add(optionalEnum);
        }
        //参数1: 项目的简明: 比如 SYS,HRM,等等.
        //参数2: 枚举类的code与msg值列表
        new EnumGenerator("SYS", codeList)
            .convertApiCodeToSql();
    }

}
