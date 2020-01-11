package com.juyeong.dev.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class PostsUpdateRequestDto {
    private String title;
    private String content;

    public PostsUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}