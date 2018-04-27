package com.plagiarism.detector.strategies;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.tree.ParseTree;
import com.plagiarism.detector.model.Outcome;
import com.plagiarism.detector.model.Result;
import com.plagiarism.detector.techniques.CosineSimilarity;
import grammar.Python3Lexer;
import grammar.Python3Parser;

/**
 * This class provides object to determine the plagiarism in two files on basis
 * of code modularity.
 * 
 * @author ashuk
 *
 */
public class PlagiarismModularity {
	static final String FONT_TAG = "</font>";
	static final double SIMILARITY_THRESHOLD = 0.98;
	CosineSimilarity cosineSimilarity = new CosineSimilarity();

	// Vector for file1 (HashMap)
	Map<String, Integer> file1Vector = new HashMap<>();

	// Vector for file2 (HashMap)
	Map<String, Integer> file2Vector = new HashMap<>();
	private StringBuilder summary;

	/**
	 * returns the outcome of plagiarism check on the basis of Code modularity.
	 * 
	 * @param file1
	 *            is the name of file1
	 * @param file2
	 *            is the name of file2
	 * @param filename2
	 * @param filename1
	 * @return outcome of modularity check on code.
	 * @throws IOException
	 */
	public Result getPlagiarismSimilarity(String filename1, String filename2, String file1, String file2)
			throws IOException {
		Result result = new Result();
		Outcome outcome;
		ANTLRInputStream inputCharStream1 = new ANTLRInputStream(new StringReader(file1));
		TokenSource lexer1 = new Python3Lexer(inputCharStream1);
		Python3Parser parser1 = new Python3Parser(new CommonTokenStream(lexer1));
		ANTLRInputStream inputCharStream2 = new ANTLRInputStream(new StringReader(file2));
		TokenSource lexer2 = new Python3Lexer(inputCharStream2);
		Python3Parser parser2 = new Python3Parser(new CommonTokenStream(lexer2));
		file1Vector.putAll(explore(parser1.file_input(), false, 0, new HashMap<>()));
		file2Vector.putAll(explore(parser2.file_input(), false, 0, new HashMap<>()));

		double similarityValue = cosineSimilarity.getCosineSimilarity(file1Vector, file2Vector);
		if (similarityValue < SIMILARITY_THRESHOLD) {
			outcome = Outcome.NOTPLAGIARIZED;
			result.setOutcome(outcome);
			summaryBuilder(filename1, filename2, file1Vector, file2Vector, 0);
			result.setSummary(summary.toString());
		} else {
			outcome = Outcome.PLAGIARIZED;
			result.setOutcome(outcome);
			summaryBuilder(filename1, filename2, file1Vector, file2Vector, 1);
			result.setSummary(summary.toString());
		}
		return result;
	}

	/***
	 * build the summary report for this modularity check
	 * 
	 * @param filename1
	 * @param filename2
	 * @param file1Vector
	 * @param file2Vector
	 * @param flag
	 */
	public void summaryBuilder(String filename1, String filename2, Map<String, Integer> file1Vector,
			Map<String, Integer> file2Vector, int flag) {
		summary = new StringBuilder();
		if (flag == 1) {
			summary.append(
					"These files are <font color='red'><b> PLAGIARIZED </b></font> for plagiarism based on code modularization based on similar number of occurences of following rule names of Python 3 grammar.");
			for (Map.Entry<String, Integer> entry : file1Vector.entrySet()) {
				if (file2Vector.containsKey(entry.getKey())) {
					summary.append("\n " + "<font color='blue'>" + entry.getKey() + FONT_TAG + ": " + filename1 + "  "
							+ "<font color='blue'>" + file1Vector.get(entry.getKey()) + FONT_TAG + " " + filename2
							+ "  " + "<font color='red'>" + file2Vector.get(entry.getKey()) + FONT_TAG + "\n");
				}
			}
		} else {
			summary.append(
					"These two files are <font color='blue'><b> NOT PLAGIARIZED </b></font>  on the basis of modularity")
					.toString();
		}
	}

	/***
	 * @return the summary for this modularity check
	 */
	public String getSummary() {
		return summary.toString();
	}

	/**
	 * to determine the rule of given context and append to map.
	 * 
	 * @param ctx
	 *            context of the rule
	 * @param verbose
	 * @param indentation
	 *            for printing AST
	 * @param map
	 *            to put the rule name and count to map recursively.
	 * @return map for AST's method.
	 */
	public Map<String, Integer> explore(RuleContext ctx, boolean verbose, int indentation, Map<String, Integer> map) {
		boolean toBeIgnored = !verbose && ctx.getChildCount() == 1 && ctx.getChild(0) instanceof ParserRuleContext;
		if (!toBeIgnored) {
			// Keep a count of which rulename occurs how many times in a tree
			String ruleName = Python3Parser.ruleNames[ctx.getRuleIndex()];
			if (indentation != 0 && (!map.containsKey(ruleName))) {
				map.put(ruleName, 0);
			}
			if (indentation != 0 && (map.containsKey(ruleName))) {
				map.put(ruleName, 1 + map.get(ruleName));
			}
		}
		// consider child nodes too
		for (int i = 0; i < ctx.getChildCount(); i++) {
			ParseTree element = ctx.getChild(i);
			if (element instanceof RuleContext) {
				explore((RuleContext) element, verbose, indentation + (toBeIgnored ? 0 : 1), map);
			}
		}
		return map;
	}
}