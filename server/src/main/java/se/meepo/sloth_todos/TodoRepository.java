package se.meepo.sloth_todos;

import org.springframework.data.repository.CrudRepository;
import se.meepo.sloth_todos.model.Todo;

public interface TodoRepository extends CrudRepository<Todo, Integer> {
}
