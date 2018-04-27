package com.plagiarism.detector.techniques;

import com.plagiarism.detector.model.Outcome;

/**
 *
 * PlagiarismPercentage returns an object that compares the two codes that are
 * fed into the method getPlagiarismPercentage as strings, that are the codes of
 * the two input files.
 * 
 * @author ashuk
 */
public class LevenshteinDistance {
	static final int LEVENSHTEIN_THRESHOLD = 10;

	/**
	 * 
	 * computes the levenshtein distance for two given strings.
	 * 
	 * @param prg1
	 *            represents the string that has code for the first code file.
	 * 
	 * @param prg2
	 *            represents the string that has code for the second code file.
	 * 
	 * @return the number of changes that has to be made to convert 1st string to
	 *         another.
	 * 
	 * 
	 * @author ashuk
	 */
	public static int calculateSimilarity(String prg1, String prg2) {

		int length1 = prg1.length();
		int length2 = prg2.length();

		int[][] table = new int[length1 + 1][length2 + 1];

		for (int i = 0; i <= length1; i++) {
			for (int j = 0; j <= length2; j++) {

				if (i == 0) {
					table[i][j] = j;
				} else if (j == 0) {
					table[i][j] = i;
				} else if (prg1.charAt(i - 1) == prg2.charAt(j - 1)) {
					table[i][j] = table[i - 1][j - 1];
				} else {
					table[i][j] = 1 + minimum(table[i][j - 1], table[i - 1][j], table[i - 1][j - 1]);
				}
			}
		}

		return table[length1][length2];
	}

	/**
	 * 
	 * computes minimum of the three values.
	 * 
	 * @param one
	 *            the cost of insertion
	 * 
	 * @param two
	 *            the cost of deletion
	 * 
	 * @param three
	 *            the cost of removal
	 * 
	 * @return minimum of the 3 parameters.
	 * 
	 * @author ashuk
	 */
	public static int minimum(int one, int two, int three) {
		if ((one <= two) && (one <= three)) {
			return one;
		}
		if ((two <= one) && (two <= three)) {
			return two;
		} else
			return three;
	}

	/**
	 * computes and displays if two given files have plagiarism.
	 * 
	 * @param program1
	 *            String having code of 1st file.
	 * 
	 * @param program2
	 *            String having code of 2nd file.
	 * 
	 * @return String to check with the accepted outcome.
	 * 
	 * @author ashuk
	 */
	public Outcome getPlagiarismPercentage(String program1, String program2) {
		Outcome outcome;
		int minFileLength;
		int file1Length = program1.length();
		int file2Length = program2.length();
		if (file1Length < file2Length) {
			minFileLength = file1Length;
		} else {
			minFileLength = file2Length;
		}
		int threshold = minFileLength / LEVENSHTEIN_THRESHOLD;
		int result = calculateSimilarity(program1, program2);
		if (result < threshold) {
			outcome = Outcome.PLAGIARIZED;
		} else {
			outcome = Outcome.NOTPLAGIARIZED;
		}

		return outcome;
	}

}
