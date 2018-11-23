package com.github.sjroom.common;

import org.springframework.web.servlet.ModelAndView;

/**
 * 页面基础类
 */
public abstract class AbstractController extends AbstractBase {

    /**
     * 渲染页面
     *
     * @return
     */
    public ModelAndView render() {
        return new ModelAndView();
    }

    /**
     * 根据page名称，渲染页面
     *
     * @param page
     * @return
     */
    public ModelAndView render(String page) {
        ModelAndView mv = render();
        mv.setViewName(page);
        return mv;
    }

}
