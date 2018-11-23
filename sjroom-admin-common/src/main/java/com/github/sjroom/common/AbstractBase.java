package com.github.sjroom.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 业务最基础的类
 *
 * @author Zhouwei
 */
public abstract class AbstractBase {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 存放当前线程的HttpServletRequest对象
     */
    private static ThreadLocal<HttpServletRequest> httpServletRequestThreadLocal = new ThreadLocal<>();

    /**
     * 存放当前线程的HttpServletResponse对象
     */
    private static ThreadLocal<HttpServletResponse> httpServletResponseThreadLocal = new ThreadLocal<>();

    /**
     * 存放当前线程的Model对象
     */
    private static ThreadLocal<Model> modelThreadLocal = new ThreadLocal<>();

    /**
     * 使用@ModelAttribute注解标识的方法会在每个控制器中的方法访问之前先调用
     */
    @ModelAttribute
    protected void setThreadLocal(HttpServletRequest request, HttpServletResponse response, Model model) {
        httpServletRequestThreadLocal.set(request);
        httpServletResponseThreadLocal.set(response);
        modelThreadLocal.set(model);
    }

    /**
     * 获取当前线程的HttpServletRequest对象
     *
     * @return 当前线程的HttpServletRequest对象
     */
    protected HttpServletRequest getRequest() {
        return httpServletRequestThreadLocal.get();
    }

    /**
     * 获取当前线程的HttpServletResponse对象
     *
     * @return 当前线程的HttpServletResponse对象
     */
    protected HttpServletResponse getResponse() {
        return httpServletResponseThreadLocal.get();
    }

    /**
     * 获取当前线程的HttpSession对象
     *
     * @return 当前线程的HttpSession对象
     */
    protected HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取当前线程的Model对象
     *
     * @return 当前线程的Model对象
     */
    protected Model getModel() {
        return modelThreadLocal.get();
    }

    /**
     * 向 Model 设置属性值
     *
     * @param name  属性名
     * @param value 属性值
     */
    protected void setModelAttribute(String name, Object value) {
        getModel().addAttribute(name, value);
    }

    /**
     * 向 HttpServletRequest 设置属性值
     *
     * @param name  属性名
     * @param value 属性值
     */
    protected void setRequestAttribute(String name, Object value) {
        HttpServletRequest request = this.getRequest();
        request.setAttribute(name, value);
    }

    /**
     * 向 HttpSession 设置属性值
     *
     * @param name  属性名
     * @param value 属性值
     */
    public void setSessionAttribute(String name, Object value) {
        HttpSession session = this.getSession();
        session.setAttribute(name, value);
    }

    /**
     * 从 HttpSession 中获取属性值
     *
     * @param name 属性名
     * @return 属性值
     */
    protected Object getSessionAttribute(String name) {
        HttpSession session = this.getSession();
        Object value = session.getAttribute(name);
        return value;
    }

    /**
     * 从 HttpServletRequest 中获取属性值
     *
     * @param name 属性名
     * @return 属性值
     */
    protected Object getRequestAttribute(String name) {
        HttpServletRequest request = this.getRequest();
        Object value = request.getAttribute(name);
        return value;
    }

    /**
     * 从 HttpServletRequest 中获取属性值
     *
     * @param name 属性名
     * @return 属性值
     */
    protected Object getRequestParameter(String name) {
        HttpServletRequest request = this.getRequest();
        Object value = request.getParameter(name);
        return value;
    }

}
