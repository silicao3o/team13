package com.team13.fantree.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
	private Long postId;
	private String content;

}
