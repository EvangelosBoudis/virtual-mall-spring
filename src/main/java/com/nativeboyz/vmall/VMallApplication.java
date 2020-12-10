package com.nativeboyz.vmall;

import com.nativeboyz.vmall.repositories.HelperRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = HelperRepositoryImpl.class)
public class VMallApplication {

	public static void main(String[] args) {
		SpringApplication.run(VMallApplication.class, args);
	}

}

/*
TODO:
 2. Hibernate Validation
 3. Authentication - JWT
 4. Database Authorization
 5. Password Encryption
 6. Replace Response Messages with application.properties @Value
 7. x-obj-properties ?
 8. Set + function operations @ManyToMany + Check Video
 9. AOP for each function returns (Category - Customer - Product) change Url
 */
