package guide.todo.tasks.retrofit2;

import com.linecorp.armeria.client.retrofit2.ArmeriaRetrofit;
import guide.todo.tasks.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

abstract class AbstractClientTest {

    private TaskService taskService;

    abstract String getBaseUrl();

    @BeforeEach
    void beforeAll() {
        final var retrofit =
                ArmeriaRetrofit.builder(getBaseUrl())
                        .addConverterFactory(JacksonConverterFactory.create())
                        .build();
        final var taskRetrofitClient = retrofit.create(TaskRetrofitClient.class);
        taskService = new TaskRetrofitClientService(taskRetrofitClient);
    }

    @Test
    void testScenario() {
        // 전체 태스크 조회
        final var tasksBeforeInsert = taskService.selectAll();

        // 임의의 태스크 생성
        final var taskAttributesInsert = new TaskAttributesInsert(
                "임의의 태스크입니다. #" + ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE)
        );

        final var insertedTaskId = taskService.insert(taskAttributesInsert);

        assertThat(tasksBeforeInsert)
                .map(Task::getId)
                .doesNotContain(insertedTaskId);

        // 전체 태스크 재조회
        final var tasksAfterInsert = taskService.selectAll();
        assertThat(tasksAfterInsert)
                .map(Task::getId)
                .contains(insertedTaskId);

        // 생성한 태스크만 조회
        final var insertedTaskAttributes = taskService.select(insertedTaskId);
        assertThat(insertedTaskAttributes)
                .isNotEmpty()
                .get()
                .returns(taskAttributesInsert.getDetails(), TaskAttributes::getDetails)
                .returns(TaskStatus.ACTIVE, TaskAttributes::getStatus);

        // 생성한 태스크 전체 수정
        final var taskAttributesToUpdate = new TaskAttributes(
                "업데이트된 임의의 태스크입니다. #" + ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE),
                TaskStatus.ACTIVE
        );
        final var updatedTaskAttributes = taskService.update(insertedTaskId, taskAttributesToUpdate);

        assertThat(updatedTaskAttributes)
                .returns(taskAttributesToUpdate.getDetails(), TaskAttributes::getDetails)
                .returns(taskAttributesToUpdate.getStatus(), TaskAttributes::getStatus);

        // 전체 태스크 재재조회
        final var tasksAfterUpdate = taskService.selectAll();
        assertThat(tasksAfterUpdate)
                .filteredOn(it -> insertedTaskId.equals(it.getId()))
                .first()
                .returns(taskAttributesToUpdate.getDetails(), Task::getDetails)
                .returns(taskAttributesToUpdate.getStatus(), Task::getStatus);

        // 태스크 부분 수정
        final var taskAttributesPatch = new TaskAttributesPatch(
                null,
                TaskStatus.DONE
        );
        final var patchedTaskAttributes = taskService.patch(insertedTaskId, taskAttributesPatch);
        assertThat(patchedTaskAttributes)
                .returns(updatedTaskAttributes.getDetails(), TaskAttributes::getDetails)
                .returns(patchedTaskAttributes.getStatus(), TaskAttributes::getStatus);

        // 태스크 삭제
        taskService.delete(insertedTaskId);

        // 태스크 전체 재재재조회
        final var tasksAfterDelete = taskService.selectAll();
        assertThat(tasksAfterDelete)
                .map(Task::getId)
                .doesNotContain(insertedTaskId);
    }
}
