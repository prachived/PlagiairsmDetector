package com.plagiarism.detector.strategies;

import java.util.Arrays;

import com.plagiarism.detector.model.Outcome;
import com.plagiarism.detector.techniques.LevenshteinDistance;

public class PlagiarismComments {

	/***
	 * @author Preyank Jain
	 * 
	 * @param file1
	 * @param file2
	 * @return whether file1 and file2 are plagiarized or not
	 */

	public Outcome getCommentPlagiarismResult(String file1, String file2) {

		String file1Comments = extractCommentsFromFile(file1);
		char[] comments = file1Comments.toCharArray();
		Arrays.sort(comments);
		String commentsFile1 = new String(comments);

		String file2Comments = extractCommentsFromFile(file2);
		char[] comments2 = file2Comments.toCharArray();
		Arrays.sort(comments2);
		String commentsFile2 = new String(comments2);
		final Double THRESHOLD = 0.05;

		int similarityPerc = LevenshteinDistance.calculateSimilarity(commentsFile1, commentsFile2);

		int minFileLength = Math.min(commentsFile1.length(), commentsFile2.length());

		if (minFileLength == 0)
			return Outcome.NOTPLAGIARIZED;

		if (((double) similarityPerc / minFileLength) < THRESHOLD) {
			return Outcome.PLAGIARIZED;
		} else
			return Outcome.NOTPLAGIARIZED;
	}

	/***
	 * @author Preyank Jain
	 * 
	 * @param file
	 * @return only the comments of file
	 */
	public String extractCommentsFromFile(String file) {
		StringBuilder comments = new StringBuilder();
		String[] lines = file.split("\n");
		for (String l : lines) {
			if (l.split("#").length > 1)
				comments.append(l.split("#")[1]);
		}
		return comments.toString();
	}
}