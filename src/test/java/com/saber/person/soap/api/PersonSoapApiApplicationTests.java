package com.saber.person.soap.api;

import com.saber.person.soap.api.entity.UserAuthority;
import com.saber.person.soap.api.entity.Users;
import com.saber.person.soap.api.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class PersonSoapApiApplicationTests {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

  //@Test
    void contextLoads() {
        String username = "saber66";
        String password = "saber@123";

        String passwordEncryptedNew = passwordEncoder.encode(password);

        Users users = new Users();
        users.setUsername(username);
        users.setPassword(passwordEncryptedNew);

        users.setAccountNonExpired(true);
        users.setAccountNonExpired(true);
        users.setAccountNonLocked(true);
        users.setEnabled(true);
        users.setCredentialsNonExpired(true);
        List<UserAuthority> userAuthorities = new ArrayList<>();
        userAuthorities.add(new UserAuthority("user"));
        userAuthorities.add(new UserAuthority("admin"));
        users.setUserEntityAuthorities(userAuthorities);


        userRepository.save(users);


        Optional<Users> optionalUsers = userRepository.findByUsername(username);
        Assertions.assertTrue(optionalUsers.isPresent());
        Users users1 = optionalUsers.get();
        System.out.println(users1);
    }

}
