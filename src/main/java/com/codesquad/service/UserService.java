package com.codesquad.service;

import com.codesquad.cafeRepo.InterfaceRepo;
import com.codesquad.cafeRepo.JpaUserRepo;
import com.codesquad.exceptions.AuthenticationException;
import com.codesquad.exceptions.ForbiddenAccessException;
import com.codesquad.user.User;
import com.codesquad.user.UserUpdateForm;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class UserService {

    private final JpaUserRepo repo;

    @Autowired
    public UserService(JpaUserRepo repo){
        this.repo = repo;
    }

    public void addUser(User newUser){
        this.repo.save(newUser);
    }

    public User findUserById(String id){
        return this.repo.getUserById(id);
    }

    public List<User> allUsers(){
        return this.repo.findAll();
    }

    public User login(User modelUser){

        String userId = modelUser.getId();
        String password = modelUser.getPassword();

        User user = this.repo.getUserById(userId);

        if(user == null || !(user.getPassword().equals(password)) ){
            throw new AuthenticationException( "User Credentials Does Not Match");
        }

        return user;
    }

    public User findUserForUpdate(String id, User user){
        User targetUser = this.repo.getUserById(id);
        if(targetUser == null || !(targetUser.equals(user))){
            throw new ForbiddenAccessException("YOU CANNOT MODIFY OTHER'S PROFILE");
        }
        return targetUser;
    }

    public User updateUserProfile(String userId, UserUpdateForm form){
        User user = this.repo.getUserById(userId);

        if(user == null || !(form.getPassword().equals(user.getPassword()))){
            throw new ForbiddenAccessException("CHECK YOUR USER CREDENTIALS");
        }
        user.setName(form.getName());
        user.setPassword(form.getNewPassword());
        user.setEmail(form.getEmail());
        this.repo.save(user);
        return user;
    }

}
