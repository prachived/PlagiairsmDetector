package com.plagiarism.detector.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * @author Prachi amazon client service
 */
@Service
public class AmazonClient {
	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	
	/**
	 * logger for the class
	 */
	private static final Logger LOGGER = LogManager.getLogger();
	
	/**
	 * constant string to represent failed upload
	 */
	private static final String UPLOAD_FILE_ERROR = "upload file failed";

	
	/**
	 * amazon client object to read uploaded files from S3
	 */
	private AmazonS3 s3client;

	
	/**
	 * end point URl
	 */
	@Value("${amazonProperties.endpointUrl}")
	private String endpointUrl;
	
	/**
	 * bucket name
	 */
	@Value("${amazonProperties.bucketName}")
	private String bucketName = "team101";
	
	/**
	 * access Keys
	 */
	@Value("${amazonProperties.accessKey}")
	private String accessKey = "AKIAJIYHLSJDAQY43SNA";
	
	/**
	 * secret keys
	 */
	@Value("${amazonProperties.secretKey}")
	private String secretKey = "a1s1kKE0TvHW50bB/mlO8hD94gBbN1Skfj/5lEBN";
	
	
	/**
	 * Setup parameters to read files from S3 bucket
	 */
	@PostConstruct
	public void initializeAmazon() {
		BasicAWSCredentials creds = new BasicAWSCredentials(this.accessKey, this.secretKey);
		this.s3client = AmazonS3ClientBuilder.standard().withRegion("us-east-2")
				.withCredentials(new AWSStaticCredentialsProvider(creds)).build();
	}

	
	/**
	 * Convert multipart file to a file object to facilitate storing on S3
	 * 
	 * @param file
	 * @return File that facilitates storing on S3
	 * @throws IOException
	 */
	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.getParentFile().mkdirs();
		try (OutputStream fos = new FileOutputStream(convFile)) {
			fos.write(file.getBytes());
			return convFile;
		}
	}

	
	/**
	 * upload files to bucket
	 * 
	 * @param fileName
	 * @param file
	 */
	private void uploadFileTos3bucket(String fileName, File file) {
		s3client.putObject(
				new PutObjectRequest(bucketName, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
	}

	/**
	 * upload files to bucket individually
	 * 
	 * @param fileName
	 * @param file
	 */
	public void uploadFileTos3bucketIndv(String fileName, File file) {
		s3client.putObject(new PutObjectRequest(bucketName + "/uploads", fileName, file)
				.withCannedAcl(CannedAccessControlList.PublicRead));
	}

	/**
	 * upload files
	 * 
	 * @param multipartFile
	 */
	public void uploadFile(MultipartFile multipartFile) {

		try {
			File file = convertMultiPartToFile(multipartFile);
			String fileName = multipartFile.getOriginalFilename();
			uploadFileTos3bucket(fileName, file);
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, UPLOAD_FILE_ERROR);
		}

	}

	
	/**
	 * @param multipartFile
	 * @return
	 */
	public String uploadFileIndiv(MultipartFile multipartFile) {

		String fileUrl = "";
		try {
			File file = convertMultiPartToFile(multipartFile);
			String fileName = multipartFile.getOriginalFilename();
			uploadFileTos3bucketIndv(fileName, file);
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, UPLOAD_FILE_ERROR);
		}
		return fileUrl;
	}

	
	
	/**
	 * delete already existing files
	 * 
	 * @throws IOException
	 */
	public void deleteObject() throws IOException {
		FileUtility fileutility = new FileUtility();
		List<String> keys = fileutility.listKeysInDirectory(bucketName, "uploads");
		File filepath1 = new File("uploads");
		FileUtils.deleteDirectory(filepath1);
		for (String key : keys)

		{
			List<String> files = fileutility.getObjectslistFromFolder(bucketName, key.substring(0, key.length() - 1));
			for (String file : files) {
				s3client.deleteObject(bucketName, file);
			}

		}

	}

}
