package de.kevcodez.semver.semvervalidator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class SemverValidatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SemverValidatorApplication.class, args);
    }
}
