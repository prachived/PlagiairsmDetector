package com.plagiarism.detector.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;


/**
 * Class that provides object for reading file to a string.
 * 
 * @author ashuk
 *
 */
public class FileUtility {

	private static final Logger LOGGER = LogManager.getLogger();
	/**
	 * AWS S3 file access parameters
	 */
	@Value("${amazonProperties.bucketName}")
	private static String bucketName = "team101";
	@Value("${amazonProperties.accessKey}")
	private static String accessKey = "AKIAJIYHLSJDAQY43SNA";
	@Value("${amazonProperties.secretKey}")
	private static String secretKey = "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";
	static AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
	static AmazonS3 s3client = AmazonS3Client.builder().withRegion("us-east-2")
			.withCredentials(new AWSStaticCredentialsProvider(credentials)).build();

	/**
	 * 
	 * @param filename
	 *            represents the path of the files that needs to be checked for
	 *            plagiarism.
	 * 
	 * @return a string that is the representation of complete code for the given
	 *         file name.
	 * 
	 * @throws IOException
	 * 
	 * @author ashuk
	 */

	public String readFile(String filename) throws IOException {
		File file = new File(filename);
		byte[] encoded = Files.readAllBytes(file.toPath());
		return new String(encoded, Charset.forName("UTF-8"));
	}

	/**
	 * write to a file
	 * 
	 * @param filename
	 * @param content
	 * @throws IOException
	 */
	public void writeFile(String filename, String content) throws IOException {

		File convFile = new File(filename);
		convFile.getParentFile().mkdirs();
		try (OutputStream fos = new FileOutputStream(convFile)) {
			byte[] contentInBytes = content.getBytes();
			fos.write(contentInBytes);
		}
	}

	/**
	 * returns the string corresponding to the student code file.
	 * 
	 * @param folder
	 * @param key,
	 *            S3 folder key
	 * @return string corresponding to code
	 */
	public String readFromS3(String folder, String key) {
		StringBuilder sb = new StringBuilder();

		S3Object s3object = s3client.getObject(bucketName, folder + key);
		InputStream is = s3object.getObjectContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		String line;
		try {
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
		} catch (IOException e) {
			LOGGER.info(e.toString());
		}
		return sb.toString();
	}

	/**
	 * Get all files inside folders
	 * 
	 * @param bucketName
	 * @param prefix
	 * @return filenames
	 */
	public List<String> listKeysInDirectory(String bucketName, String directory) {
		String delimiter = "/";
		if (!directory.endsWith(delimiter)) {
			directory += delimiter;
		}

		ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(bucketName)
				.withPrefix(directory).withDelimiter(delimiter);
		ObjectListing objects = s3client.listObjects(listObjectsRequest);
		return objects.getCommonPrefixes();
	}

	/**
	 * Get all folders uploaded
	 * 
	 * @param bucketName
	 * @param folderKey
	 * @return names of folders
	 */
	public List<String> getObjectslistFromFolder(String bucketName, String folderKey) {

		ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(bucketName)
				.withPrefix(folderKey + "/");

		List<String> keys = new ArrayList<>();

		ObjectListing objects = s3client.listObjects(listObjectsRequest);
		for (;;) {
			List<S3ObjectSummary> summaries = objects.getObjectSummaries();
			if (summaries.isEmpty()) {
				break;
			}
			summaries.forEach(s -> keys.add(s.getKey()));
			objects = s3client.listNextBatchOfObjects(objects);
		}

		return keys;
	}

	/**
	 * delete already existing files
	 * 
	 * @throws IOException
	 */
	public void deleteEC2File() throws IOException {
		File filepath1 = new File("results");
		FileUtils.deleteDirectory(filepath1);
	}
}