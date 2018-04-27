package com.plagiarism.detector.strategies;

import java.util.ArrayList;
import java.util.List;

import com.plagiarism.detector.model.Outcome;
import com.plagiarism.detector.model.Result;

/**
 * class to compute weighted score of all strategies used to detect plagiarism
 * and then get a final score.
 * 
 * @author ashuk
 *
 */
public class WeightedResults {

	/**
	 * Weights given to each strategy
	 */
	static final double WEIGHT_COMMENT_CHECK = 0.30;
	static final double WEIGHT_VARIABLE_CHECK = 0.20;
	static final double WEIGHT_CODE_MOVEMENT = 0.30;
	static final double WEIGHT_CODE_MODULARITY = 0.20;
	static final String FONT = "<font color='red'>"; // string representing font color red
	static final String FONTEND = "</font>"; // string to represent font end

	/**
	 * constants for various strings
	 */
	static final String COMMENTCHANGE_SUMMARY = " Based on Similarity in Comments:";
	static final String VARIABLECHANGE_SUMMARY = " Based on Change in Variable name:";
	static final String CODEMOVEOVER_SUMMARY = " Based on Code move over:";
	static final String MODULARITY_SUMMARY = " Based on Code modularization:";
	static final String SUMM_1 = "These two files are ";
	static final String SUMM_2 = " based on following results form different strategies";
	static final String NOT_PLAGIARIZED_SUMMARY = SUMM_1 + "<font color='blue'>" + "NOT PLAGIARIZED" + FONTEND + SUMM_2;
	static final String FILE_SUSCEPTIBILITY_SUMMARY = SUMM_1 + "<font color='orange'>" + " SUSCEPTIBLE" + FONTEND
			+ SUMM_2;
	static final String FILE_PLAGIARIZED_SUMMARY = SUMM_1 + FONT + "PLAGIARIZED" + FONTEND + SUMM_2;

	/**
	 * constants for similarity threshold
	 */
	static final double RANGE_1A = 0;
	static final double RANGE_1B = 0.50;
	static final double RANGE_2A = 0.51;
	static final double RANGE_2B = 0.80;
	static final double RANGE_3A = 0.81;
	static final double RANGE_3B = 1.0;

	/**
	 * This method gets the result of comparison of two files if they are similar or
	 * not.
	 * 
	 * @return List of Outcomes of comparing student files
	 */
	public List<Result> getWeightedSimilarity() {

		FunctionalityFactory factory = FunctionalityFactory.instance();
		ComparisonStrategy modularityCheck = factory.makeModularityCheckInstance();

		List<Result> resultModularity = modularityCheck.getSimilarity();

		ComparisonStrategy codeMoveOverCheck = factory.makeCodeMoveOverCheckInstance();
		List<Result> resultCodeMoveOver = codeMoveOverCheck.getSimilarity();

		ComparisonStrategy variableCheck = factory.makeVariableCheckInstance();
		List<Result> resultVariableNameChange = variableCheck.getSimilarity();

		ComparisonStrategy commentCheck = factory.makeCommentsCheckInstance();
		List<Result> resultCommentCheck = commentCheck.getSimilarity();

		List<Result> resultFinal = new ArrayList<>();

		for (int i = 0; i < resultModularity.size(); i++) {
			StringBuilder summary = new StringBuilder();
			Outcome weightedOutcome = Outcome.NOTPLAGIARIZED;
			double weightedScore = (WEIGHT_CODE_MODULARITY * resultModularity.get(i).getOutcome().ordinal())
					+ (WEIGHT_VARIABLE_CHECK * resultVariableNameChange.get(i).getOutcome().ordinal())
					+ (WEIGHT_CODE_MOVEMENT * resultCodeMoveOver.get(i).getOutcome().ordinal()
							+ (WEIGHT_COMMENT_CHECK * resultCommentCheck.get(i).getOutcome().ordinal()));

			if (weightedScore >= RANGE_2A && weightedScore <= RANGE_2B) {
				weightedOutcome = Outcome.NOTPLAGIARIZED;
				summary.append(FILE_SUSCEPTIBILITY_SUMMARY + "\n " + VARIABLECHANGE_SUMMARY + FONT
						+ resultVariableNameChange.get(i).getOutcome().name() + FONTEND + "\n " + CODEMOVEOVER_SUMMARY
						+ FONT + resultCodeMoveOver.get(i).getOutcome().name() + FONTEND + "\n " + MODULARITY_SUMMARY
						+ FONT + resultModularity.get(i).getOutcome().name() + FONTEND + "\n " + COMMENTCHANGE_SUMMARY
						+ FONT + resultCommentCheck.get(i).getOutcome().name() + FONTEND + "\n ");
			} else if (weightedScore >= RANGE_3A && weightedScore <= RANGE_3B) {
				weightedOutcome = Outcome.NOTPLAGIARIZED;
				summary.append(NOT_PLAGIARIZED_SUMMARY + "\n " + VARIABLECHANGE_SUMMARY + FONT
						+ resultVariableNameChange.get(i).getOutcome().name() + FONTEND + "\n " + CODEMOVEOVER_SUMMARY
						+ FONT + resultCodeMoveOver.get(i).getOutcome().name() + FONTEND + "\n " + MODULARITY_SUMMARY
						+ FONT + resultModularity.get(i).getOutcome().name() + FONTEND + "\n " + COMMENTCHANGE_SUMMARY
						+ FONT + resultCommentCheck.get(i).getOutcome().name() + FONTEND + "\n ");
			} else if (weightedScore >= RANGE_1A && weightedScore <= RANGE_1B) {
				weightedOutcome = Outcome.PLAGIARIZED;
				summary.append(FILE_PLAGIARIZED_SUMMARY + "\n " + VARIABLECHANGE_SUMMARY + FONT
						+ resultVariableNameChange.get(i).getOutcome().name() + FONTEND + "\n " + CODEMOVEOVER_SUMMARY
						+ FONT + resultCodeMoveOver.get(i).getOutcome().name() + FONTEND + "\n " + MODULARITY_SUMMARY
						+ FONT + resultModularity.get(i).getOutcome().name() + FONTEND + "\n " + COMMENTCHANGE_SUMMARY
						+ FONT + resultCommentCheck.get(i).getOutcome().name() + FONTEND + "\n ");
			}

			// creating result object and setting file name, outcome and summary
			Result tempRes = new Result(resultModularity.get(i).getFile1(), resultModularity.get(i).getFile2());
			tempRes.setOutcome(weightedOutcome);
			tempRes.setSummary(summary.toString());
			resultFinal.add(tempRes);
		}
		return resultFinal;
	}

}