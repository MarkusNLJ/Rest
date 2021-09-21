package jpa.kea.demo.repository;

import jpa.kea.demo.model.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRep extends CrudRepository<Student, Long> {
    public Iterable<Student> findStudentByName(String name);
}
