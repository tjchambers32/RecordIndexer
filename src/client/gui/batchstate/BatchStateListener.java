/**
 * 
 */
package client.gui.batchstate;

/**
 * @author tchambs
 *
 */
public interface BatchStateListener {
	
		public void valueChanged(Cell cell, String newValue);
		
		public void selectedCellChanged(Cell newSelectedCell);
		
		public void invertImageChanged();
		
		public void highlightsVisibleChanged();
		
		public void zoomChanged();
}
