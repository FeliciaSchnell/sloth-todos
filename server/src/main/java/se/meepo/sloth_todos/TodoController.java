package se.meepo.sloth_todos;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.meepo.sloth_todos.model.Todo;
import se.meepo.sloth_todos.model.Task;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TodoController {

    @Inject
    TodoRepository todoRepository;
    @Inject
    TaskRepository tasksRepository;
    private static final String allowedRequestOrigin = "http://localhost:8080"; 

    @RequestMapping(
        value = "todos",
        method = RequestMethod.GET,
        produces = "application/json"
    )
    @CrossOrigin(origins = allowedRequestOrigin)
    public List<Todo> get() {
        List<Todo> result = new ArrayList<>();
        List<Todo> todos = (List<Todo>) todoRepository.findAll();
        List<Task> tasks = (List<Task>) tasksRepository.findAll();
        
        for (Todo todo : todos) {
            for (Task task : tasks) {
                if (todo.id == task.todoId) {
                    todo.tasks.add(task);
                }
            }
            result.add(todo);
        }
        return result;
    }

    @RequestMapping(
            value = "todos/{id}",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    @CrossOrigin(origins = allowedRequestOrigin)
    public Todo get(@PathVariable int id) {
        return todoRepository.findById(id)
                .map(todo -> {
                    for (Task task : tasksRepository.findAll()) {
                        if (todo.id == task.todoId) {
                            todo.tasks.add(task);
                        }
                    }
                    return todo;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));
    }

    @RequestMapping(
        value = "todos",
        method = RequestMethod.POST,
        consumes = "application/json"
    )
    @CrossOrigin(origins = allowedRequestOrigin)
    public void post(@RequestBody Todo todo) {
        todo = todoRepository.save(todo);
        for (Task task : todo.tasks) {
            task.todoId = todo.id;
            tasksRepository.save(task);
        } 
    }

    @RequestMapping(
            value = "todos/{id}",
            method = RequestMethod.PUT,
            consumes = "application/json"
    )
    @CrossOrigin(origins = allowedRequestOrigin)
    public void put(@PathVariable int id, @RequestBody Todo todo) {
        todo.id = id;
        todoRepository.save(todo);
        for (Task task : todo.tasks) {
            task.todoId = todo.id;
            tasksRepository.save(task);
        }
    }

    @RequestMapping(
            value = "todos/{id}",
            method = RequestMethod.DELETE
    )
    @CrossOrigin(origins = allowedRequestOrigin)
    public void delete(@PathVariable int id) {
        todoRepository.deleteById(id);
    }
}
