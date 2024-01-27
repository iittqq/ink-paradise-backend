package com.catsaredope.inkparadise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import com.catsaredope.inkparadise.Repositories.AccountRepository;
import com.catsaredope.inkparadise.Models.Account;

@SpringBootApplication
public class InkparadiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(InkparadiseApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(AccountRepository repository) {
		return (args) -> {
			// Fetch all accounts
			Iterable<Account> accounts = repository.findAll();

			System.out.println("Accounts found with findAll():");
			// Print each account
			for (Account account : accounts) {
				System.out.println(account);
			}
		};
	}
}