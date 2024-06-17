package com.team13.fantree;

import com.team13.fantree.dto.PostRequestDto;
import com.team13.fantree.dto.PostResponseDto;
import com.team13.fantree.entity.Post;
import com.team13.fantree.entity.User;
import com.team13.fantree.repository.PostRepository;
import com.team13.fantree.repository.UserRepository;
import com.team13.fantree.service.PostService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.class)
public class PostServiceIntergrationTest {

    @Autowired
    PostService postService;
    @Autowired
    UserRepository userRepository;

    User user;

    PostRequestDto createPost = null;
    @Autowired
    private PostRepository postRepository;

    @Test
    @Order(1)
    @DisplayName("게시글 작성")
    void createPost() {
        // given
        String content = "테스트1";

        PostRequestDto requestDto = new PostRequestDto(
                content
        );
        user = userRepository.findById(1L).get();

        //when
        PostResponseDto post = postService.createPost(requestDto, user);

        //then
        assertNotNull(post.getId());
        assertEquals(content, post.getContent());
    }

    @Test
    @Order(2)
    @DisplayName("게시글 수정")
    void updatePost() {
        // given
        String content = "업데이트";

        PostRequestDto requestDto = new PostRequestDto(
                content
        );
        user = userRepository.findById(1L).get();

        //when
        PostResponseDto post = postService.updatePost(user.getId(), requestDto, user);

        //then
        assertNotNull(post.getId());
        assertEquals(content, post.getContent());
    }

    @Test
    @Order(3)
    @DisplayName("게시글 조회")
    void getPost() {
        //given
        user = userRepository.findById(1L).get();
        //when
        Post post = postRepository.findById(1L).get();
        //then
        assertNotNull(post);
    }

    @Test
    @Order(4)
    @DisplayName("게시글 삭제")
    void deletePost() {
        // given
        user = userRepository.findById(1L).get();
        //when
        String resultMessage = postService.deletePost(user.getId(), user);

        //then
        assertEquals("성공했습니다", resultMessage);
    }
}
