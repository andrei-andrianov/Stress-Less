package mindfulness.service;

import lombok.extern.slf4j.Slf4j;
import mindfulness.model.User;
import mindfulness.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class DBLoaderService {

    @Bean
    CommandLineRunner initDB(UserRepository repository){
        return args -> {
            log.info("Preloading default user " + repository.save(new User("default@email.com", "DefaultUser", 99,
                    "DefaultGender", "DefaultOccupation", "DefaultEthnicity")));;
        };
    }


}