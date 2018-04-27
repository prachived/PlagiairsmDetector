package com.plagiarism.detector.strategies;

/**
 * 
 * Factory class to obtain objects
 * of corresponding comparison startegy's
 * class.
 * 
 * @author ashuk
 *
 */
public class FunctionalityFactory {

	private static FunctionalityFactory factoryInstance = null;

	/**
	 * Constructor for NodeFactorySingleton class.
	 */
	private FunctionalityFactory() {
	}

	/**
	 * If more than one instance of NodeFactorySingleton class needs to be created,
	 * it doesn't allow and returns the first instance that was created.
	 * 
	 * @return NodeFactorySingleton instance
	 */
	public static FunctionalityFactory instance() {
		if (factoryInstance == null) {
			factoryInstance = new FunctionalityFactory();
		}
		return factoryInstance;
	}

	
	/**
	 * 
	 * @return object of VariableCheck
	 */
	public VariableCheck makeVariableCheckInstance() {
		return new VariableCheck();
	}

	/**
	 * 
	 * @return object of CodeMoveOverCheck.
	 */
	public CodeMoveOverCheck makeCodeMoveOverCheckInstance() {
		return new CodeMoveOverCheck();
	}

	/**
	 * 
	 * @return object of ModularityCheck.
	 */
	public ModularityCheck makeModularityCheckInstance() {
		return new ModularityCheck();
	}
	
	/**
	 * 
	 * @return object of CommentsCheck.
	 */
	public CommentsCheck makeCommentsCheckInstance() {
		return new CommentsCheck();
	}
	
}
