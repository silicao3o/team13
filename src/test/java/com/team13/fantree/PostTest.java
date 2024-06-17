package com.team13.fantree;


import com.team13.fantree.entity.Post;
import com.team13.fantree.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.team13.fantree.entity.Post.*;

public class PostTest {

    User user;
    @BeforeEach
    void setUp() {
        user = User.builder().username("테스터").build();
    }

    @Test
    @DisplayName("게시글이 작성되는지 테스트")
    void setPost(){
        // given
        Post post = Post.builder().content("테스트1").user(user).build();

        //when, then
       Assertions.assertThat(post.getContent()).isEqualTo("테스트1");
       Assertions.assertThat(post.getUser()).isEqualTo(user);
    }
}
