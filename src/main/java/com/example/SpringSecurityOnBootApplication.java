package com.example;

import com.example.domain.User;
import com.example.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.Filter;

@SpringBootApplication
@Configuration
@ComponentScan
public class SpringSecurityOnBootApplication {

	private static final Logger logger = LoggerFactory.getLogger(SpringSecurityOnBootApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityOnBootApplication.class, args);
	}

	@Bean
	public InitializingBean insertDefaultUsers() {
		return new InitializingBean() {
			@Autowired
			private UserRepository userRepository;

			@Override
			public void afterPropertiesSet() {
				String password = new BCryptPasswordEncoder().encode("test");
				logger.debug("[Password]" + password);
				addUser("test1", password);
				addUser("test2", password);
				addUser("test3", null);
			}

			private void addUser(String userName, String password) {
				User user = new User();
				user.setUserName(userName);
				user.setEncodedPassword(password);
				user.setLoginProvider(0);
				userRepository.save(user);
			}
		};
	}

	@Bean
	public Filter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return characterEncodingFilter;
	}

}
