package com.plagiarism.detector.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * User entity table
 * @author Monica
 *
 */
/**
 * @author Prachi
 *
 */
@Entity // This tells Hibernate to make a table out of this class
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class User {
	/**
	 * auto generated id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	/**
	 * Name of user
	 */

	@Autowired
	@Column(name = "name", nullable = false)
	private String name;

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	/**
	 * password
	 */
	@Column(name = "password", nullable = false)
	private String password;

	/**
	 * fname
	 */
	@Column(name = "fname", nullable = false)
	private String fname;

	/**
	 * lname
	 */
	@Column(name = "lname", nullable = false)
	private String lname;

	@Autowired
	@Version
	@Column(name = "runCount", nullable = false)
	private int runCount;

	/**
	 * @return number of times the system is run by a particular user
	 */
	public int getRunCount() {
		return runCount;
	}

	/**
	 * @param runCount
	 */
	public void setRunCount(int runCount) {
		this.runCount = runCount;
	}

	public User(String name2) {
		this.setName(name2);
	}

	public User() {

	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * email of user
	 */
	@Column(nullable = false, name = "email")
	private String email;

	/**
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (id == null || obj == null || getClass() != obj.getClass())
			return false;
		User that = (User) obj;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}

}