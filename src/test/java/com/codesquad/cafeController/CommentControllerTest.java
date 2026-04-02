package com.codesquad.cafeController;

import com.codesquad.user.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
    @DisplayName("Fail to get comments when session is missing")
    void getCommentFailDueToMissingSessionUserData() throws Exception {
        mockMvc.perform(get("/api/comment/1"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("Successfully post a comment and receive DTO")
    void postCommentSuccess() throws Exception {
        User testUser = new User();
        testUser.setId("test1");
        testUser.setPassword("testPassword1");
        testUser.setName("testUser");

        mockMvc.perform(post("/api/comment/1")
                        .sessionAttr("currentUser", testUser)
                        .param("commentContent", "This is a test comment") // Matches @RequestParam
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isCreated()) // Matches status(201)
                .andExpect(jsonPath("$.user_id").value("test1")) // Verifies DTO field
                .andExpect(jsonPath("$.reply").value("This is a test comment"))
                .andDo(print());
    }

    @Test
    @DisplayName("Successfully delete own comment")
    void deleteCommentSuccess() throws Exception {
        User testUser = new User();
        testUser.setId("test1");
        testUser.setPassword("testPassword1");
        testUser.setName("testUser");

        mockMvc.perform(delete("/api/comment/1/12") // {articleId}/{commentId}
                        .sessionAttr("currentUser", testUser))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted")); // Matches "Deleted" body
    }

    @Test
    @DisplayName("Fail to delete someone else's comment")
    void deleteCommentForbidden() throws Exception {
        User wrongUser = new User();
        wrongUser.setId("hacker_id");

        mockMvc.perform(delete("/api/comment/1/12")
                        .sessionAttr("currentUser", wrongUser))
                .andExpect(status().is3xxRedirection()) // Matches status(403)
                .andExpect(content().string("FAILED"));
    }
}
