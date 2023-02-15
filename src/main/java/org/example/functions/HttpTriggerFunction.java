package org.example.functions;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.BindingName;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;

/**
 * Azure Functions with HTTP Trigger.
 */
public class HttpTriggerFunction {

    /**
     * This function listens at endpoint "/api/HttpExample". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpExample
     * 2. curl "{your host}/api/HttpExample?name=HTTP%20Query"
     */
    @FunctionName("HttpExample")
    public HttpResponseMessage run(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.GET, HttpMethod.POST},
                authLevel = AuthorizationLevel.ANONYMOUS)
                HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        final String query = request.getQueryParameters().get("name");
        final String name = request.getBody().orElse(query);

        if (name == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a name on the query string or in the request body").build();
        } else {
            return request.createResponseBuilder(HttpStatus.OK).body("Hello, " + name).build();
        }
    }

    @FunctionName("get-todos")
    public HttpResponseMessage getTodos(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.GET},
                    route = "todos",
                    authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        context.getLogger().info("Java HTTP trigger processed a get todos request.");

        // Parse query parameter
        final String query = request.getQueryParameters().get("name");
        final String name = request.getBody().orElse(query);

        TodoService todoService = new TodoService();

        return request.createResponseBuilder(HttpStatus.OK).body(todoService.getTodos()).build();
    }

    @FunctionName("get-todo")
    public HttpResponseMessage getTodo(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.GET},
                    route = "todos/{id}",
                    authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<String>> request,
            @BindingName("id") String id,
            final ExecutionContext context) {

        context.getLogger().info("Java HTTP trigger processed a get todo by id request.");
        TodoService todoService = new TodoService();
        return request.createResponseBuilder(HttpStatus.OK).body(todoService.getTodo(id)).build();
    }

    @FunctionName("create-todo")
    public HttpResponseMessage createTodo(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.POST},
                    route = "todos",
                    authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<Todo>> request,
            final ExecutionContext context) {

        context.getLogger().info("Java HTTP trigger processed a create todo request.");

        Todo todo = request.getBody().get();
        TodoService todoService = new TodoService();

        if (todo == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Invalid request body").build();
        } else {
            return request.createResponseBuilder(HttpStatus.OK).body(todoService.createTodo(todo)).build();
        }
    }

    @FunctionName("update-todo")
    public HttpResponseMessage updateTodo(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.PUT},
                    route = "todos/{id}",
                    authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<Todo>> request,
            @BindingName("id") String id,
            final ExecutionContext context) {

        context.getLogger().info("Java HTTP trigger processed a update todo request.");

        Todo todo = request.getBody().get();
        TodoService todoService = new TodoService();

        if (todo == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Invalid request body").build();
        } else {
            return request.createResponseBuilder(HttpStatus.OK).body(todoService.updateTodo(id, todo)).build();
        }
    }

    @FunctionName("delete-todo")
    public HttpResponseMessage deleteTodo(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.DELETE},
                    route = "todos/{id}",
                    authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<String>> request,
            @BindingName("id") String id,
            final ExecutionContext context) {

        context.getLogger().info("Java HTTP trigger processed a delete todo request.");

        // Parse query parameter
        final String query = request.getQueryParameters().get("name");
        final String name = request.getBody().orElse(query);

        TodoService todoService = new TodoService();
        todoService.deleteTodo(id);
        return request.createResponseBuilder(HttpStatus.OK).body("Deleted todo successfully").build();
    }

}
