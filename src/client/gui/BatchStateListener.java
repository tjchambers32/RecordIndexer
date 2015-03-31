/**
 * 
 */
package client.gui;

/**
 * @author tchambs
 *
 */
public interface BatchStateListener {
	
		public void valueChanged(Cell cell, String newValue);
		
		public void selectedCellChanged(Cell newSelectedCell);
}
