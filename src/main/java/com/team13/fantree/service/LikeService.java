package com.team13.fantree.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.team13.fantree.dto.LikeResponseDto;
import com.team13.fantree.entity.Comment;
import com.team13.fantree.entity.ContentEnumType;
import com.team13.fantree.entity.Like;
import com.team13.fantree.entity.Post;
import com.team13.fantree.exception.CommonErrorCode;
import com.team13.fantree.exception.DuplicateException;
import com.team13.fantree.exception.LikeErrorCode;
import com.team13.fantree.exception.MismatchException;
import com.team13.fantree.exception.NotFoundException;
import com.team13.fantree.exception.PostErrorCode;
import com.team13.fantree.exception.SelfLikeException;
import com.team13.fantree.repository.CommentRepository;
import com.team13.fantree.repository.LikeRepository;
import com.team13.fantree.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikeService {

	private final LikeRepository likeRepository;
	private final CommentRepository commentRepository;
	private final PostRepository postRepository;

	public LikeResponseDto createLike(long userId, long contentId, String contentType) {

		duplicateLikeCheck(userId, contentId, contentType);
		isContentsAndSelfLikeCheck(userId, contentId, contentType);

		Like like = likeRepository.save(new Like(userId, contentId, contentType));
		return new LikeResponseDto(like);
	}

	private void duplicateLikeCheck(long userId, long contentId, String contentType) {
		Optional<Like> findLike = likeRepository.findByUserId(userId);
		if (findLike.isPresent()) {
			if (findLike.get().getContentId() == contentId &&
				Objects.equals(findLike.get().getContentType().getType(), contentType)) {
				throw new DuplicateException(LikeErrorCode.DUPLICATE_LIKE);
			}
		}
	}

	private void isContentsAndSelfLikeCheck(long userId, long contentId, String contentType) {
		//해당 댓글이 존재하지 않으면 예외 발생
		if (Objects.equals(contentType, ContentEnumType.COMMENT.getType())) {
			Comment comment = commentRepository.findById(contentId).orElseThrow(
				// comment 예외 발생
			);
			if (comment.getUser().getId() == userId)
				throw new SelfLikeException(LikeErrorCode.SELF_LIKE);
			//해당 게시글이 존재하지 않으면 예외 발생
		} else if (Objects.equals(contentType, ContentEnumType.POST.getType())) {
			Post post = postRepository.findById(contentId).orElseThrow(
				() -> new NotFoundException(PostErrorCode.POST_NOT_FOUND)
			);
			if (post.getUser().getId() == userId)
				throw new SelfLikeException(LikeErrorCode.SELF_LIKE);
		} else {
			throw new NotFoundException(CommonErrorCode.RESOURCE_NOT_FOUND);
		}
	}

	public void deleteLike(long likeId, long UserId) {
		Like like = likeRepository.findById(likeId).orElseThrow(
			() -> new NotFoundException(LikeErrorCode.LIKE_NOT_FOUND)
		);

		if (like.getUserId() != UserId)
			throw new MismatchException(LikeErrorCode.USER_MISMATCH);
		likeRepository.delete(like);
	}
}
