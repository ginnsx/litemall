package org.linlinjava.litemall.admin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.linlinjava.litemall.core.util.bcrypt.BCryptPasswordEncoder;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BcryptTest {

    @Test
    public void test() {
        String rawPassword = "aaaaaa";
        String encodedPassword = "";
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        encodedPassword = bCryptPasswordEncoder.encode(rawPassword);

        System.out.println("rawPassword=" + rawPassword + " encodedPassword=" + encodedPassword);

        Assertions.assertTrue(bCryptPasswordEncoder.matches(rawPassword, encodedPassword));
    }
}
