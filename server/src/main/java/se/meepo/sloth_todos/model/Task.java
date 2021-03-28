package se.meepo.sloth_todos.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Task {
    @Id
    @GeneratedValue
    public int id;
    public int todoId;
    public String name;
    public boolean completed;
    public int memberId;
}
