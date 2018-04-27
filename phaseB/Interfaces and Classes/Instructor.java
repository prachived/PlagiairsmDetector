/***
 * An object of the Admin class represents an Instructor, i.e
 * a professor, or a TA. An instructor can perform varios tasks
 * like uploadFile, runCheck, viewResults, viewTop10, downloadResults,
 * and viewDiff.
 */

import java.util.Map;
public class Instructor extends User {
	
	StudentAssignment<Object> studentAssignments[];
	Result[] results;
	boolean runCompleted=false;
    int filesCompeleted;
	
	protected Instructor(String string, String string2) {
	}
	
	/***
	 * Given: a filePath
	 * Effect: populates the studentAssignments array of
	 * this Instructor, with the files present on the
	 * specified path.
	 */
	public void uploadFile(String filePath)
	{
		FileUpload file = factory.fetchFileUpload();
		this.studentAssignments=file.uploadFiles(filePath);
	}

	/***
	 * Effect: populates the results array of this Instructor,
	 * after running the plagiarism check algorithm.
	 */
	public void runCheck()
	{
		PlagiarismRun file = factory.fetchPlagiarismRun();
		this.results=file.runCheck(this.studentAssignments,this);
		runCompleted=true;
	}
	
	
	/***
	 * Effect: view the results of running the plagiarism check
	 * algorithm.
	 */
	public void viewResults()
	{
		RetrieveResult result = factory.fetchResult();
		result.viewResults(this.results);
	}
	
	
	/***
	 * Effect: view the top 10 plagiarised homeworks.
	 */
	public void viewTop10()
	{
		RetrieveResult result = factory.fetchResult();
		result.viewTopTen(this.results);
	}
	
	
	/***
	 * Effect: download the results of the plagiarism check
	 */
	public void downloadResults()
	{
		RetrieveResult result = factory.fetchResult();
		result.downloadResults(this.results);
	}
	
	/***
	 * Effect: Show the differences between two files
	 */
	public void viewDiff()
	{
		RetrieveResult result = factory.fetchResult();
		result.viewDiff(results[0],results[0]);
	}
	
	
}
