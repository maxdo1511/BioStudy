package ru.hbb.learnstudio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.hbb.learnstudio.Auth.JwtCore;

@SpringBootApplication
public class LearnstudioApplication {

	private JwtCore jwtCore;


	public void setJwtCore(JwtCore jwtCore) {
		this.jwtCore = jwtCore;
	}

	public static void main(String[] args) {
		SpringApplication.run(LearnstudioApplication.class, args);
	}

}
