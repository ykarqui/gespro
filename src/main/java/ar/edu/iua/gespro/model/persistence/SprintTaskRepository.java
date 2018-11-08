package ar.edu.iua.gespro.model.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.iua.gespro.model.SprintTask;

public interface SprintTaskRepository extends JpaRepository<SprintTask, Integer> {

}
