package com.guillermo.leif.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.guillermo.leif.challenges.Challenge;
import com.guillermo.leif.challenges.dec04.Challenge4;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Application {
    private Challenge challenge;

    // == constructors ==
    // To change the challenge to be solved, just change the parameter annotation.
    // Each challenge should have its own annotation.
    @Autowired
    public Application(@Challenge4 Challenge challenge) {
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
