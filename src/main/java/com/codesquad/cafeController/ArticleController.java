package com.codesquad.cafeController;

import com.codesquad.article.Article;
import com.codesquad.manager.PagingManager;
import com.codesquad.service.ArticleService;
import com.codesquad.service.ReplyService;
import com.codesquad.user.LoginRequired;
import com.codesquad.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/qna")
public class ArticleController {

    private final ArticleService service;

    @Autowired
    public ArticleController(ArticleService as, ReplyService rs){
        this.service = as;
    }

    @GetMapping("/")
    public String getQna(@RequestParam(defaultValue = "1") int page,  Model model){
        Page<Article> articlePage = this.service.getArticlesPage(page - 1, 15);
        List<Article> articles = articlePage.getContent();
        PagingManager pageManager = new PagingManager(page, articlePage.getTotalPages(), 5);
        model.addAttribute("articles", articles);
        model.addAttribute("currentPage", page);
        model.addAttribute("pagingManager", pageManager);
        return "qna/qnaList";
    }



    @LoginRequired
    @GetMapping("/question")
    public String getQuestionForm(Model model){
        Article targetArticle = new Article();
        model.addAttribute("article", targetArticle);
        model.addAttribute("formActionUrl", "/qna/question");
        return "qna/submitQuestion";
    }

    @LoginRequired
    @PostMapping("/question")
    public String postQuestionForm(@ModelAttribute Article article, @SessionAttribute(value="currentUser", required=false) User currentUser){
        service.putNewArticle(currentUser, article);
        return "redirect:/qna/";
    }

    @LoginRequired(message = "Please Log In to Read Questions")
    @GetMapping("/article/{articleId}")
    public String getArticleDetail(@PathVariable int articleId, Model model){
        model.addAttribute("article", service.findArticleById(articleId));
        return "qna/questionDetail";
    }

    @LoginRequired
    @GetMapping("/{id}/edit")
    public String getEditPageForArticle(@PathVariable int id, @SessionAttribute(value="currentUser", required=false) User currentUser, Model model, RedirectAttributes ra){
        Article targetArticle = service.findArticleForEdit(id,currentUser);
        model.addAttribute("article", targetArticle);
        model.addAttribute("formActionUrl", "/qna/"+id+"/edit");
        return "qna/submitQuestion";

    }

    @LoginRequired
    @PostMapping("/{id}/edit")
    public String postEditedArticle(@PathVariable int id, @SessionAttribute(value="currentUser", required=false) User currentUser, @ModelAttribute Article article){
        service.putNewArticle(currentUser, article);
        return "redirect:/qna/"+id;
    }

    @LoginRequired
    @DeleteMapping("/{id}/delete")
    public String deleteArticle(@PathVariable int id, @SessionAttribute(value="currentUser", required=false) User currentUser, RedirectAttributes ra){
        Article targetArticle = service.findArticleForEdit(id,currentUser);
        service.deleteArticle(targetArticle, currentUser);
        return "redirect:/qna/";
    }

}

// The point to improve is not making lazy fetch on User class in article
// In fact loading article with its whole context is more dangerous than eager fetching of users
