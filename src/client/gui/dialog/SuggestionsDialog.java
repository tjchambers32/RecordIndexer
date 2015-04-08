package client.gui.dialog;

import javax.swing.JDialog;

import client.gui.batchstate.BatchState;

@SuppressWarnings("serial")
public class SuggestionsDialog extends JDialog{

	BatchState batchState;
	
	public SuggestionsDialog(BatchState batchState) {
		super();
		
		this.setTitle("Suggestions");
		this.setModal(true);
		this.setResizable(false);
		
		this.batchState = batchState;
	}

}
