package com.AnthonyChuan.Librerialura;

import com.AnthonyChuan.Librerialura.Principal.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibrerialuraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LibrerialuraApplication.class, args);
	}
	@Autowired
	private Principal principal;
	@Override
	public void run(String... args) throws Exception {
		principal.muestraMenu();
	}
}
