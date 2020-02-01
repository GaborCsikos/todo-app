package hu.gabor.csikos.todoapp.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "todo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "days_to_achieve_id", referencedColumnName = "id")
    private DaysToAchieve daysToAchieve;

    @OneToMany(mappedBy = "todo")
    private List<Note> notes;
    @ManyToMany
    @JoinTable(
            name = "todo_goal",
            joinColumns = @JoinColumn(name = "todo_id"),
            inverseJoinColumns = @JoinColumn(name = "goal_id"))
    private Set<Goal> goals;

    public Todo(Long id, String name, Priority priority) {
        this.id = id;
        this.name = name;
        this.priority = priority;
    }
}
