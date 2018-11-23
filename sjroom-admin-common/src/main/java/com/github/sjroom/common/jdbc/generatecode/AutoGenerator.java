package com.github.sjroom.common.jdbc.generatecode;

import com.github.sjroom.common.jdbc.entity.DbTableFieldInfo;
import com.github.sjroom.common.jdbc.entity.DbTableInfo;
import com.github.sjroom.common.jdbc.entity.EnumMethodModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <B>说明：</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-02-24 15-14
 */
public class AutoGenerator {

    private static Logger logger = LoggerFactory.getLogger(AutoGenerator.class);

    public AutoGenerator(ConfigGenerator config) {
        this.config = config;
    }

    private ConfigGenerator config;

    /**
     * run 执行
     */
    public void run() {

        /**
         * 检查文件夹是否存在
         */
        File gf = new File(config.getSaveDir());
        if (!gf.exists()) {
            gf.mkdirs();
        }

        /**
         * 开启生成映射关系
         */
        generateCode();

        System.out.println(" generate success! ");

    }


    public void generateCode() {

        DBHelper.getConnection(config
                .getDbDriverName(), config.getDbUrl(), config.getDbUser(), config.getDbPassword());

        String tableNames = config.getGenerateTableName();
        for (String tableName : tableNames.split(",")) {
            System.out.println("开始生成表" + tableName);

            //设置返回的时的数据
            Map<String, Object> data = createRenderData(tableName);
            //根据需求生成，不需要的注掉，模板有问题的话可以自己修改。
            buildMapper(data);
            buildMapperXml(data);
            buildEntity(data);
            buildRequestEntity(data);
            buildResponseEntity(data);
            buildEnum(data);
            buildService(data);
            buildController(data);
            System.out.println(tableName + "生成完成");
        }
    }

    /**
     * 生成 enum
     *
     * @param data
     */
    public void buildEnum(Map<String, Object> data) {
        List<DbTableFieldInfo> dbTableFieldInfoList = (List<DbTableFieldInfo>) data.get("dbTableFieldInfoList");
        List<EnumMethodModel> enumMethodModelList = new ArrayList<>();
        DbTableFieldInfo dbTableFieldStatus = new DbTableFieldInfo();
        try {
            for (DbTableFieldInfo tableFieldInfo : dbTableFieldInfoList) {
                String startsWithContent = "@status";
                if (tableFieldInfo.getComment().startsWith(startsWithContent)) {
                    String comment = tableFieldInfo.getComment().replace(startsWithContent, "");
                    comment = comment.substring(1, comment.indexOf(")"));
                    String[] commentArr = comment.split(",");

                    /**
                     * 有道翻译成中文字段
                     */
                    String englishComment = comment;
                    String[] englishCommentArr = englishComment.split(",");
                    HashMap englishCommentHashMap = new HashMap();
                    for (String item : englishCommentArr) {
                        String[] englishFieldNameArr = item.split("=");
                        if (englishFieldNameArr.length > 0) {
                            englishCommentHashMap.put(englishFieldNameArr[0], englishFieldNameArr[1]);
                        }
                    }

                    for (String item : commentArr) {
                        String[] fieldNameArr = item.split("=");

                        if (fieldNameArr.length > 0) {
                            EnumMethodModel enumMethodModel = new EnumMethodModel();
                            enumMethodModel.setCode(Integer.valueOf(fieldNameArr[0]));
                            enumMethodModel.setMsg(fieldNameArr[1]);
                            enumMethodModel.setFieldName((String) englishCommentHashMap.get(fieldNameArr[0]));
                            enumMethodModelList.add(enumMethodModel);
                        }
                    }
                    BeanUtils.copyProperties(tableFieldInfo, dbTableFieldStatus);
                    dbTableFieldStatus.setProperty(convertUpperCamel(dbTableFieldStatus.getProperty()));
                    break;
                }
            }
        } catch (Exception ex) {
            logger.error("AutoGenerator buildEnum ex:{}", ex.getLocalizedMessage());
        }

        if (enumMethodModelList.size() > 0) {
            data.put("statusList", enumMethodModelList);
            data.put("dbTableFieldStatus", dbTableFieldStatus);
            String upperModelName = String.valueOf(data.get("upperModelName"));
            String templatePath = "enum.java";
            String outputPath = config.getEnumPath() + upperModelName + dbTableFieldStatus.getProperty() + "Enum.java";
            Output.createFile(templatePath, outputPath, data);
        }
    }


    /**
     * 生成 mapper
     *
     * @param data
     */
    public void buildMapper(Map<String, Object> data) {
        String upperModelName = String.valueOf(data.get("upperModelName"));
        String templatePath = "mapper.java";
        String outputPath = config.getMapperPath() + upperModelName + "Mapper.java";
        Output.createFile(templatePath, outputPath, data);
    }

    /**
     * 生成 mapper
     *
     * @param data
     */
    public void buildMapperXml(Map<String, Object> data) {
        String upperModelName = String.valueOf(data.get("upperModelName"));
        String templateXMLPath = "mapper.xml";
        String outputXMLPath = config.getXmlPath() + upperModelName + "Mapper.xml";
        Output.createFile(templateXMLPath, outputXMLPath, data);
    }



    /**
     * 生成entity类
     */
    public void buildEntity(Map<String, Object> data) {
        String upperModelName = String.valueOf(data.get("upperModelName"));
        String templateName = "entity.java";
        String outputName = config.getEntityPath() + upperModelName + ".java";
        Output.createFile(templateName, outputName, data);
    }

    /**
     * 生成request entity类
     */
    public void buildRequestEntity(Map<String, Object> data) {
        String upperModelName = String.valueOf(data.get("upperModelName"));
        String templateName = "requestEntity.java";
        String outputName = config.getRequestEntityPath() + upperModelName + "Request.java";
        Output.createFile(templateName, outputName, data);
    }

    /**
     * 生成response entity类
     */
    public void buildResponseEntity(Map<String, Object> data) {
        String upperModelName = String.valueOf(data.get("upperModelName"));
        String templateName = "responseEntity.java";
        String outputName = config.getResponseEntityPath() + upperModelName + "Response.java";
        Output.createFile(templateName, outputName, data);
    }

    /**
     * 生成 service类
     *
     * @param data
     */
    public void buildService(Map<String, Object> data) {
        String upperModelName = String.valueOf(data.get("upperModelName"));
        String templateName = "service.java";
        String outputName = config.getServicePath() + upperModelName + "Service.java";
        Output.createFile(templateName, outputName, data);

        String templateImplName = "serviceImpl.java";
        String outputImplName = config.getServiceImplPath() + upperModelName + "ServiceImpl.java";
        Output.createFile(templateImplName, outputImplName, data);
    }

    /**
     * 生成controller类
     *
     * @param data
     */
    public void buildController(Map<String, Object> data) {
        String upperModelName = String.valueOf(data.get("upperModelName"));
        String templateName = "controller.java";
        String outputName = config.getControllerPath() + upperModelName + "Controller.java";
        Output.createFile(templateName, outputName, data);
    }

    /**
     * 渲染模板的数据
     *
     * @param tableName
     * @return
     */
    public Map<String, Object> createRenderData(String tableName) {
        Map<String, Object> data = new HashMap();
        //赋值表信息
        DbTableInfo dbTableInfo = getTableInfo(config.getDbSchema(), tableName);
        data.put("dbTableInfo", dbTableInfo);

        //赋值字段信息
        List<DbTableFieldInfo> dbTableFieldInfoList = getEntityList(tableName, dbTableInfo);
        data.put("dbTableFieldInfoList", dbTableFieldInfoList);

        data.put("dbTableName", tableName);
        tableName = tableName.replace(config.getPrefixTableName(),"");
        data.put("tableName", tableName);
        data.put("date", new SimpleDateFormat("yyyy-MM-dd HH:ss").format(new java.util.Date()));
        data.put("upperModelName", convertUpperCamel(tableName));
        data.put("lowerModelName", tableNameConvertLowerCamel(tableName));
        data.put("config", config);
        data.put("orderBy", "${orderBy}");
        data.put("asc", "${asc}");

        return data;
    }

    /**
     * 表名大小写转换
     *
     * @param dbName
     * @return
     */
    private String tableNameConvertLowerCamel(String dbName) {
        StringBuilder result = new StringBuilder();
        if (dbName != null && dbName.length() > 0) {
            dbName = dbName.toLowerCase();
            boolean flag = false;
            for (int i = 0; i < dbName.length(); i++) {
                char ch = dbName.charAt(i);
                if ("_".charAt(0) == ch) {
                    flag = true;
                } else {
                    if (flag) {
                        result.append(Character.toUpperCase(ch));
                        flag = false;
                    } else {
                        result.append(ch);
                    }
                }
            }
        }
        return result.toString();
    }

    private String convertUpperCamel(String dbName) {
        return convertUpperCamel(dbName, true);
    }

    private String convertUpperCamel(String dbName, boolean firstUpper) {
        String camel = tableNameConvertLowerCamel(dbName);
        if (firstUpper) {
            return camel.substring(0, 1).toUpperCase() + camel.substring(1);
        } else {
            return camel.substring(0, 1).toLowerCase() + camel.substring(1);
        }
    }

    private String tableNameConvertMappingPath(String tableName) {
        tableName = tableName.toLowerCase();
        return "/" + (tableName.contains("_") ? tableName.replaceAll("_", "/") : tableName);
    }


    public DbTableInfo getTableInfo(String schemaName, String tableName) {

        String sql = String.format("SELECT * FROM `information_schema`.`TABLES` WHERE TABLE_SCHEMA = '%s' AND TABLE_NAME ='%s'", schemaName, tableName);
        ResultSet rs = DBHelper.execQuery(sql);

        //数据库读取表的结构
        DbTableInfo dbTableInfo = new DbTableInfo();
        try {
            while (rs.next()) {
                String comment = rs.getString("TABLE_COMMENT");
                dbTableInfo.setComment(comment);

                dbTableInfo.setNamespace(config.getEntityPackage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbTableInfo;
    }


    public List<DbTableFieldInfo> getEntityList(String tableName, DbTableInfo dbTableInfo) {

        String sql = String.format("SHOW FULL FIELDS FROM %s", tableName);
        ResultSet rs = DBHelper.execQuery(sql);

        //数据库读取表的结构
        List<DbTableFieldInfo> entityList = new ArrayList();
        DbTableFieldInfo dbTableFieldInfo;
        try {
            while (rs.next()) {
                String columnName = rs.getString("field");
                String columnType = rs.getString("type");
                String comment = rs.getString("comment");
                String isNullable = rs.getString("null");
                String key = rs.getString("key");

                boolean firstUpper = false;
                dbTableFieldInfo = new DbTableFieldInfo();
                dbTableFieldInfo.setColumn(columnName);
                dbTableFieldInfo.setColumnType(columnType.toUpperCase());

                convertColumnLength(dbTableFieldInfo);

                dbTableFieldInfo.setProperty(convertUpperCamel(columnName, firstUpper));
                dbTableFieldInfo.setPropertyType(dataTypeConvertJavaType(dbTableFieldInfo.getColumnType()));
                dbTableFieldInfo.setComment(comment);

                //忽略字段不做validation操作
                if (!ignoreField(columnName)) {
                    if ("YES".equals(isNullable)) {
                        dbTableFieldInfo.setIsNull(Boolean.TRUE);
                    } else {
                        dbTableFieldInfo.setIsNull(Boolean.FALSE);
                    }
                }

                //设置主键
                if ("PRI".equals(key) && dbTableInfo.getKeyColumn() == null) {
                    dbTableInfo.setKeyColumn(dbTableFieldInfo.getColumn());
                    dbTableInfo.setKeyProperty(dbTableFieldInfo.getProperty());
                }
                entityList.add(dbTableFieldInfo);
            }
            dbTableInfo.setDbTableFieldInfoList(entityList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entityList;
    }

    /**
     * 获取字段的长度
     *
     * @return
     */
    public void convertColumnLength(DbTableFieldInfo dbTableFieldInfo) {
        if (dbTableFieldInfo.getColumnType().indexOf("(") >= 0) {
            String columnType = dbTableFieldInfo.getColumnType().substring(0, dbTableFieldInfo.getColumnType().indexOf("("));
            String columnLengh = dbTableFieldInfo.getColumnType().substring(dbTableFieldInfo.getColumnType().indexOf("("), dbTableFieldInfo.getColumnType().indexOf(")"));
            dbTableFieldInfo.setColumnType(columnType);
            dbTableFieldInfo.setColumnLength(columnLengh);
        }
    }

    /**
     * 将dbType 转换为 javaType
     *
     * @return
     */
    private String dataTypeConvertJavaType(String dbType) {
        //全部变成小写开始
        dbType = dbType.toLowerCase();
        if ("bigint".equals(dbType)) {
            return "Long";
        } else if ("varchar".equals(dbType) || "text".equals(dbType) || "char".equals(dbType)) {
            return "String";
        } else if ("int".equals(dbType) || "tinyint".equals(dbType)) {
            return "Integer";
        } else if ("datetime".equals(dbType) || "timestamp".equals(dbType)) {
            return "Date";
        } else if ("bit".equals(dbType)) {
            return "Boolean";
        } else if (dbType.indexOf("decimal") > -1) {
            return "java.math.BigDecimal";
        } else if (dbType.indexOf("blob") > -1) {
            return "byte[]";
        } else if (dbType.indexOf("float") > -1) {
            return "Float";
        } else if (dbType.indexOf("double") > -1) {
            return "Double";
        }
        return "other";
    }

    /**
     * 忽略的字段
     *
     * @param fieldName
     * @return
     */
    private boolean ignoreField(String fieldName) {
        for (String item : config.getIgnoreFieldArr()) {
            if (item.equals(fieldName)) {
                return true;
            }
        }
        return false;
    }


}
