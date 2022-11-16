package com.example.socialmedia.auth;

import com.example.socialmedia.model.SmUser;
import com.example.socialmedia.payload.SmUserDTO;
import com.example.socialmedia.repository.SmUserDAO;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService{
    private SmUserDAO smUserDAO;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SmUser user = smUserDAO.findByUsername(username);

        if(user == null){
            System.out.println("Username not found");
            throw new UsernameNotFoundException("User with username " + username + " not found");
        }

        return new User(user.getUsername(),user.getPassword(),new ArrayList<>());
    }

    public Boolean userNameExists(String username){
        SmUser user = smUserDAO.findByUsername(username);
        if(user == null){
            return false;
        }
        return true;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){ return  NoOpPasswordEncoder.getInstance();}
    public SmUser save(SmUserDTO userRequest){
        SmUser newUser = new SmUser();
        newUser.setUsername(userRequest.getUsername());
        newUser.setPassword(userRequest.getPassword());
        newUser.setPhone(userRequest.getPhone());
        return smUserDAO.save(newUser);
    }
}
