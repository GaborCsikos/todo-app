package hu.gabor.csikos.todoapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "days_to_achieve")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DaysToAchieve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "days")
    private int days;
}
