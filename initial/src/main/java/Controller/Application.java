package Controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import Services.LoginService;
import connectDB.ConnectDB;
import connectDB.LoginJDBC;

@SpringBootApplication
public class Application extends SpringBootServletInitializer { 
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
	
	public static void main(String[] args)throws Exception {
		SpringApplication.run(Application.class, args);

		System.out.println("Let's inspect the beans provided by Spring Boot:");
	}

	@Bean
	public LoginService login() {
		return new LoginService();
	}

	@Bean
	public ConnectDB connect() {
		return new ConnectDB();
	}

	@Bean
	public LoginJDBC loginjdbc() {
		return new LoginJDBC();
	}

	@Bean
	JdbcTemplate jdbcTemplate() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(datasource());
		return jdbcTemplate;
	}

	@Bean
	DriverManagerDataSource datasource() {
		DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setDriverClassName("com.mysql.jdbc.Driver");
		datasource.setUrl("jdbc:mysql://localhost/testloginservice?characterEncoding=utf-8"); // *** spring?characterEncoding=utf-8 กำหนดส่งกลับภาษาไทยได้ ***
		datasource.setUsername("root");
		datasource.setPassword("");
		return datasource;
	}
}
