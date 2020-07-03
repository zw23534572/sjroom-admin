package github.sjroom.core.mybatis.mapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderTest {


    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        System.out.println("encoder: " + bCryptPasswordEncoder.encode("manson"));
    }
}
