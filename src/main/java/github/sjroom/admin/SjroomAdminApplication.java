package github.sjroom.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author {author}
 */
@SpringBootApplication
@Slf4j
@ComponentScan(basePackages = {"github.sjroom"})
public class SjroomAdminApplication {
	public static void main(String[] args) {
		SpringApplication.run(SjroomAdminApplication.class, args);
	}
}
