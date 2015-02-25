/**
 * 
 */
package shared.communication;

/**
 * @author tchambs
 *
 */
public class SubmitBatch_Result {

	private boolean result;

	/**
	 * 
	 * @param result true or false if whether a batch was submitted successfully or failed
	 */
	SubmitBatch_Result(boolean result) {
		setResult(result);
	}
	
	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
	
}
