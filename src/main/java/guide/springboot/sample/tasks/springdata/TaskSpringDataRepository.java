package guide.springboot.sample.tasks.springdata;

import org.springframework.data.jpa.repository.JpaRepository;

interface TaskSpringDataRepository extends JpaRepository<TaskEntity, String> {
}
