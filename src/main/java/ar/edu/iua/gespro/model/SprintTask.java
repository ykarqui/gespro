package ar.edu.iua.gespro.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "sprint_task")
public class SprintTask {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "task_id", nullable = false)
	private Integer id;
	
	@Column(name = "name")
	private String name;
	@Column(name = "created")
	private Date created;
	@Column(name = "modified")
	private Date modified;
	@Column(name = "priority")
	private String priority;
	
	@ManyToOne
	@JoinColumn(name = "list_id")
	@JsonIgnoreProperties("task")
	private SprintList listName;
	@Column(name = "estimate")
	private Integer estimate;

	public SprintTask() {
		super();
	}

	public SprintTask(Integer id, String name, Date created, Date modified, String priority, SprintList listName,
			Integer estimate) {
		super();
		this.id = id;
		this.name = name;
		this.created = created;
		this.modified = modified;
		this.priority = priority;
		this.listName = listName;
		this.estimate = estimate;
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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public SprintList getListName() {
		return listName;
	}

	public void setListName(SprintList listName) {
		this.listName = listName;
	}

	public Integer getEstimate() {
		return estimate;
	}

	public void setEstimate(Integer estimate) {
		this.estimate = estimate;
	}

	@Override
	public String toString() {
		return "SprintTask\n\t [id: " + id + ",\n\t name: " + name + ",\n\t created: " + created + ",\n\t modified: "
				+ modified + ",\n\t priority: " + priority + ",\n\t listName: " + listName.getName() + ",\n\t estimate: "
				+ estimate + "]";
	}
	
	
}
