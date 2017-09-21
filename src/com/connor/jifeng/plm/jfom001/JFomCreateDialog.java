package com.connor.jifeng.plm.jfom001;

import java.awt.Frame;

import com.teamcenter.rac.commands.newitem.NewItemDialog;
import com.teamcenter.rac.kernel.TCSession;

public class JFomCreateDialog extends NewItemDialog{

	protected JFomCreateDialog(Frame frame, TCSession tcsession, boolean flag) {
		super(frame, tcsession, flag);
		this.objectType="Item";
		// TODO Auto-generated constructor stub
	}
	

}
