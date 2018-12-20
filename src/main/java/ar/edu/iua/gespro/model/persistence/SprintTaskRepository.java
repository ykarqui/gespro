package ar.edu.iua.gespro.model.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import ar.edu.iua.gespro.model.SprintTask;

public interface SprintTaskRepository extends JpaRepository<SprintTask, Integer> {
	public List<SprintTask> findAllByOrderByCreatedAsc();
	
	@Modifying
	@Query(value="SELECT * FROM sprint_task WHERE list_id=? ORDER BY created", nativeQuery=true)
	public List<SprintTask> findByCreated(Integer list_id);
	
	@Modifying
	@Query(value="SELECT * FROM sprint_task ORDER BY created", nativeQuery=true)
	public List<SprintTask> findAllByCreated();
}
