package com.juyeong.dev.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juyeong.dev.service.posts.PostsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsService postsService;

    @Test
    public void indexTest() throws JsonProcessingException {
        String responseBody = this.restTemplate.getForObject("/", String.class);

        assertThat(responseBody).contains("</body>");
    }
}