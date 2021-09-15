package guide.springboot.sample.tasks.springdata;

import guide.springboot.sample.lang.UuidGenerator;
import guide.springboot.sample.tasks.TaskService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskServiceConfig {

    @Bean
    UuidGenerator uuidGenerator() {
        return new UuidGenerator();
    }

    @Bean
    TaskService taskService(
            final TaskSpringDataRepository taskSpringDataRepository,
            final UuidGenerator uuidGenerator) {
        return new TaskSpringDataService(taskSpringDataRepository,
                uuidGenerator);
    }

}
