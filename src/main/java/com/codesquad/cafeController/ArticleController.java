package com.codesquad.cafeController;

import com.codesquad.article.Article;
import com.codesquad.reply.Reply;
import com.codesquad.service.ArticleService;
import com.codesquad.service.ReplyService;
import com.codesquad.user.LoginRequired;
import com.codesquad.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/qna")
public class ArticleController {

    private final ArticleService service;
    private final ReplyService replyService;

    @Autowired
    public ArticleController(ArticleService as, ReplyService rs){
        this.service = as;
        this.replyService=rs;
    }

    @GetMapping("/")
    public String getQna(Model model){
        model.addAttribute("articles", service.getAllArticles());
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
    @GetMapping("/{articleId}")
    public String getArticleDetail(@PathVariable int articleId, Model model){
        model.addAttribute("article", service.findArticleById(articleId));
        model.addAttribute("comments", replyService.findRepliesForArticle(articleId));
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
