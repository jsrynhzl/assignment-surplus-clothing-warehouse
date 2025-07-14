 package com.example.clotheswarehouse;

import com.example.clotheswarehouse.model.Clothing;
import com.example.clotheswarehouse.model.Clothing.Brand;
import com.example.clotheswarehouse.model.User;
import com.example.clotheswarehouse.repository.ClothingRepository;

import com.example.clotheswarehouse.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@SpringBootApplication
public class ClothesWarehouse {

	public static void main(String[] args) {
		SpringApplication.run(ClothesWarehouse.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(ClothingRepository repository) {
		return args -> {
			// Input limits: year > 2021; price > 1000
			repository.save(Clothing.builder()
					.name("Varsity jacket")
					.yearOfCreation(2022)
					.price(new BigDecimal(3050.95))
					.brand(Brand.BALENCIAGA).build());

			repository.save(Clothing.builder()
					.name("Bandana")
					.yearOfCreation(2024)
					.price(new BigDecimal(1020.35))
					.brand(Brand.DIOR).build());

			repository.save(Clothing.builder()
					.name("Gloves")
					.yearOfCreation(2024)
					.price(new BigDecimal(1250.95))
					.brand(Brand.CHANEL).build());

			repository.save(Clothing.builder()
					.name("Handkerchief")
					.yearOfCreation(2022)
					.price(new BigDecimal(1100.35))
					.brand(Brand.GUCCI).build());

			repository.save(Clothing.builder()
					.name("Tie")
					.yearOfCreation(2024)
					.price(new BigDecimal(1100.95))
					.brand(Brand.DIOR).build());

			repository.save(Clothing.builder()
					.name("Socks")
					.yearOfCreation(2025)
					.price(new BigDecimal(1476.99))
					.brand(Brand.BALENCIAGA).build());

			repository.save(Clothing.builder()
					.name("Long sleeved shirt")
					.yearOfCreation(2023)
					.price(new BigDecimal(2550.95))
					.brand(Brand.GUCCI).build());

			repository.save(Clothing.builder()
					.name("Denim jeans")
					.yearOfCreation(2022)
					.price(new BigDecimal(3500.10))
					.brand(Brand.CHANEL).build());
		};
	}

	@Bean
	public CommandLineRunner preloadUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (userRepository.findByUsername("adminsample").isEmpty()) {
				userRepository.save(User.builder()
						.username("adminsample")
						.password(passwordEncoder.encode("adminpass"))
						.role("ADMIN")
						.build());
			}

			if (userRepository.findByUsername("employeesample").isEmpty()) {
				userRepository.save(User.builder()
						.username("employeesample")
						.password(passwordEncoder.encode("employeepass"))
						.role("EMPLOYEE")
						.build());
			}

			if (userRepository.findByUsername("usersample").isEmpty()) {
				userRepository.save(User.builder()
						.username("usersample")
						.password(passwordEncoder.encode("userpass"))
						.role("USER")
						.build());
			}
		};
	}


}