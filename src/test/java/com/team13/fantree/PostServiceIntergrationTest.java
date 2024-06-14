package com.team13.fantree;

import com.team13.fantree.dto.PostRequestDto;
import com.team13.fantree.dto.PostResponseDto;
import com.team13.fantree.entity.User;
import com.team13.fantree.repository.UserRepository;
import com.team13.fantree.service.PostService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.class)
public class PostServiceIntergrationTest {

    @Autowired
    PostService postService;
    @Autowired
    UserRepository userRepository;

    User user;

    PostRequestDto createPost = null;

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

}
