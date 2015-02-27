/**
 * 
 */
package shared;

import shared.model.Model;
import shared.model.ModelException;

/**
 * @author tchambs
 *
 */
public class DataImporter {

	private Model model = new Model();
	
	public static void main(String[] args) throws Exception {
		
		if (args.length != 1) {
			System.out.println("USAGE: DataImporter [filepath]");
		}
		
		DataImporter di = new DataImporter();
		
		di.importData(args[0]);
		
	}

	/**
	 * @param string
	 * @throws ModelException 
	 */
	private void importData(String string) throws Exception {
		
		Model.initialize();
		
		Model.clear();
	}
	
}
