package guide.springboot.sample.tasks.springdata;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface TaskSpringDataRepository extends JpaRepository<TaskEntity, UUID> {
}
