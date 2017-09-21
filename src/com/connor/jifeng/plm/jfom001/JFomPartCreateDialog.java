package com.connor.jifeng.plm.jfom001;

import java.awt.Frame;

import com.teamcenter.rac.commands.newpart.NewPartDialog;
import com.teamcenter.rac.util.MessageBox;

public class JFomPartCreateDialog extends NewPartDialog {
	  public JFomPartCreateDialog(Frame frame, boolean flag)
	    {
	        super(frame, flag);
//	        MessageBox.post("info","info",MessageBox.INFORMATION);
	    }

	    public JFomPartCreateDialog(JFomPartCreateCommond newpartcommand)
	    {
	        super(newpartcommand);
//	        MessageBox.post("info","info",MessageBox.INFORMATION);
	    }

}
