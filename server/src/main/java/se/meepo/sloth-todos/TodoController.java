package se.meepo.recruitment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.meepo.recruitment.model.Todo;
import se.meepo.recruitment.model.Task;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TodoController {

    @Inject
    TodoRepository todoRepository;
    @Inject
    TaskRepository tasksRepository;

    @RequestMapping(
            value = "todos",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    @CrossOrigin(origins = "http://localhost:8080")
    public List<Todo> get() {
        List<Todo> todos = new ArrayList<>();
        for (Todo todo : todoRepository.findAll()) {
            for (Task task : tasksRepository.findAll()) {
                if (todo.id == task.todoId) {
                    todo.tasks.add(task);
                }
            }
            todos.add(todo);
        }
        return todos;
    }

    @RequestMapping(
            value = "todos",
            method = RequestMethod.POST,
            consumes = "application/json"
    )
    @CrossOrigin(origins = "http://localhost:8080")
    public void post(@RequestBody Todo todo) {
        todoRepository.save(todo);
    }

    @RequestMapping(
            value = "todos/{id}",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    @CrossOrigin(origins = "http://localhost:8080")
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
            value = "todos/{id}",
            method = RequestMethod.PUT,
            consumes = "application/json"
    )
    @CrossOrigin(origins = "http://localhost:8080")
    public void put(@PathVariable int id, @RequestBody Todo todo) {
        todo.id = id;
        todoRepository.save(todo);
    }

    @RequestMapping(
            value = "todos/{id}",
            method = RequestMethod.DELETE
    )
    @CrossOrigin(origins = "http://localhost:8080")
    public void delete(@PathVariable int id) {
        todoRepository.deleteById(id);
    }
}
