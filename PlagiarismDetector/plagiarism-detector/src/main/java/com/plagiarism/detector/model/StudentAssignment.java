package com.plagiarism.detector.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Prachi
 * 
 * Class acts as a data type to store all assignments uploaded by a particulare student
 *
 */
public class StudentAssignment {

	/**
	 * Name of the folder of student
	 */
	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	/**
	 * List of file names belonging to a student
	 */
	List<String> studentFile = new ArrayList<>();

	public List<String> getStudentFile() {
		return studentFile;
	}


}
