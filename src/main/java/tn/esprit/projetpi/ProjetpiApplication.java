package tn.esprit.projetpi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@SpringBootApplication
@RestController
public class ProjetpiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetpiApplication.class, args);
	}

	@GetMapping("/test")
	public String test() {
		return "test";
	}

}
