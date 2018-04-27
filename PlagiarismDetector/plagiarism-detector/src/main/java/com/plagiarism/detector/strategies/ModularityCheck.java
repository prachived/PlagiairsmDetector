package com.plagiarism.detector.strategies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.plagiarism.detector.model.Result;
import com.plagiarism.detector.strategies.ComparisonStrategy;
import com.plagiarism.detector.utilities.FileUtility;

/**
 * @author Prachi
 *
 *         This class checks if a structured code in one file is broken down
 *         into modules in another file.
 *
 */
public class ModularityCheck extends ComparisonStrategy {
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
	List<Result> compareFilesForModularity(List<String> student1, List<String> student2) throws IOException {
		List<Result> results = new ArrayList<>();
		int resultsDone = 0;
		for (String file1 : student1) {
			for (String file2 : student2) {
				FileUtility fileUtility = new FileUtility();
				String fileProgram1 = fileUtility.readFromS3("uploads", file1);
				String fileProgram2 = fileUtility.readFromS3("uploads", file2);
				PlagiarismModularity plagiarismModularity = new PlagiarismModularity();
				Result result = plagiarismModularity.getPlagiarismSimilarity(file1, file2, fileProgram1, fileProgram2);
				result.setFile1(file1);
				result.setFile2(file2);
				results.add(result);
				int totalFiles = student1.size() * student2.size();
				resultsDone += 1;
				LOGGER.info(String.format("Running ModularityCheck: %s files done out of %s files", resultsDone,
						totalFiles));
			}
		}
		return results;
	}
}