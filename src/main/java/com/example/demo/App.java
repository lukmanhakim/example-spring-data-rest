package com.example.demo;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EnableSwagger2
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    ApplicationRunner inject(EmployeeRepository repository) {
        Faker faker = new Faker(Locale.US);
        List<Employee> employees =
                Stream.generate(
                        () -> Employee.builder()
                                .fullName(faker.name().fullName())
                                .job(faker.job().position())
                                .phone(faker.phoneNumber().cellPhone())
                                .address(faker.address().fullAddress())
                                .build()
                )
                .limit(50)
                .collect(Collectors.toList());
        return args -> repository.saveAll(employees);
    }

    @Bean
    Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder().title("Demo App").build())
                .select()
                .paths(PathSelectors.regex("/error").negate())
                .paths(PathSelectors.regex("/profile").negate())
                .paths(PathSelectors.ant("/actuator/**").negate())
                .build();
    }
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
class Employee implements Serializable {

    private static final long serialVersionUID = 5161632694371701599L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String address;
    private String phone;
    private String job;
}

@RepositoryRestResource
interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @RestResource(path = "name")
    List<Employee> findByFullNameContainingIgnoreCase(String q);
}