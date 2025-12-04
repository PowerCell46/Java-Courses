package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import javax.crypto.SecretKey;

@Controller
public class CommandLineRunnerImpl implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        SecretKey key = Jwts.SIG.HS512.key().build();
        String base64 = Encoders.BASE64.encode(key.getEncoded());
        System.out.println("base64:");
        System.out.println(base64);

    }
}
