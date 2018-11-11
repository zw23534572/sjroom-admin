package com.github.sjroom.controller;


import com.github.sjroom.common.AbstractController;
import org.springframework.web.bind.annotation.*;
/**
 * <B>说明：特定交易时间管理</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-03-01 14-03
 */
@RestController
@RequestMapping("/test/")
public class TestController extends AbstractController {

    @GetMapping("test")
    public Object test6() {
        return null;
    }
}
