package guide.springboot.sample.tasks.springdata;

import guide.springboot.sample.tasks.TaskService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskServiceConfig {

    @Bean
    TaskService taskService(
            final TaskSpringDataRepository taskSpringDataRepository) {
        return new TaskSpringDataService(taskSpringDataRepository);
    }

}
