package com.codesquad;

import com.codesquad.cafeRepo.JpaArticleRepo;
import com.codesquad.cafeRepo.JpaReplyRepo;
import com.codesquad.cafeRepo.JpaUserRepo;
import com.codesquad.service.ArticleService;
import com.codesquad.service.ReplyService;
import com.codesquad.service.UserService;
import com.codesquad.user.LoginInterceptor;
import com.codesquad.user.LoginRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableAspectJAutoProxy
public class SpringConfig implements WebMvcConfigurer {

    private final JpaUserRepo userRepository;
    private final JpaArticleRepo articleRepository;
    private final JpaReplyRepo jpaReplyRepo;

    @Autowired
    public SpringConfig( JpaUserRepo userRepo, JpaArticleRepo articleRepo, JpaReplyRepo jpaReplyRepo){
        this.userRepository = userRepo;
        this.articleRepository = articleRepo;
        this.jpaReplyRepo = jpaReplyRepo;
    }

    @Override
    public void addInterceptors(InterceptorRegistry reg){
        reg.addInterceptor(new LoginInterceptor()).addPathPatterns("/**");
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
