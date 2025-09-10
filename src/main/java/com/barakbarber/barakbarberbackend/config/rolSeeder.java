package com.barakbarber.barakbarberbackend.config;

import com.barakbarber.barakbarberbackend.model.Rol;
import com.barakbarber.barakbarberbackend.repository.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class rolSeeder {

@Bean
    CommandLineRunner initRoles(RolRepository rolRepository){
    return args -> {
        String[] roles = {"ADMIN","BARBERO","SECRETARIA"};
        for(String roleName : roles){
            if(rolRepository.findByNombre(roleName).isEmpty()){
                rolRepository.save(new Rol(roleName));
                System.out.println("Rol creado: " + roleName);
            }
        }
    };
}
}
