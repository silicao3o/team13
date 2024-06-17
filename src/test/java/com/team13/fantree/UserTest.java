package com.team13.fantree;

import com.team13.fantree.entity.User;
import com.team13.fantree.entity.UserStatusEnum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    User user;
    UserStatusEnum.Status status;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .name("테스터1")
                .headline("테스트중")
                .password("1234")
                .build();
    }

    @DisplayName("회원탈퇴 테스트")
    @Test
    void withdrawTest() {
    	// given

    	// when
        user.withDraw();
    	// then
        assertEquals(user.getStatus(), UserStatusEnum.NON_USER);
    }
    
    @DisplayName("업데이트 테스트")
    @Test
    void updateTest() {
    	// given
           user = User.builder()
                   .name("테스트2")
                   .headline("수정")
                   .password("4567")
                   .build();
    	// when

    	// then

        Assertions.assertThat(user.getName()).isEqualTo("테스트2");
        Assertions.assertThat(user.getHeadline()).isEqualTo("수정");
        Assertions.assertThat(user.getPassword()).isEqualTo("4567");
    }
}
