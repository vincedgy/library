package net.vincedgy.aws.library;

import net.vincedgy.aws.library.service.SimpleQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryApplication {

	@Autowired
	SimpleQueueService simpleQueueService;

	public static void main(String[] args) {

		SpringApplication.run(LibraryApplication.class, args);
	}
}
