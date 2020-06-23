package ua.edu.ukma.distedu.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Collections;

@SpringBootApplication
@EnableJpaRepositories("ua.edu.ukma.distedu.storage.persistence.repository")
@EntityScan("ua.edu.ukma.distedu.storage.persistence.model")
public class StorageApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(StorageApplication.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", "8080"));
        app.run(args);
    }

}
