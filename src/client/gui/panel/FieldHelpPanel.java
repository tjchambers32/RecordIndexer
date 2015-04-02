package client.gui.panel;

import javax.swing.JPanel;

import client.gui.batchstate.*;

@SuppressWarnings("serial")
public class FieldHelpPanel extends JPanel implements BatchStateListener{

	public FieldHelpPanel() {
		super();
		
		createComponents();
	}

	private void createComponents() {
		
	}

	/* (non-Javadoc)
	 * @see client.gui.batchstate.BatchStateListener#valueChanged(client.gui.batchstate.Cell, java.lang.String)
	 */
	@Override
	public void valueChanged(Cell cell, String newValue) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see client.gui.batchstate.BatchStateListener#selectedCellChanged(client.gui.batchstate.Cell)
	 */
	@Override
	public void selectedCellChanged(Cell newSelectedCell) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see client.gui.batchstate.BatchStateListener#invertImageChanged()
	 */
	@Override
	public void invertImageChanged() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see client.gui.batchstate.BatchStateListener#highlightsVisibleChanged()
	 */
	@Override
	public void highlightsVisibleChanged() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see client.gui.batchstate.BatchStateListener#zoomChanged()
	 */
	@Override
	public void zoomChanged() {
		// TODO Auto-generated method stub
		
	}
}
