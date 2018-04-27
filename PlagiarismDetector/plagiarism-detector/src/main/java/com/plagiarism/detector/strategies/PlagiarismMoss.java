package com.plagiarism.detector.strategies;
import java.io.File;
import java.util.Collection;
import java.util.logging.Logger;
import java.net.URL;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import it.zielke.moji.SocketClient;

//Based on Nordic Way Moji

public class PlagiarismMoss {
	
	final String RESULT = "Results available at: ";
	
	public void runMossRequest() throws Exception {
		// a list of students' source code files located in the prepared
		// directory.
		String currpath = Paths.get("").toAbsolutePath().toString();
		Collection<File> files = FileUtils.listFiles(new File(
			currpath + "//Sample_Submissions"), new String[] { "py" }, true);
		
		//get a new socket client to communicate with the Moss server
		//and set its parameters.
		SocketClient socketClient = new SocketClient();
		
		//set your Moss user ID
		socketClient.setUserID("363865293");
		//socketClient.setOpt...
		
		//set the programming language of all student source codes
		socketClient.setLanguage("python");
		
		//initialize connection and send parameters
		socketClient.run();

		
		//upload all source files of students
		for (File f : files) {
			socketClient.uploadFile(f);
		}
		
		//finished uploading, tell server to check files
		socketClient.sendQuery();
		
		//get URL with Moss results and do something with it
		URL results = socketClient.getResultURL();
		Logger logger = Logger.getLogger("URL returned by Moss for the results");
		String resultUrl = RESULT + results.toString();
		logger.info(resultUrl);
	}
}
