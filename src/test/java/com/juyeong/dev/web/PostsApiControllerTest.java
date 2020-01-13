package com.juyeong.dev.web;

import com.juyeong.dev.domain.posts.Posts;
import com.juyeong.dev.domain.posts.PostsRepository;
import com.juyeong.dev.web.dto.PostsResponseDto;
import com.juyeong.dev.web.dto.PostsSaveRequestDto;
import com.juyeong.dev.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void tearDown() {
        postsRepository.deleteAll();
    }

    @Test
    public void savePostTest() {
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();
        String url = "http://localhost:" + port + "/api/v1/posts";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> allPosts = postsRepository.findAll();

        assertThat(allPosts.get(0).getTitle()).isEqualTo(title);
        assertThat(allPosts.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void updatePostTest() {
        Posts savedPost = postsRepository.save(Posts.builder()
                .title("타이틀1")
                .content("콘텐츠1")
                .author("저자저자")
                .build());

        Long updateId = savedPost.getId();
        String expectedTitle = "바뀐타이틀";
        String expectedContent = "바뀐콘텐츠";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder().
                title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> allPosts = postsRepository.findAll();

        assertThat(allPosts.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(allPosts.get(0).getContent()).isEqualTo(expectedContent);
    }

    @Test
    public void deletePostTest() {
        Posts savedPost = postsRepository.save(Posts.builder()
                .title("타이틀1")
                .content("콘텐츠1")
                .author("저자저자")
                .build());

        Long deleteId = savedPost.getId();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(headers);
        String url = "http://localhost:" + port + "/api/v1/posts/" + deleteId;

        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, entity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(postsRepository.findAll()).isEmpty();
    }

    @Test
    public void findByIdTest() {
        Posts savedPost = postsRepository.save(Posts.builder()
                .title("타이틀1")
                .content("콘텐츠1")
                .author("저자저자")
                .build());

        Long findId = savedPost.getId();

        String url = "http://localhost:" + port + "/api/v1/posts/" + findId;

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).contains("타이틀1");
    }
}