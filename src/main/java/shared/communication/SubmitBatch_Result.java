/**
 * 
 */
package main.java.shared.communication;

/**
 * @author tchambs
 *
 */
public class SubmitBatch_Result {

	private boolean result;

	/**
	 * 
	 * @param result true or false if a batch was submitted successfully or failed
	 */
	public SubmitBatch_Result(boolean result) {
		setResult(result);
	}
	
	/**
	 * Empty Constructor
	 * @return
	 */
	public SubmitBatch_Result() {
	}
	
	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String toString() {
		return "True\n";
	}
}
