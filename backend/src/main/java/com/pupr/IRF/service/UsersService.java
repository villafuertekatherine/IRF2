package com.pupr.IRF.service;

import com.pupr.IRF.model.UsersModel;
import com.pupr.IRF.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UsersModel registerUser(String username, String password, String email){
        if (username == null || password == null) {
            throw new IllegalArgumentException("Username and password must not be null");
        }else {
            UsersModel usersModel = new UsersModel();
            usersModel.setUsername(username);
            usersModel.setPassword(password);
            usersModel.setEmail(email);
            return usersRepository.save(usersModel);
        }
    }
    public UsersModel authenticate(String username, String password){
        return usersRepository.findByUsernameAndPassword(username, password).orElse(null);
    }
}
