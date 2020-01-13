package com.juyeong.dev;

import com.juyeong.dev.domain.posts.Posts;
import com.juyeong.dev.domain.posts.PostsRepository;
import com.juyeong.dev.service.posts.PostsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DevApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevApplication.class, args);
    }

//    @Bean
//    public static CommandLineRunner commandLineRunner(PostsRepository postsRepository) {
//        return args -> {
//            postsRepository.save(Posts.builder().title("test").content("testContent").author("author").build());
//        };
//    }
}
