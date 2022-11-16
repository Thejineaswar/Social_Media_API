package com.example.socialmedia.repository;


import com.example.socialmedia.model.SmUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmUserDAO extends CrudRepository<SmUser,String>{

     SmUser findByUsername(String username);
}
