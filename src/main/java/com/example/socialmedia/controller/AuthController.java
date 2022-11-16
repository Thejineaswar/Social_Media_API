package com.example.socialmedia.controller;


import com.example.socialmedia.auth.JwtTokenUtil;
import com.example.socialmedia.auth.JwtUserDetailsService;
import com.example.socialmedia.model.SmUser;
import com.example.socialmedia.payload.JwtRequest;
import com.example.socialmedia.payload.JwtResponse;
import com.example.socialmedia.payload.SmUserDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@CrossOrigin
public class AuthController{
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private JwtUserDetailsService userDetailsService;



    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws
            Exception{
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            System.out.println("Disabled Exception Happenning");
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            System.out.println("Bad Credentials Exception");
            throw new Exception("INVALID_CREDENIALS", e);
        }
    }

    @GetMapping("/hello")
    public String return_hello(){
        System.out.println("In hello method");
        return "Hello";
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody SmUserDTO smUserDTO){

        if(userDetailsService.userNameExists(smUserDTO.getUsername())){
            return ResponseEntity.status(400).body("Username already exists");
        }else{
            userDetailsService.save(smUserDTO);
        }
        return ResponseEntity.status(200).body("User Registered");
    }
}
