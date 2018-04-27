package com.plagiarism.detector.strategies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.plagiarism.detector.model.Result;
import com.plagiarism.detector.utilities.FileUtility;

/**
 * Class to compare two source code files for code move over check
 * 
 * @author Prachi
 * @version 1.0
 *
 */
public class CodeMoveOverCheck extends ComparisonStrategy {

	private static final Logger LOGGER = LogManager.getLogger();

	/**
	 * Compare all the files per student to another student's files
	 * 
	 * @param student1
	 *            : List of files belonging to student 1
	 * @param student2
	 *            : List of files belonging to student 2
	 * @param name2
	 * @param name
	 * @return : List of Outcomes of comparison of files
	 * @throws IOException
	 */
	public List<Result> compareFilesForCodeMoveOverPlagiarism(List<String> student1, List<String> student2)
			throws IOException {
		int resultsDone = 0;
		List<Result> results = new ArrayList<>();
		for (String file1 : student1) {
			for (String file2 : student2) {
				FileUtility fileUtility = new FileUtility();
				PlagiarismCodeMoveOver plagiarismCodeMoveOver = new PlagiarismCodeMoveOver();
				String fileProgram1 = fileUtility.readFromS3("uploads", file1);
				String fileProgram2 = fileUtility.readFromS3("uploads", file2);
				Result result = plagiarismCodeMoveOver.getCodeMoveOverPlagiarismResult(fileProgram1, fileProgram2);
				result.setFile1(file1);
				result.setFile2(file2);
				results.add(result);
				int totalFiles = student1.size() * student2.size();
				resultsDone += 1;
				LOGGER.info(String.format("Running CodeMoveOverCheck: %s files done out of %s files", resultsDone,
						totalFiles));

			}

		}
		return results;
	}
}
