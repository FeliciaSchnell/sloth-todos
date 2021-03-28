package se.meepo.sloth_todos.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Todo {
    @Id
    @GeneratedValue
    public int id;
    public String title, description;
    @Transient
    @JsonInclude
    public List<Task> tasks = new ArrayList<>();

    public Todo() {}

    public Todo(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
