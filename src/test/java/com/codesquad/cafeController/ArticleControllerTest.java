package com.codesquad.cafeController;

import com.codesquad.article.Article;
import com.codesquad.service.ArticleService;
import com.codesquad.user.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ArticleService articleService;

    @Test
    @DisplayName("GET /question should return submitQuestion view when user is logged in")
    void testGetQuestionFormLoggedIn() throws Exception {
        // Create a dummy user to act as the logged-in user
        User dummyUser = new User();
        dummyUser.setId("testUser");

        mockMvc.perform(get("/qna/question")
                        .sessionAttr("currentUser", dummyUser)) // <-- THIS simulates the session!
                .andExpect(status().isOk())
                .andExpect(view().name("qna/submitQuestion"))
                .andExpect(model().attributeExists("article"));
    }

    @Test
    @DisplayName("GET /question should redirect to login if no user in session")
    void testGetQuestionFormNotLoggedIn() throws Exception {
        // Do NOT pass a session attribute here
        mockMvc.perform(get("/qna/question"))
                .andExpect(status().is3xxRedirection()) // Expect a redirect
                .andExpect(redirectedUrl("/user/login")); // Expect redirect to login page
    }

    @Test
    @DisplayName("POST /question should save article and redirect when user is logged in")
    void testPostQuestionFormLoggedIn() throws Exception {
        // 1. Arrange: Create a dummy user to act as the logged-in session user
        User dummyUser = new User();
        dummyUser.setId("testUser123");

        // 2. Act & Assert: Perform the POST request with the session and form parameters
        mockMvc.perform(post("/qna/question")
                        .sessionAttr("currentUser", dummyUser) // <-- Injects the session!
                        .param("title", "How to test Spring Boot?") // Simulates form input
                        .param("content", "I am learning MockMvc."))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/qna/"));

        // 3. Verify that the service layer was actually called to save the article
        verify(articleService).putNewArticle(any(User.class),any(Article.class));
    }
}
