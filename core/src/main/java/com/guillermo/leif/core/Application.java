package com.guillermo.leif.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.guillermo.leif.challenges.Challenge;
import com.guillermo.leif.challenges.dec12.Challenge12;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Application {
	private final Challenge challenge;

	// == constructors ==
	// To change the challenge to be solved, just change the parameter annotation.
	// Each challenge should have its own annotation.
	@Autowired
	public Application(@Challenge12 Challenge challenge) {
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
