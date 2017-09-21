package com.connor.jifeng.plm.erp.ui.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.connor.jifeng.plm.util.JFomUtil;
import com.teamcenter.rac.aif.AbstractAIFDialog;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.kernel.TCPreferenceService;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.DateButton;
import com.teamcenter.rac.util.PropertyLayout;

public class JFomErpDialog extends AbstractAIFDialog implements ActionListener {
	protected JButton okBtn;
	protected JButton celBtn;
	protected JPanel rootPanel;
	protected JPanel midPanel;
	protected JPanel btmPanel;
	protected JLabel discardLabel;
	protected DateButton discardText;

	// 应用程序常量
	protected String nameOfDate;
	protected String dialogName;
	protected AbstractAIFUIApplication app;
	protected TCSession session;

	//
	protected String[] cp_name_values;
	protected String[] bcp_name_values;
	protected String[] ycl_name_values;
	protected String[] cp_gg_values;
	protected String[] bcp_gg_values;
	protected String[] erp_java_values;

	public void getErpNameAndGG() {
		TCPreferenceService service = this.session.getPreferenceService();
		cp_name_values = service.getStringArray(
				TCPreferenceService.TC_preference_site,
				"Cust_ERP_PLM_CP_Name_Props");
		cp_gg_values = service.getStringArray(
				TCPreferenceService.TC_preference_site,
				"Cust_ERP_PLM_CP_GG_Props");
		bcp_name_values = service.getStringArray(
				TCPreferenceService.TC_preference_site,
				"Cust_ERP_PLM_BCP_Name_Props");
		bcp_gg_values = service.getStringArray(
				TCPreferenceService.TC_preference_site,
				"Cust_ERP_PLM_BCP_GG_Props");
		ycl_name_values = service.getStringArray(
				TCPreferenceService.TC_preference_site,
				"Cust_ERP_PLM_YCL_NAME_Props");
		erp_java_values = service
				.getStringArray(TCPreferenceService.TC_preference_site,
						"Cust_ERP_PLM_WL_Props");
	}

	public JFomErpDialog(String nameOfDate, String dialogName,
			AbstractAIFUIApplication app, TCSession session) {
		super(false);
		this.dialogName = dialogName;
		this.nameOfDate = nameOfDate;
		this.app = app;
		this.session = session;
		init();
		getErpNameAndGG();
	}

	public void setMidPanel() {
		if (this.nameOfDate == null) {
			this.discardLabel = new JLabel("失效日期");
		} else {
			this.discardLabel = new JLabel(this.nameOfDate);
		}
		SimpleDateFormat sdf = new SimpleDateFormat(JFomUtil.TIME_FORMAT);
		discardText = new DateButton(sdf);
		discardText.setFocusable(false);
		this.midPanel.add("1.1.right.top.preferred.preferred",
				new JLabel("   "));
		this.midPanel.add("2.1.right.top.preferred.preferred", discardLabel);
		this.midPanel.add("2.2.left.top.preferred.preferred", discardText);
	}

	public void init() {
		this.setTitle(dialogName);
		this.setAutoRequestFocus(true);
		this.setPreferredSize(new Dimension(450, 200));
		this.okBtn = new JButton("确认传递");
		this.okBtn.addActionListener(this);
		this.celBtn = new JButton("取消传递");
		this.celBtn.addActionListener(this);
		this.rootPanel = new JPanel(new BorderLayout());
		this.midPanel = new JPanel(new PropertyLayout());
		setMidPanel();
		this.btmPanel = new JPanel(new FlowLayout());
		this.btmPanel.add(okBtn);
		this.btmPanel.add(new JLabel("   "));
		this.btmPanel.add(celBtn);
		rootPanel.add(midPanel, BorderLayout.CENTER);
		rootPanel.add(btmPanel, BorderLayout.SOUTH);
		this.add(rootPanel, "Center");
		this.pack();
		this.centerToScreen();
		this.showDialog();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
