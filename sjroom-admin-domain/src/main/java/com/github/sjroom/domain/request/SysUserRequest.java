package com.github.sjroom.domain.request;


import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class SysUserRequest  {

    private Long id;

    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空")
    private String account;


    /**
     * token
     */
    @NotBlank(message = "token不能为空")
    private String token;


}
