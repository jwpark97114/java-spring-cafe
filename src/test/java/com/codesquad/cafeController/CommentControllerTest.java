package com.codesquad.cafeController;

import com.codesquad.user.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCommentsWithDTOAndFakeSession() throws Exception{
        User testUser = new User();
        testUser.setId("test1");
        testUser.setPassword("testPassword1");
        testUser.setName("testUser");

        mockMvc.perform(get("/api/comment/1")
                .sessionAttr("currentUser",testUser)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].reply").exists())
                .andExpect(jsonPath("$[0].user_id").exists())
                .andExpect(jsonPath("$[0].password").doesNotExist())
                .andDo(print());
    }

    @Test
    void getCommentFailDueToMissingSessionUserData() throws Exception{
        mockMvc.perform(get("/api/comment/1"))
                .andExpect(status().is3xxRedirection());
    }
}
