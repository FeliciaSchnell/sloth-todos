package se.meepo.sloth_todos;

import org.springframework.web.bind.annotation.*;
import se.meepo.sloth_todos.model.Member;

import javax.inject.Inject;

@RestController
public class MembersController {

    @Inject
    MembersRepository membersRepository;

    @RequestMapping(
            value = "members",
            method = RequestMethod.POST,
            consumes = "application/json"
    )
    @CrossOrigin(origins = "http://localhost:8080")
    public void post(@RequestBody Member member) {
        membersRepository.save(member);
    }

    @RequestMapping(
            value = "members/{id}",
            method = RequestMethod.PUT,
            consumes = "application/json"
    )
    @CrossOrigin(origins = "http://localhost:8080")
    public void put(@PathVariable int id, @RequestBody Member member) {
        member.id = id;
        membersRepository.save(member);
    }

    @RequestMapping(
            value = "members/{id}",
            method = RequestMethod.DELETE
    )
    @CrossOrigin(origins = "http://localhost:8080")
    public void delete(@PathVariable int id) {
        membersRepository.deleteById(id);
    }
}
