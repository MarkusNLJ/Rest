package jpa.kea.demo.repository;

import jpa.kea.demo.model.Assignment;
import org.springframework.data.repository.CrudRepository;

public interface AssignmentRep extends CrudRepository<Assignment, Long> {
}
