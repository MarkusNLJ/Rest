package jpa.kea.demo.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "assignments")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String name;

    LocalDate deadline;
}
