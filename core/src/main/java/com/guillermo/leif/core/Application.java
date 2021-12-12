package com.guillermo.leif.core;

import com.guillermo.leif.challenges.Challenge;
import com.guillermo.leif.challenges.dec09.Challenge9;
import com.guillermo.leif.challenges.dec10.Challenge10;
import com.guillermo.leif.challenges.dec11.Challenge11;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Application {
    private final Challenge challenge;

    // == constructors ==
    // To change the challenge to be solved, just change the parameter annotation.
    // Each challenge should have its own annotation.
    @Autowired
    public Application(@Challenge11 Challenge challenge) {
        this.challenge = challenge;
    }

    // == methods ==
    @EventListener(ContextRefreshedEvent.class)
    public void runApplication() throws Exception {
        challenge.solveProblem();
    }
}

@Slf4j
class AppContextCreator {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        context.close();
    }
}
