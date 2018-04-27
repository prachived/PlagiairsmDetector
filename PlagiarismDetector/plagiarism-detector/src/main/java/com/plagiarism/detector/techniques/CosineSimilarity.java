package com.plagiarism.detector.techniques;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 
 * Class that provides object to determine Cosine similarity between two
 * vectors.
 * 
 * @author ashuk
 *
 */
public class CosineSimilarity {

	/**
	 * returns the cosine distance between two vectors
	 * 
	 * @param file1
	 *            vector of file1
	 * @param file2
	 *            vector of file2
	 * @return cosine distance between 2 vectors.
	 */
	public Double getCosineSimilarity(Map<String, Integer> file1, Map<String, Integer> file2) {

		double similarityValue;
		double product = 0;
		double squaresFile1Count = 0;
		double squareFile2Count = 0;
		Set<String> commonRules = null;

		if (file1 == null || file2 == null) {
			throw new IllegalArgumentException("Files are empty");
		}

		commonRules = getCommonRules(file1, file2);

		// calculating product
		for (String key : commonRules) {
			product += file1.get(key) * file2.get(key);
		}

		// finding sum of squares
		for (Integer value : file1.values()) {
			squaresFile1Count += Math.pow(value, 2);
		}

		// finding sum of squares
		for (Integer value : file2.values()) {
			squareFile2Count += Math.pow(value, 2);
		}

		similarityValue = getSimilarityValue(product, squaresFile1Count, squareFile2Count);

		return similarityValue;

	}

	/**
	 * returns the set of commonRules in the two given vectors.
	 * 
	 * @param file1
	 *            vector of the file 1
	 * @param file2
	 *            vector of the file 2
	 * @return set of common rules in the two vectors.
	 */
	public Set<String> getCommonRules(Map<String, Integer> file1, Map<String, Integer> file2) {

		Set<String> commonRules = new HashSet<>();

		for (String key : file1.keySet()) {
			if (file2.keySet().contains(key)) {
				commonRules.add(key);
			}
		}

		for (String key : file2.keySet()) {
			if (file1.keySet().contains(key)) {
				commonRules.add(key);
			}
		}
		return commonRules;
	}

	/**
	 * calculates and returns the cosine distance
	 * 
	 * @param product
	 *            dot product of two vectors
	 * @param squareVector1
	 *            sum of squares of 1st vector
	 * @param squareVector2
	 *            sum of squares of 2nd vector
	 * @return cosine distance.
	 */
	public double getSimilarityValue(double product, double squareVector1, double squareVector2) {

		double similarityValue;

		similarityValue = (product / (Math.sqrt(squareVector1) * Math.sqrt(squareVector2)));
		return similarityValue;
	}

}
