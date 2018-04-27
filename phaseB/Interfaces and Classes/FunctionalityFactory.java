/***
 * This is a factory that would be used to create objects of the
 * various classes that extend the class SystemFunctinalities.
 */

public class FunctionalityFactory{
	
	/***
	 * Create an object of FileUpload class.
	 */
	public FileUpload fetchFileUpload()
	{
		return new FileUpload();
	}

	/***
	 * Create an object of RetrieveResult class.
	 */
	public RetrieveResult fetchResult()
	{
		return new RetrieveResult();
	}
	
	
	/***
	 * Create an object of PlagiarismRun class.
	 */
	public PlagiarismRun fetchPlagiarismRun()
	{
		return new PlagiarismRun();
	}
	
}