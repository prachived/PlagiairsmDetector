package com.plagiarism.detector.strategies;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.TokenSource;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import com.plagiarism.detector.model.Outcome;
import com.plagiarism.detector.model.Result;
import com.plagiarism.detector.techniques.LevenshteinDistance;

import grammar.Python3BaseListener;
import grammar.Python3Lexer;
import grammar.Python3Parser;

/**
 * Object that provides operation for detecting plagiarism through code
 * movement.
 * 
 * @author ashuk
 *
 */
public class PlagiarismCodeMoveOver {

	static final Integer PERCENT_THRESHOLD = 80;
	LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

	/**
	 * returns the outcome of the plagiarism test for code movement in twow files
	 * 
	 * @param file1
	 *            Filename of file 1
	 * @param file2
	 *            Filename of file 2
	 * @return Outcome of code movement's result.
	 * @throws IOException
	 */
	public Result getCodeMoveOverPlagiarismResult(String file1, String file2) throws IOException {
		List<List<String>> file1MethodsAST;
		List<List<String>> file2MethodsAST;

		List<Integer> file1LineNum = new ArrayList<>();
		List<Integer> file2LineNum = new ArrayList<>();
		int plagCount = 0;
		double percentage;
		ANTLRInputStream inputCharStream = new ANTLRInputStream(new StringReader(file1));
		TokenSource lexer = new Python3Lexer(inputCharStream);
		Python3Parser parser = new Python3Parser(new CommonTokenStream(lexer));
		ANTLRInputStream inputCharStream2 = new ANTLRInputStream(new StringReader(file2));
		TokenSource lexer2 = new Python3Lexer(inputCharStream2);
		Python3Parser parser2 = new Python3Parser(new CommonTokenStream(lexer2));

		// generate list of AST methods.
		file1MethodsAST = generateASTOfMethods(parser);
		file2MethodsAST = generateASTOfMethods(parser2);

		for (List<String> s1 : file1MethodsAST) {
			for (List<String> s2 : file2MethodsAST) {
				if (levenshteinDistance.getPlagiarismPercentage(s1.get(0), s2.get(0)).equals(Outcome.PLAGIARIZED)) {
					addLineNumbers(file1LineNum, s1.get(1), s1.get(2));
					addLineNumbers(file2LineNum, s2.get(1), s2.get(2));
					plagCount++;
				}
			}

		}

		// calculate percent.
		percentage = (double) plagCount * 100 / file1MethodsAST.size();

		// determining plagiarism
		if (percentage >= PERCENT_THRESHOLD) {
			Result res = new Result(file1LineNum, file2LineNum, Outcome.PLAGIARIZED);
			res.setSummary("Files are Plagiarised");
			return res;
		} else {
			Result res = new Result(file1LineNum, file2LineNum, Outcome.NOTPLAGIARIZED);
			res.setSummary("Files are Not Plagiarised");
			return res;
		}
	}

	
	/***
	 * add plagiarized line numbers to the list fileLineNum
	 * @param fileLineNum
	 * @param n1
	 * @param n2
	 */
	private void addLineNumbers(List<Integer> fileLineNum, String n1, String n2) {

		for (int i = Integer.parseInt(n1); i <= Integer.parseInt(n2); i++)
			fileLineNum.add(i);
	}

	
	/**
	 * returns a list that has strings of AST of methods in code file.
	 * 
	 * @param parser
	 *            Object of parser of given file.
	 * @return list of ast methods
	 */
	private static List<List<String>> generateASTOfMethods(Python3Parser parser) {
		List<List<String>> methodList = new ArrayList<>();

		ParseTreeWalker.DEFAULT.walk(new Python3BaseListener() {
			@Override
			public void enterFuncdef(Python3Parser.FuncdefContext ctx) {
				List<String> outcome = new ArrayList<>();
				PlagiarismCodeMoveOver modularity = new PlagiarismCodeMoveOver();
				StringBuilder astString = modularity.explore(ctx, false, 0, new StringBuilder(""));
				outcome.add(astString.toString());
				outcome.add(Integer.toString(ctx.getStart().getLine()));
				outcome.add(Integer.toString(ctx.getStop().getLine()));
				methodList.add(outcome);
			}
		}, parser.file_input());
		return methodList;
	}

	/**
	 * to determine the rule of given context and append to string.
	 * 
	 * @param ctx
	 *            context of the rule
	 * @param verbose
	 * @param indentation
	 *            for printing AST
	 * @param methodString
	 *            to append the rule name to string.
	 * @return String for the given method.
	 */
	private StringBuilder explore(RuleContext ctx, boolean verbose, int indentation, StringBuilder methodString) {
		boolean toBeIgnored = !verbose && ctx.getChildCount() == 1 && ctx.getChild(0) instanceof ParserRuleContext;
		if (!toBeIgnored) {
			String ruleName = Python3Parser.ruleNames[ctx.getRuleIndex()];

			if (indentation != 0) {
				methodString.append(ruleName + " ");
			}

		}
		for (int i = 0; i < ctx.getChildCount(); i++) {
			ParseTree element = ctx.getChild(i);
			if (element instanceof RuleContext) {
				explore((RuleContext) element, verbose, indentation + (toBeIgnored ? 0 : 1), methodString);
			}
		}
		return methodString;
	}
}