package com.sfg.course.spring6webapp;

import com.sfg.course.spring6webapp.web.controller.AuthorController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class Spring6WebappApplicationTests {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	AuthorController authorController;

	@Test
	void testDirectly(){
		System.out.println(authorController.check());
	}

	@Test
	void testAppContxtInsideTest(){
		AuthorController authorController=applicationContext.getBean(AuthorController.class);
		System.out.println(authorController.check());
	}
	@Test
	void contextLoads() {
	}

}
