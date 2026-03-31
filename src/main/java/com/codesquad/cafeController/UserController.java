package com.codesquad.cafeController;

import com.codesquad.service.UserService;
import com.codesquad.user.LoginRequired;
import com.codesquad.user.User;
import com.codesquad.user.UserUpdateForm;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService userService){
        this.service = userService;
    }

    @GetMapping("/login")
    public String logInPage(HttpSession session){
        if(session.getAttribute("currentUser") != null){
            return "redirect:/";
        }
        return "user/login";
    }

    @PostMapping("/login")
    public String logInWithCredentials(@ModelAttribute User user, HttpSession session){
        User validatedUser = this.service.login(user);
        session.setAttribute("currentUser", validatedUser);
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String getSignUp(){
        return "user/signup";
    }

    @PostMapping("/signup")
    public String postSignUp(@ModelAttribute User user, HttpSession session){
        service.addUser(user);
        session.setAttribute("currentUser", service.findUserById(user.getId()));
        return "redirect:/user/users";
    }

    @GetMapping("/users")
    public String getUsersList(Model model){
        model.addAttribute("users", service.allUsers());
        return "user/users";
    }

    @GetMapping("/{id}")
    public String getUserDetail(@PathVariable String id, Model model){
        model.addAttribute("user", service.findUserById(id));
        return "user/userDetail";
    }

    @LoginRequired
    @GetMapping("/users/{id}/edit")
    public String getUserInfoModPage(@PathVariable String id, Model model, HttpSession session){
        User user = service.findUserForUpdate(id, (User)session.getAttribute("currentUser"));
        model.addAttribute("user",user);
        return "user/editProfile";

    }

    @LoginRequired
    @PutMapping("/users/{id}/edit")
    public String putUserInfoMod(@PathVariable String id, UserUpdateForm form, Model model, HttpSession session){
        User updatedUser = service.updateUserProfile(id, form);
        session.setAttribute("currentUser", updatedUser);
        model.addAttribute("user", session.getAttribute("currentUser"));
        return "user/userDetail";
    }

    @GetMapping("/logout")
    public String logOut(HttpSession session){
        session.setAttribute("currentUser", null);
        return "redirect:/";
    }
}
