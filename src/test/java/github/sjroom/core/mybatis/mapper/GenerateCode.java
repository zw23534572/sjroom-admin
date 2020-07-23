package github.sjroom.core.mybatis.mapper;

import github.sjroom.core.mybatis.enums.SystemEnum;
import github.sjroom.core.mybatis.generatecode.AutoGenerator;
import github.sjroom.core.mybatis.generatecode.ConfigGenerator;
import github.sjroom.core.mybatis.generatecode.EnumMethodModel;
import github.sjroom.core.utils.Exceptions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * <B>说明：</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-02-24 16-08
 */
public class GenerateCode {

    public static void main(String[] args) {
        // 代码生成器--生成器
        genCode();
        // 枚举类--生成器
        //genEnum();
    }


    /**
     * 代码生成器--生成器
     * 用户需要填写的值
     */
    public static void genCode() {
        Properties props = getProperties();
        ConfigGenerator configGenerator = new ConfigGenerator();
        configGenerator.setDbUrl(props.getProperty("spring.datasource.url"));
        configGenerator.setDbUser(props.getProperty("spring.datasource.username"));
        configGenerator.setDbPassword(props.getProperty("spring.datasource.password"));
        configGenerator.setDbSchema(props.getProperty("spring.datasource.dbSchema"));
        configGenerator.setAuthor(System.getProperty("user.name"));
		configGenerator.setXmlPaging(false);


        //  ---  需要更改的值
        // 包名
        configGenerator.setBasePackage("github.sjroom.admin");
        // 需要生成的表,多个为 "sys_msg_info,sys_msg_info12,sys_msg_info33"
        configGenerator.setGenerateTableName("sys_menu");
        // 替换表名前缀
        configGenerator.setPrefixTableName("sys");
        new AutoGenerator(configGenerator).run();
    }


    /**
     * 枚举类--生成器
     *
     * @className 类名
     * @basePackage 包名
     * @statusList 状态列表
     * <p>
     * 可以测试,
     */
    public static void genEnum() {
        HashMap enumData = new HashMap();
        // 类名
        enumData.put("className", "YNStatusEnum");
        // 包名
        enumData.put("basePackage", "com.dao.test.enum");
        // 状态列表
        List<EnumMethodModel> statusList = new ArrayList<>();
        statusList.add(new EnumMethodModel("YES", 1, "有效"));
        statusList.add(new EnumMethodModel("NO", 0, "无效"));

        enumData.put("statusList", statusList);

        // 开始生成枚举类
        new AutoGenerator(enumData).buildEnumClass();
    }


    /**
     * 获取配置文件
     *
     * @return 配置Props
     */
    private static Properties getProperties() {
        // 读取配置文件
		Resource resource = new ClassPathResource("generator.properties");
        Properties props = new Properties();
        try {
            return PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

}
