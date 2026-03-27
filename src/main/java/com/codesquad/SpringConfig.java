package com.codesquad;

import com.codesquad.cafeRepo.JpaArticleRepo;
import com.codesquad.cafeRepo.JpaReplyRepo;
import com.codesquad.cafeRepo.JpaUserRepo;
import com.codesquad.service.ArticleService;
import com.codesquad.service.ReplyService;
import com.codesquad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class SpringConfig {

    private final JpaUserRepo userRepository;
    private final JpaArticleRepo articleRepository;
    private final JpaReplyRepo jpaReplyRepo;

    @Autowired
    public SpringConfig( JpaUserRepo userRepo, JpaArticleRepo articleRepo, JpaReplyRepo jpaReplyRepo){
        this.userRepository = userRepo;
        this.articleRepository = articleRepo;
        this.jpaReplyRepo = jpaReplyRepo;
    }

     @Bean
    public UserService userService(){
        return new UserService(userRepository);
     }

     @Bean
    public ArticleService articleService(){
        return new ArticleService(articleRepository);
     }

     @Bean
    public ReplyService replyService(){
        return new ReplyService(jpaReplyRepo);
     }


}
