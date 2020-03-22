package org.codejudge.sb.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.stereotype.Service;

@Service
@Entity
@Table(name = "Lead",uniqueConstraints =
{		@UniqueConstraint(columnNames =  {"mobile"}),
		@UniqueConstraint(columnNames =  {"email"})
}
)
public class Lead {

	private String first_name;
	private String last_name;
	private int mobile;
	@Id
	private String email;
	@Enumerated(EnumType.STRING)
	private Location_type location_type;
	private String location_string;
	@Enumerated(EnumType.STRING)
	private Status status;
	
	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public int getMobile() {
		return mobile;
	}
	public void setMobile(int mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Location_type getLocation_type() {
		return location_type;
	}
	public void setLocation_type(Location_type location_type) {
		this.location_type = location_type;
	}
	public String getLocation_string() {
		return location_string;
	}
	public void setLocation_string(String location_string) {
		this.location_string = location_string;
	}
	
	

}


