package se.meepo.sloth_todos;

import org.springframework.data.repository.CrudRepository;
import se.meepo.sloth_todos.model.Task;

public interface TaskRepository extends CrudRepository<Task, Integer> {
}
