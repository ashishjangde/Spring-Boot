package org.demo.springsecuritybasic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostDto {
    private long id;
    private String title;
    private String content;
}
