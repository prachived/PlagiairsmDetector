package com.plagiarism.detector.strategies;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.tree.ParseTree;

import grammar.Python3Lexer;
import grammar.Python3Parser;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import com.plagiarism.detector.model.Outcome;
import com.plagiarism.detector.model.Result;
import com.plagiarism.detector.techniques.LevenshteinDistance;
import com.plagiarism.detector.utilities.FileUtility;

/**
 * Class to compare if two source code files differ only by the variable names
 * 
 * @author Prachi
 * @version 1.0
 *
 */
public class VariableCheck extends ComparisonStrategy {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final String READFOLDER = "uploads";

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
	public List<Result> compareFilesForVariablePlagiarism(List<String> student1, List<String> student2)
			throws IOException {
		int resultsDone = 0;
		List<Result> results = new ArrayList<>();
		for (String file1 : student1) {
			for (String file2 : student2) {

				Result result = new Result(file1, file2);
				String file1AST = generateAST(file1);
				String file2AST = generateAST(file2);
				LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
				Outcome outcome = levenshteinDistance.getPlagiarismPercentage(file1AST, file2AST);
				result.setOutcome(outcome);
				results.add(result);
				int totalFiles = student1.size() * student2.size();
				resultsDone += 1;
				LOGGER.info(
						String.format("Running VariableCheck: %s files done out of %s files", resultsDone, totalFiles));

			}
		}
		return results;
	}

	/**
	 * function to generate AST of a file
	 * 
	 * @param filename
	 *            Name of file
	 * @param name
	 * @return AST of file
	 * @throws IOException
	 */
	public String generateAST(String filename) throws IOException {
		FileUtility fileUtility = new FileUtility();
		String simplestProgram = fileUtility.readFromS3(READFOLDER, filename);
		ANTLRInputStream inputCharStream = new ANTLRInputStream(new StringReader(simplestProgram));
		TokenSource tokenSource = new Python3Lexer(inputCharStream);
		CommonTokenStream inputTokenStream = new CommonTokenStream(tokenSource);
		Python3Parser parser = new Python3Parser(inputTokenStream);
		ParseTree tree = parser.file_input();
		return tree.toStringTree(parser);
	}
}
