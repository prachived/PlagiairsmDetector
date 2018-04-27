package com.plagiarism.detector.strategies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import com.plagiarism.detector.model.Result;
import com.plagiarism.detector.model.StudentAssignment;
import com.plagiarism.detector.utilities.FileUtility;

/**
 * @author Preyank
 *
 *         Abstract class to define the comparison strategy to check plagiarism
 *         of files.
 */
@EnableAutoConfiguration
public abstract class ComparisonStrategy {
	private static final Logger LOGGER = LogManager.getLogger();

	@Value("${amazonProperties.bucketName}")
	private String bucketName = "team101";
	@Value("${amazonProperties.accessKey}")
	private String accessKey = "AKIAJIYHLSJDAQY43SNA";
	@Value("${amazonProperties.secretKey}")
	private String secretKey = "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";
	AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
	AmazonS3 s3client = AmazonS3Client.builder().withRegion("us-east-2")
			.withCredentials(new AWSStaticCredentialsProvider(credentials)).build();

	/**
	 * List stored all the results of the file comparison
	 */
	protected List<StudentAssignment> studentAssignmentList = new ArrayList<>();

	/**
	 * Get student files from uploaded files of students
	 */
	protected void createStudentAssignments() {
		FileUtility fileutility = new FileUtility();
		// Fetch all folders uploaded to S3 bucket.
		List<String> keys = fileutility.listKeysInDirectory(bucketName, "uploads");

		for (String key : keys) {
			StudentAssignment studentAssignment = new StudentAssignment();
			String studentname = key.substring(key.indexOf('/') + 1, key.length() - 1);
			studentAssignment.setName(studentname);
			// Fetch files from uploads folder of S3 bucket
			List<String> files = fileutility.getObjectslistFromFolder(bucketName, key.substring(0, key.length() - 1));
			for (String file : files) {
				if (file.endsWith(".py"))
					studentAssignment.getStudentFile().add(file.substring(file.indexOf('/'), file.length()));

			}
			// Add all the user uploaded assignments to be checked for plagiairsm to
			// studentAssignmentList
			this.studentAssignmentList.add(studentAssignment);

		}

	}

	/**
	 * @return List of Result containing name of files compared and their similarity
	 *         status
	 */
	public List<Result> getSimilarity() {
		createStudentAssignments();
		List<Result> results = new ArrayList<>();
		for (int i = 0; i < studentAssignmentList.size(); i++) {
			for (int j = i + 1; j < studentAssignmentList.size(); j++) {
				StudentAssignment studentAss = studentAssignmentList.get(i);
				StudentAssignment studentAss2 = studentAssignmentList.get(j);
				if (!studentAss.getName().equals(studentAss2.getName())) {
					List<String> student1FileNames = studentAss.getStudentFile();
					List<String> student2FileNames = studentAss2.getStudentFile();
					try {
						List<Result> temp;
						switch (this.getClass().getName()) {
						case "com.plagiarism.detector.strategies.VariableCheck":
							VariableCheck variableCheck = new VariableCheck();
							temp = variableCheck.compareFilesForVariablePlagiarism(student1FileNames,
									student2FileNames);
							results.addAll(temp);
							break;
						case "com.plagiarism.detector.strategies.CommentsCheck":
							CommentsCheck commentsCheck = new CommentsCheck();
							temp = commentsCheck.compareFilesForCommentsPlagiarism(student1FileNames,
									student2FileNames);
							results.addAll(temp);
							break;

						case "com.plagiarism.detector.strategies.ModularityCheck":
							ModularityCheck modularityCheck = new ModularityCheck();
							temp = modularityCheck.compareFilesForModularity(student1FileNames, student2FileNames);
							results.addAll(temp);
							break;

						case "com.plagiarism.detector.strategies.CodeMoveOverCheck":
							CodeMoveOverCheck codeMoveOverCheck = new CodeMoveOverCheck();
							temp = codeMoveOverCheck.compareFilesForCodeMoveOverPlagiarism(student1FileNames,
									student2FileNames);
							results.addAll(temp);
							break;

						default:
							LOGGER.info("No Strategy selected");

						}

					} catch (IOException e) {
						LOGGER.info("Error in Strategy selection");
					}

				}

			}
		}
		return results;

	}

}
