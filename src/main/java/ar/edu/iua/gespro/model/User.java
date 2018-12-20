package ar.edu.iua.gespro.model;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class User implements UserDetails {

	private static final long serialVersionUID = -3552525853367219773L;

	private boolean accountNonExpired = true;

	private boolean accountNonLocked = true;

	private boolean credentialsNonExpired = true;

	@Column(length = 255)
	private String email;
	private boolean enabled;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUser;
	@Column(length = 100)
	private String name;
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "userroles", joinColumns = {
			@JoinColumn(name = "idUser", referencedColumnName = "idUser") }, inverseJoinColumns = {
					@JoinColumn(name = "idRole", referencedColumnName = "id") })
	private Set<Role> roles;

	@Column(length = 200)
	private String username;

	@Transient
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		//List<Role> rolesOrdenados=getRoles().stream().sorted().collect(Collectors.toList());
		
		//List<Role> rolesFiltrados=rolesOrdenados.stream().filter(r -> r.getId()%2 == 0).collect(Collectors.toList());
		
		//Filtro tradicional
		//List<Role> rolesFiltrados1=new ArrayList<Role>();
		//for(Role r:rolesOrdenados) {
		//	if(r.getId()%2 == 0) {
		//		rolesFiltrados1.add(r);
		//	}
		//}		getRoles().stream().map(r -> {System.out.println(r); return r;}).collect(Collectors.toList());

		
		//Version en una sola línea
		//List<GrantedAuthority> authorities =rolesFiltrados.stream()
		//		.map(role -> new SimpleGrantedAuthority(role.getName()))
		//		.collect(Collectors.toList());
		
		//List<GrantedAuthority> authorities1= getRoles().stream().sorted().collect(Collectors.toList())
		//		.stream().filter(r -> r.getId()%2 == 0).collect(Collectors.toList())
		//		.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		
		//getRoles().stream().map(r -> {System.out.println(r); return r;}).collect(Collectors.toList());
		//getRoles().stream().map(r -> {System.out.println(r); return r;}).limit(3).collect(Collectors.toList());
		List<GrantedAuthority> authorities =getRoles().stream()
						.map(role -> new SimpleGrantedAuthority(role.getName()))
						.collect(Collectors.toList());
		return authorities;
	}

	
	
	
	public String getEmail() {
		return email;
	}

	public Long getIdUser() {
		return idUser;
	}

	public String getName() {
		return name;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
