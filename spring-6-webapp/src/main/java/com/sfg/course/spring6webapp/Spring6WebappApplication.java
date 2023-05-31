package com.sfg.course.spring6webapp;

import com.sfg.course.spring6webapp.web.controller.AuthorController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Spring6WebappApplication {

	public static void main(String[] args) {

		ApplicationContext cntxt= SpringApplication.run(Spring6WebappApplication.class, args);
		AuthorController authorController=cntxt.getBean(AuthorController.class);
		System.out.println("Inside main class");
		System.out.println(authorController.check());
	}

}
