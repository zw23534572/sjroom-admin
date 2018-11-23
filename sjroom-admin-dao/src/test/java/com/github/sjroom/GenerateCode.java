package com.github.sjroom;


import com.github.sjroom.common.jdbc.generatecode.AutoGenerator;
import com.github.sjroom.common.jdbc.generatecode.ConfigGenerator;

/**
 * <B>说明：</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-02-24 16-08
 */
public class GenerateCode {

    public static void main(String[] args) {
        ConfigGenerator configGenerator = new ConfigGenerator();

        configGenerator.setDbUrl("jdbc:mysql://47.106.103.121:3306/credit-admin");
        configGenerator.setDbUser("root");
        configGenerator.setDbPassword("123456");
        configGenerator.setDbSchema("credit-admin");
        configGenerator.setFunctionPath("bad");
        configGenerator.setBasePackage("com.github.sjroom");
        configGenerator.setGenerateTableName("td_bad_record,td_bad_repay,td_bad_sue");
        configGenerator.setPrefixTableName("td_");
        new AutoGenerator(configGenerator).run();
    }

}
