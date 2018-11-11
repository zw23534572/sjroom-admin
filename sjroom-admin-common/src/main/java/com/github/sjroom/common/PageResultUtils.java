package com.github.sjroom.common;

import com.github.sjroom.common.response.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

/**
 * <B>说明：将pagehelper 的page类 转换为 dzcommon PageResult</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-03-04 17:11
 */
@SuppressWarnings("unchecked")
public class PageResultUtils {

    private static final Logger logger = LoggerFactory.getLogger(PageResultUtils.class);

    /**
     * Page 返回结果,可用于macro.vm showPageFormSubmit1
     * 类转向：com.github.pagehelper.Page 转换为 com.dazong.common.resp.PageResult
     *
     * @param page
     * @return
     */
    public static PageResult convert(Page page) {
        PageResult pageResult = new PageResult();
        pageResult.setPageNo(page.getPageNum());
        pageResult.setPageSize(page.getPageSize());
        pageResult.setTotalPage(page.getPages());
        pageResult.setTotalItem((int) page.getTotal());
        pageResult.setData(page.getResult());
        return pageResult;
    }

    /**
     * PageInfo 返回结果,可用于macro.vm showPageFormSubmit1
     * 类转向：com.github.pagehelper.PageInfo 转换为 com.dazong.common.resp.PageResult
     *
     * @param page
     * @return
     */
    public static PageResult convert(PageInfo page) {
        PageResult pageResult = new PageResult(page.getPageNum(), page.getPageSize(), (int)page.getTotal());
        pageResult.setData(page.getList());
        return pageResult;
    }



}
