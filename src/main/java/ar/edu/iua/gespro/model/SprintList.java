package ar.edu.iua.gespro.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "sprint_list")
public class SprintList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "list_id")
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "sprint_name")
	private String sprintName;
	
	@OneToMany(mappedBy = "listName", fetch = FetchType.EAGER)
	@JsonIgnoreProperties("listName")
	private List<SprintTask> task;

	public SprintList() {
		super();
	}

	public SprintList(Integer id, String name, String sprintName, List<SprintTask> task) {
		super();
		this.id = id;
		this.name = name;
		this.sprintName = sprintName;
		this.task = task;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSprintName() {
		return sprintName;
	}

	public void setSprintName(String sprintName) {
		this.sprintName = sprintName;
	}

	public List<SprintTask> getTask() {
		return task;
	}

	public void setTask(List<SprintTask> task) {
		this.task = task;
	}

	@Override
	public String toString() {
		return "SprintList\n\t [id: " + id + ",\n\t name: " + name + ",\n\t sprintName: " + sprintName + "]";
	}
	
}
