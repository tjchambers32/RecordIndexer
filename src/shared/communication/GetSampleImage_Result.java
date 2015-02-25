/**
 * 
 */
package shared.communication;

/**
 * @author tchambs
 *
 */
public class GetSampleImage_Result {
	private String imageURL;

	/**
	 * 
	 * @param imageURL the URL of the sample image
	 */
	GetSampleImage_Result(String imageURL) {
		setImageURL(imageURL);
	}
	
	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	
	
}
