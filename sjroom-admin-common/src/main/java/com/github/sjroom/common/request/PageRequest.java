package com.github.sjroom.common.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.sjroom.common.xss.SQLFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * page 分页的入参
 *
 * @author ZhouWei
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PageRequest {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 当前页码
     */
    private Integer pageNo;
    /**
     * 每页条数
     */
    private Integer pageSize;
    /**
     * 排序名称
     */
    private String orderBy;
    /**
     * 排序规则
     */
    private String order;
    /**
     * 开始时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date startDate;
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date endDate;
    /**
     * 查询的关键字
     */
    private String key;
    /**
     * 创建的用户
     */
    private String createUser;

    //参数
    private Map condition;

    public PageRequest() {
        this.pageNo = 1;
        this.pageSize = 10;
    }

    public PageRequest(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public PageRequest(int pageNo, int pageSize, String orderBy, String order) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        if (!StringUtils.isEmpty(orderBy)) {
            this.orderBy = SQLFilter.sqlInject(orderBy);
            this.order = SQLFilter.sqlInject(order);
        }
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null){
            this.pageSize = 10;
        }else{
            this.pageSize = pageSize;
        }

    }
    public void setPageNo(Integer pageNo) {
        if (pageNo == null){
            this.pageNo = 1;
        } else {
            this.pageNo = pageNo;
        }
    }
}
