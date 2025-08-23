package com.acu.hellospring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HellospringApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void hello_default() throws Exception {
		mockMvc.perform(get("/hello"))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello World!"));
	}

	@Test
	void hello_withName() throws Exception {
		mockMvc.perform(get("/hello").param("name", "Spring"))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello Spring!"));
	}
}
