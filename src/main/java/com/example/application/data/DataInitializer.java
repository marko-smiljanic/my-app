package com.example.application.data;

import com.example.application.entity.Role;
import com.example.application.entity.User;
import com.example.application.repository.UserRepository;
import javassist.bytecode.ByteArray;
import org.apache.commons.codec.binary.Hex;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;



//moja klasa dodata da bih imao pristup ulogovanom korisniku
//manmuelno sam ulogovao korisnika

//ne treba autowired!!!


@Configuration
public class DataInitializer {

    @Bean(name = "customDataInitializer")
    public CommandLineRunner dataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                User admin = new User();
                admin.setUsername("marko");
                admin.setIme("Marko");
                admin.setPrezime("Smiljanic");
                admin.setProfilePicture(null);
                admin.setHashedPassword(passwordEncoder.encode("password")); // koristi bcrypt za lozinku, zato sto sam definisao bean
                admin.setRoles(Set.of(Role.ADMIN, Role.USER));
                userRepository.save(admin);
            }
        };
    }


}
