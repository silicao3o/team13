package com.team13.fantree.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team13.fantree.dto.PostRequestDto;
import com.team13.fantree.dto.PostResponseDto;
import com.team13.fantree.entity.Post;
import com.team13.fantree.entity.User;
import com.team13.fantree.exception.MismatchException;
import com.team13.fantree.exception.NotFoundException;
import com.team13.fantree.exception.UserErrorCode;
import com.team13.fantree.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	@Transactional
	public PostResponseDto createPost(PostRequestDto requestDto, User user) {
		Post post = new Post(requestDto.getContent(), user);
		log.info("Creating post: {}", post.getUser().getUsername());
		postRepository.save(post);
		return new PostResponseDto(post);
	}

	public List<PostResponseDto> findAllPosts(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);

		Page<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(pageRequest);
		List<PostResponseDto> postsListDto = new ArrayList<>();
		for (Post post : posts) {
			PostResponseDto postResponseDto = new PostResponseDto(post);
			postsListDto.add(postResponseDto);
		}
		return postsListDto;
	}

	public PostResponseDto findPostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(
			() -> new NotFoundException(UserErrorCode.POST_NOT_FOUND)
		);
		return new PostResponseDto(post);

	}

	@Transactional
	public PostResponseDto updatePost(long id, PostRequestDto requestDto, User user) {
		Post post = postRepository.findById(id).orElseThrow(
			() -> new NotFoundException(UserErrorCode.POST_NOT_FOUND)
		);
		if (post.getUser().getId() != user.getId()) {
			throw new MismatchException(UserErrorCode.USER_MISMATCH_FOR_POST);
		}
		post.setContent(requestDto.getContent());
		postRepository.save(post);
		return new PostResponseDto(post);
	}

	@Transactional
	public String deletePost(long id, User user) {
		Post post = postRepository.findById(id).orElseThrow(
			() -> new NotFoundException(UserErrorCode.POST_NOT_FOUND)
		);
		if (post.getUser().getId() != user.getId()) {
			throw new MismatchException(UserErrorCode.USER_MISMATCH_FOR_POST);
		}
		postRepository.delete(post);
		return "성공했습니다";
	}

	public List<PostResponseDto> findAllPostsPeriod(String startDate, String endDate) {
		List<Post> postList = postRepository.findByCustomCondition(startDate, endDate);
		List<PostResponseDto> postResponseDtoList = new ArrayList<>();
		for (Post post : postList) {
			PostResponseDto postResponseDto = new PostResponseDto(post);
			postResponseDtoList.add(postResponseDto);
		}
		return postResponseDtoList;
	}

}
