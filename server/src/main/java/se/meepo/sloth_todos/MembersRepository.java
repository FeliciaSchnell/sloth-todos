package se.meepo.sloth_todos;

import org.springframework.data.repository.CrudRepository;
import se.meepo.sloth_todos.model.Member;

public interface MembersRepository extends CrudRepository<Member, Integer> {
}
