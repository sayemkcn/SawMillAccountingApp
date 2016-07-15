package sayem.toracode.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages={"sayem.toracode.controllers","sayem.toracode.pojo"})
@EnableJpaRepositories(basePackages="sayem.toracode.repositories")
@EntityScan(basePackages="sayem.toracode.entities")
public class AccountingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountingAppApplication.class, args);
	}
}
