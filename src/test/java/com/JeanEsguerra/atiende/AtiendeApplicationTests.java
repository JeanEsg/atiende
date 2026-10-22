package com.JeanEsguerra.atiende;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = EnvTestConfig.class)
class AtiendeApplicationTests {

	@Test
	void contextLoads() {
	}

}
