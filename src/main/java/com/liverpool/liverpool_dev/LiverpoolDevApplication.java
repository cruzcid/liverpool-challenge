package com.liverpool.liverpool_dev;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.liverpool.liverpool_dev.domain.port.in.SyncDataUseCase;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class LiverpoolDevApplication implements CommandLineRunner {

	private final SyncDataUseCase syncDataUseCase;

	public static void main(String[] args) {
		SpringApplication.run(LiverpoolDevApplication.class, args);
	}

	@Override
	public void run(String... args) {
		syncDataUseCase.syncAll();
	}

}
