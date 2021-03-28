package se.meepo.sloth_todos;

import org.springframework.web.bind.annotation.*;
import se.meepo.sloth_todos.model.Task;

import javax.inject.Inject;

@RestController
public class TasksController {

    @Inject
    TaskRepository tasksRepository;

    @RequestMapping(
            value = "tasks",
            method = RequestMethod.POST,
            consumes = "application/json"
    )
    @CrossOrigin(origins = "http://localhost:8080")
    public void post(@RequestBody Task task) {
        tasksRepository.save(task);
    }

    @RequestMapping(
            value = "tasks/{id}",
            method = RequestMethod.PUT,
            consumes = "application/json"
    )
    @CrossOrigin(origins = "http://localhost:8080")
    public void put(@PathVariable int id, @RequestBody Task task) {
        task.id = id;
        tasksRepository.save(task);
    }

    @RequestMapping(
            value = "tasks/{id}",
            method = RequestMethod.DELETE
    )
    @CrossOrigin(origins = "http://localhost:8080")
    public void delete(@PathVariable int id) {
        tasksRepository.deleteById(id);
    }
}
