package com.plagiarism.detector.model;

import java.util.List;

/**
 * @author Prachi
 * 
 *         Class to store results of running the plagiarism check
 *
 */
public class Result {

	public Result(String filename, String filename2) {
		this.file1 = filename;
		this.file2 = filename2;
	}

	public Result(String filename, String filename2, List<Integer> file1lineNum, List<Integer> file2lineNum) {
		this.file1 = filename;
		this.file2 = filename2;
		this.file1lineNum = file1lineNum;
		this.file2lineNum = file2lineNum;
	}

	public Result(List<Integer> file1lineNum, List<Integer> file2lineNum, Outcome outcome) {
		this.file1lineNum = file1lineNum;
		this.file2lineNum = file2lineNum;
		this.outcome = outcome;
	}

	public void setFile1lineNum(List<Integer> file1lineNum) {
		this.file1lineNum = file1lineNum;
	}

	public void setFile2lineNum(List<Integer> file2lineNum) {
		this.file2lineNum = file2lineNum;
	}

	public Result() {
	}

	public Result(Outcome outcome, String summary) {
		this.outcome = outcome;
		this.summary = summary;

	}

	/**
	 * File 1: compared to File 2: compared against Outcome: result of comparing
	 * file 1 to file 2
	 */
	String file1;
	String file2;

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	Outcome outcome;
	String summary;

	List<Integer> file1lineNum;
	List<Integer> file2lineNum;

	public List<Integer> getFile1lineNum() {
		return file1lineNum;
	}

	public List<Integer> getFile2lineNum() {
		return file2lineNum;
	}

	public void setFile1(String file1) {
		this.file1 = file1;
	}

	public void setFile2(String file2) {
		this.file2 = file2;
	}

	/**
	 * getters and setters for all variables of Result
	 */
	public String getFile1() {
		return file1;
	}

	/**
	 * @return file 2
	 */
	public String getFile2() {
		return file2;
	}

	/**
	 * @return outcome of type Outcome
	 */
	public Outcome getOutcome() {
		return outcome;
	}

	public void setOutcome(Outcome outcome) {
		this.outcome = outcome;
	}

}
