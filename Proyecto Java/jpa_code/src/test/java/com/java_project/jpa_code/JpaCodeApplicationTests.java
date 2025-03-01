package com.java_project.jpa_code;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class JpaCodeApplicationTests {
	@Test
	void contextLoads() {
	}

}
