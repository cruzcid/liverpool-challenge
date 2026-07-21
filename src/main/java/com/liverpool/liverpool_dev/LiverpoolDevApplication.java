package com.liverpool.liverpool_dev;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.liverpool.liverpool_dev.application.service.CustomerService;
import com.liverpool.liverpool_dev.domain.model.Customer;
import com.liverpool.liverpool_dev.domain.model.DeliveryAddress;
import com.liverpool.liverpool_dev.domain.port.in.SyncDataUseCase;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class LiverpoolDevApplication implements CommandLineRunner {

	private final SyncDataUseCase syncDataUseCase;
	private final CustomerService customerService;

	public static void main(String[] args) {
		SpringApplication.run(LiverpoolDevApplication.class, args);
	}

	@Override
	public void run(String... args) {
		syncDataUseCase.syncAll();

		Customer customer = Customer.builder()
				.userId("usr-001")
				.firstName("Juan")
				.lastNamePaternal("García")
				.lastNameMaternal("López")
				.email("juan.garcia@example.com")
				.deliveryAddress(DeliveryAddress.builder()
						.street("Av. Insurgentes Sur 1234")
						.city("Ciudad de México")
						.state("CDMX")
						.zipCode("03100")
						.country("México")
						.build())
				.build();

		customerService.create(customer);
	}

}
