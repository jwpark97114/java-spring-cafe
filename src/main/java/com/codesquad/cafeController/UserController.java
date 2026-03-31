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
    public String logInWithCredentials(@ModelAttribute User user, HttpSession session, RedirectAttributes redirection){

        if(service.verifyUserCredentials(user)){
            User targetUser = service.findUserById(user.getId());
            session.setAttribute("currentUser", targetUser);
            return "redirect:/";
        }
        else{
            redirection.addFlashAttribute("errorMessage", "User Credentials Does Not Match");
            return "redirect:/user/login";
        }
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
    public String getUserInfoModPage(@PathVariable String id, Model model, HttpSession session, RedirectAttributes redirectAttribute){

        if((session.getAttribute("currentUser")).equals(service.findUserById(id))){
            model.addAttribute("user",service.findUserById(id));
            return "user/editProfile";
        }
        else{
            redirectAttribute.addFlashAttribute("errorMessage", "YOU CANNOT MODIFY OTHER'S PROFILE");
            return "redirect:/user/users";
        }

    }

    @LoginRequired
    @PutMapping("/users/{id}/edit")
    public String putUserInfoMod(@PathVariable String id, UserUpdateForm form, Model model, RedirectAttributes redirectAttrs, HttpSession session){

        User existingUser = service.findUserById(id);

        if(session.getAttribute("currentUser").equals(existingUser) && service.updateUserProfile(existingUser.getId(), form)){
            session.setAttribute("currentUser", existingUser);
            model.addAttribute("user", session.getAttribute("currentUser"));
            return "user/userDetail";
        }
        else{
            redirectAttrs.addFlashAttribute("errorMessage", "Incorrect Credentials. Please try again");
            return "redirect:/user/users/{id}/edit";
        }
    }

    @GetMapping("/logout")
    public String logOut(HttpSession session){
        session.setAttribute("currentUser", null);
        return "redirect:/";
    }
}
