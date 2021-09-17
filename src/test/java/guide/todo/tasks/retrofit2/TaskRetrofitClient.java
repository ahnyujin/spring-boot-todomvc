package guide.todo.tasks.retrofit2;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

interface TaskRetrofitClient {

    @GET("/tasks")
    Call<List<TaskClientResponse>> getAll();

    @GET("/tasks/{id}")
    Call<TaskAttributesClientResponse> get(
            @Path("id") String id
    );

    @POST("/tasks")
    Call<TaskIdClientResponse> create(
            @Body TaskCreateClientRequest attributes
    );

    @PUT("/tasks/{id}")
    Call<TaskAttributesClientResponse> update(
            @Path("id") String id,
            @Body TaskAttributesUpdateClientRequest attributes
    );

    @PATCH("/tasks/{id}")
    Call<TaskAttributesClientResponse> patch(
            @Path("id") String id,
            @Body TaskAttributesPatchClientRequest attributes
    );

    @DELETE("/tasks/{id}")
    Call<Void> delete(
            @Path("id") String id
    );
}
