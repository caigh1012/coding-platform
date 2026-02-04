package cn.caigh.coding_platform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("cn.caigh.coding_platform.dao")
@EnableTransactionManagement
public class CodingPlatformApplication {
	public static void main(String[] args) {
		SpringApplication.run(CodingPlatformApplication.class, args);
	}
}
