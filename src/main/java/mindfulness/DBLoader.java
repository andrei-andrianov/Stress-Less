package mindfulness;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class DBLoader {

    @Bean
    CommandLineRunner initDB(UserRepository repository){
        return args -> {
            log.info("Preloading " + repository.save(new User("default@email.com", "DefaultUser", 99,
                    "DefaultGender", "DefaultOccupation", "DefaultEthnicity")));;
        };
    }


}
