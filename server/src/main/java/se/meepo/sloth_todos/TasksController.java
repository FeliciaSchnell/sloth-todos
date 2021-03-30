package se.meepo.sloth_todos;

import org.springframework.web.bind.annotation.*;
import se.meepo.sloth_todos.model.Task;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TasksController {

    @Inject
    TaskRepository tasksRepository;
    private static final String allowedRequestOrigin = "http://localhost:8080"; 

    @RequestMapping(
            value = "tasks/member/{memberId}",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    @CrossOrigin(origins = allowedRequestOrigin)
    public List<Task> get(@PathVariable int memberId) {
        List<Task> result = new ArrayList<>();
        List<Task> tasks = (List<Task>) tasksRepository.findAll();
        for (Task task : tasks) {
            if (memberId == task.memberId) {
                result.add(task);
            }  
        }  
        return result;
    }

    @RequestMapping(
            value = "tasks",
            method = RequestMethod.POST,
            consumes = "application/json"
    )
    @CrossOrigin(origins = allowedRequestOrigin)
    public void post(@RequestBody Task task) {
        tasksRepository.save(task);
    }

    @RequestMapping(
            value = "tasks/{id}",
            method = RequestMethod.PUT,
            consumes = "application/json"
    )
    @CrossOrigin(origins = allowedRequestOrigin)
    public void put(@PathVariable int id, @RequestBody Task task) {
        task.id = id;
        tasksRepository.save(task);
    }

    @RequestMapping(
            value = "tasks/{id}",
            method = RequestMethod.DELETE
    )
    @CrossOrigin(origins = allowedRequestOrigin)
    public void delete(@PathVariable int id) {
        tasksRepository.deleteById(id);
    }
}
