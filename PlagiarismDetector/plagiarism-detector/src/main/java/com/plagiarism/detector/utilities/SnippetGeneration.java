package com.plagiarism.detector.utilities;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.List;

import com.plagiarism.detector.model.Outcome;

public class SnippetGeneration {

	
	/**
	 * constant strings being used at various places
	 */
	static final String PART1 = "/Part1.txt";
	static final String PART2 = "/Part2.txt";
	static final String PART3 = "/Part3.txt";
	static final String SKELETON_REPORT = "skeleton-report";
	static final String RESULT = "/results/";
	static final String HTML = ".html";
	static final String UPLOAD = "uploads";
	static final String RED_COLOR = "<pre><font color='red'>";
	static final String PRE_FONT = "</font></pre>";
	static final String PRE = "<pre>";
	static final String END_PRE = "</pre>";

	/**
	 * 
	 * make html skeleton
	 * 
	 * @param file1
	 * @param file2
	 * @param list
	 * @param list2
	 * @throws IOException
	 */
	public void makeHTML(String file1, String file2, List<Integer> list, List<Integer> list2) throws IOException {
		String currpath = Paths.get("").toAbsolutePath().toString();
		FileUtility fileUtility = new FileUtility();
		String part1 = fileUtility.readFromS3(SKELETON_REPORT, PART1);
		String part2 = fileUtility.readFromS3(SKELETON_REPORT, PART2);
		String part3 = fileUtility.readFromS3(SKELETON_REPORT, PART3);

		String html = part1;
		html += getFormattedFile(file1, list);
		html += part2;
		html += getFormattedFile(file2, list2);
		html += part3;

		String resFileName = file1.replace("/", "").replace("\\", "") + file2.replace("/", "").replace("\\", "");

		String res = currpath + RESULT + resFileName + HTML;
		fileUtility.writeFile(res, html);
	}

	/**
	 * get html for line numbers formatted
	 * 
	 * @param file
	 * @param list
	 * @return
	 * @throws IOException
	 */
	public String getFormattedFile(String file, List<Integer> list) throws IOException {
		FileUtility fileUtility = new FileUtility();
		String fileContents = fileUtility.readFromS3(UPLOAD, file);
		StringBuilder html = new StringBuilder("");
		InputStream fstream = new ByteArrayInputStream(fileContents.getBytes());
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		try {
			String strLine;
			int lineIndex = 0;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				if (!strLine.equals("")) {
					if (list.contains(lineIndex))
						html.append(RED_COLOR + strLine + PRE_FONT);
					else
						html.append(PRE + strLine + END_PRE);
				}
				lineIndex++;
			}
		} finally {
			br.close();
		}
		// Close the input stream

		return html.toString();
	}

	/**
	 * get html skeleton for summary
	 * 
	 * @param file1
	 * @param file2
	 * @param summary
	 * @throws IOException
	 */
	public void makeHTMLForSummary(String file1, String file2, String summary) throws IOException {
		String currpath = Paths.get("").toAbsolutePath().toString();
		FileUtility fileUtility = new FileUtility();
		String part1 = fileUtility.readFromS3(SKELETON_REPORT, "/Part1Summ.txt");
		String part2 = fileUtility.readFromS3(SKELETON_REPORT, "/Part2Summ.txt");
		String html = part1;
		html += summary;
		html += part2;

		String resFileName = file1.replace("/", "").replace("\\", "") + file2.replace("/", "").replace("\\", "");

		String res = currpath + RESULT + resFileName + HTML;
		fileUtility.writeFile(res, html);

	}

	/**
	 * get html skeleton for variable check
	 * 
	 * @param file1
	 * @param file2
	 * @param outcome
	 * @throws IOException
	 */
	public void makeHTMLForVariableCheck(String file1, String file2, Outcome outcome) throws IOException {
		String currpath = Paths.get("").toAbsolutePath().toString();
		FileUtility fileUtility = new FileUtility();
		String part1 = fileUtility.readFromS3(SKELETON_REPORT, PART1);
		String part2 = fileUtility.readFromS3(SKELETON_REPORT, PART2);
		String part3 = fileUtility.readFromS3(SKELETON_REPORT, PART3);

		String html = part1;
		html += getFormattedFileBasedOnOutcome(file1, outcome);
		html += part2;
		html += getFormattedFileBasedOnOutcome(file2, outcome);
		html += part3;

		String resFileName = file1.replace("/", "").replace("\\", "") + file2.replace("/", "").replace("\\", "");

		String res = currpath + RESULT + resFileName + HTML;
		fileUtility.writeFile(res, html);

	}

	/**
	 * Get formatted file for variable check
	 * 
	 * @param file
	 * @param outcome
	 * @return
	 * @throws IOException
	 */
	public String getFormattedFileBasedOnOutcome(String file, Outcome outcome) throws IOException {
		FileUtility fileUtility = new FileUtility();
		String fileContents = fileUtility.readFromS3(UPLOAD, file);
		StringBuilder html = new StringBuilder("");
		InputStream fstream = new ByteArrayInputStream(fileContents.getBytes());
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		try {
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				if (!strLine.equals("")) {
					if (outcome.equals(Outcome.PLAGIARIZED))
						html.append(RED_COLOR + strLine + PRE_FONT);

					else
						html.append(PRE + strLine + END_PRE);

				}
			}
		} finally {
			br.close();
		}
		// Close the input stream

		return html.toString();
	}

	/**
	 * get html skeleton for variable check
	 * 
	 * @param file1
	 * @param file2
	 * @param outcome
	 * @throws IOException
	 */
	public void makeHTMLforCommentsCheck(String file1, String file2, Outcome outcome) throws IOException {
		String currpath = Paths.get("").toAbsolutePath().toString();
		FileUtility fileUtility = new FileUtility();
		String part1 = fileUtility.readFromS3(SKELETON_REPORT, PART1);
		String part2 = fileUtility.readFromS3(SKELETON_REPORT, PART2);
		String part3 = fileUtility.readFromS3(SKELETON_REPORT, PART3);

		String html = part1;
		html += getFormattedFileBasedOnCommentOutcome(file1, outcome);
		html += part2;
		html += getFormattedFileBasedOnCommentOutcome(file2, outcome);
		html += part3;

		String resFileName = file1.replace("/", "").replace("\\", "") + file2.replace("/", "").replace("\\", "");

		String res = currpath + RESULT + resFileName + HTML;
		fileUtility.writeFile(res, html);

	}

	/**
	 * Get formatted file for variable check
	 * 
	 * @param file
	 * @param outcome
	 * @return
	 * @throws IOException
	 */
	public String getFormattedFileBasedOnCommentOutcome(String file, Outcome outcome) throws IOException {
		FileUtility fileUtility = new FileUtility();
		String fileContents = fileUtility.readFromS3(UPLOAD, file);
		StringBuilder html = new StringBuilder("");
		InputStream fstream = new ByteArrayInputStream(fileContents.getBytes());
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		try {
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				if (!strLine.equals("")) {
					if (outcome.equals(Outcome.PLAGIARIZED) && strLine.contains("#"))
						html.append(RED_COLOR + strLine + PRE_FONT);
					else
						html.append(PRE + strLine + END_PRE);
				}
			}
		} finally {
			br.close();
		}
		// Close the input stream

		return html.toString();
	}

}