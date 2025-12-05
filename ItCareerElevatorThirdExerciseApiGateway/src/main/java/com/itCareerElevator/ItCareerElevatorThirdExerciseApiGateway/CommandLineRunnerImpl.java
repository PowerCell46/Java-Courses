package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;

import javax.crypto.SecretKey;

@Controller
@Order(2) // ! Check whether for all components, or only for CommandLineRunner implementations
public class CommandLineRunnerImpl implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Message from command line runner...!");
    }

    public String generateBase64Key() {
        SecretKey key = Jwts.SIG.HS512.key().build();
        return Encoders
                .BASE64
                .encode(key.getEncoded());
    }
}
