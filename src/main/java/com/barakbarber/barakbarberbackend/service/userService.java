package com.barakbarber.barakbarberbackend.service;

import com.barakbarber.barakbarberbackend.model.Rol;
import com.barakbarber.barakbarberbackend.model.User;
import com.barakbarber.barakbarberbackend.repository.RolRepository;
import com.barakbarber.barakbarberbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class userService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolRepository rolRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public User userRegister(User user) throws Exception {
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new Exception("Email already exists");
        }

        String generatedUsername = generateUsername(user.getName(),user.getLastname());
        user.setUsername(generatedUsername);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        if(user.getRol()== null){
            Optional<Rol> rolOpt = rolRepository.findByName("CLIENTE");
            if(rolOpt.isPresent()){
                user.setRol(rolOpt.get());
            } else {
                Rol rolCliente = new Rol("CLIENTE");
                rolRepository.save(rolCliente);
                user.setRol(rolCliente);
            }
        }
        return userRepository.save(user);
    }
    private String generateUsername(String name, String lastName) {
        String base =( name + lastName).toLowerCase().replaceAll("\\s+","");
        String username = base;
        int counter = 1;
        while(userRepository.findByName(username).isPresent()){
            username = base + counter;
            counter++;
        }
        return username;
    }


}
