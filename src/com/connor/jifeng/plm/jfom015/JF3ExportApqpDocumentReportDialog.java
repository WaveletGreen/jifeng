package com.connor.jifeng.plm.jfom015;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.AbstractAIFDialog;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponentProject;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;
import com.teamcenter.rac.util.PropertyLayout;

public class JF3ExportApqpDocumentReportDialog extends AbstractAIFDialog
		implements ActionListener {

	private AbstractAIFApplication app;
	private TCSession session;

	private JTextField nameField;
	private JTextField pathField;
	private JButton okButton;
	private JButton celButton;
	private JButton fileButton;
	private JFileChooser jfc;

	public JF3ExportApqpDocumentReportDialog(AbstractAIFApplication app) {
		super(true);
		this.app = app;
		this.session = (TCSession) app.getSession();

	}

	public void init() {
		this.setTitle("导出APQP资料清单");
		this.setPreferredSize(new Dimension(450, 180));
		jfc = new JFileChooser();
		jfc.setCurrentDirectory(new File("c:\\"));// 文件选择器的初始目录定为d盘
		okButton = new JButton("确定");
		celButton = new JButton("取消");
		fileButton = new JButton("...");
		okButton.addActionListener(this);
		celButton.addActionListener(this);
		fileButton.addActionListener(this);
		pathField = new JTextField(30);
		nameField = new JTextField(30);
		nameField.setText("APQP资料清单点检表");
		pathField.setText("c:\\");
		JPanel panel1 = null;
		panel1 = new JPanel(new PropertyLayout());
		panel1.add("1.1.right.top.preferred.preferred", new JLabel("         "));
		panel1.add("2.1.right.top.preferred.preferred", new JLabel("文件名称:"));
		panel1.add("2.2.left.top.preferred.preferred", nameField);
		panel1.add("3.1.right.top.preferred.preferred", new JLabel("选择文件夹:"));
		panel1.add("3.2.left.top.preferred.preferred", pathField);
		panel1.add("3.3.left.top.preferred.preferred", fileButton);

		JPanel panel2 = null;
		panel2 = new JPanel(new FlowLayout());
		panel2.add(okButton);
		panel2.add(new JLabel("     "));
		panel2.add(celButton);

		JPanel rootPanel = null;
		rootPanel = new JPanel(new BorderLayout());
		rootPanel.add(panel1, BorderLayout.CENTER);
		rootPanel.add(panel2, BorderLayout.SOUTH);

		this.add(rootPanel, "Center");
		this.pack();
		this.centerToScreen();
		this.showDialog();

	}

	@Override
	public void run() {
		init();
		super.run();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(this.okButton)) {
			InterfaceAIFComponent comp = this.app.getTargetComponent();
			if (comp instanceof TCComponentProject) {

			} else {
				this.dispose();
				MessageBox.post("请选择项目后再执行此操作", "警告", MessageBox.WARNING);

			}

		} else if (e.getSource().equals(this.celButton)) {
			this.dispose();
		} else if (e.getSource().equals(this.jfc)) {
			jfc.setFileSelectionMode(1);// 设定只能选择到文件夹
			int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
			if (state == 1) {
				return;
			} else {
				File f = jfc.getSelectedFile();// f为选择到的目录
				pathField.setText(f.getAbsolutePath());
			}

		}

	}

}
