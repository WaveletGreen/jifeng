package com.connor.jifeng.plm.jfom009;

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

import com.teamcenter.rac.aif.AbstractAIFDialog;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponentBOMLine;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;
import com.teamcenter.rac.util.PropertyLayout;

public class JFomBomExportDialog extends AbstractAIFDialog {
	private AbstractAIFUIApplication app;
	private TCSession session;
	private JPanel rootPanel;
	private JPanel panel1;
	private JPanel panel2;
	private JButton okButton;
	private JButton celButton;
	private JButton fileButton;
	private JTextField pathText;
	private JFileChooser jfc;
	private JTextField projCode;
	private JTextField projName;

	public JFomBomExportDialog(AbstractAIFUIApplication app, TCSession session) {
		super(true);
		this.app = app;
		this.session = session;
		// TODO 根据需要添加逻辑

		init();
	}

	public void init() {
		this.setTitle("导出物料BOM");
		this.setPreferredSize(new Dimension(450, 180));
		jfc = new JFileChooser();
		jfc.setCurrentDirectory(new File("c:\\"));// 文件选择器的初始目录定为d盘
		okButton = new JButton("确定");
		celButton = new JButton("取消");
		fileButton = new JButton("...");

		fileButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectFileButtonEvent(e);
			}
		});
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				okEvent(e);
			}
		});
		celButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				celEvent(e);
			}
		});

		pathText = new JTextField(30);
		this.projCode = new JTextField(30);

		pathText.setText("c:\\");

		panel1 = new JPanel(new PropertyLayout());

		panel1.add("1.1.right.top.preferred.preferred", new JLabel("         "));

		panel1.add("2.1.right.top.preferred.preferred", new JLabel("项目ID:"));
		panel1.add("2.2.left.top.preferred.preferred", projCode);
		panel1.add("3.1.right.top.preferred.preferred", new JLabel("选择文件夹:"));
		panel1.add("3.2.left.top.preferred.preferred", pathText);
		panel1.add("3.3.left.top.preferred.preferred", fileButton);
		// panel1.add("3.1.center.top.preferred.preferred", okButton);
		// panel1.add("3.2.center.top.preferred.preferred", celButton);

		panel2 = new JPanel(new FlowLayout());
		panel2.add(okButton);
		panel2.add(new JLabel("     "));
		panel2.add(celButton);

		rootPanel = new JPanel(new BorderLayout());
		rootPanel.add(panel1, BorderLayout.CENTER);
		rootPanel.add(panel2, BorderLayout.SOUTH);

		this.add(rootPanel, "Center");
		this.pack();
		this.centerToScreen();
		this.showDialog();

	}

	/**
	 * 文件夹选择按钮事件
	 * 
	 * @param e
	 */
	public void selectFileButtonEvent(ActionEvent e) {
		jfc.setFileSelectionMode(1);// 设定只能选择到文件夹
		int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
		if (state == 1) {
			return;
		} else {
			File f = jfc.getSelectedFile();// f为选择到的目录
			pathText.setText(f.getAbsolutePath());
		}
	}

	/**
	 * 添加导出逻辑
	 * 
	 * @param e
	 */
	public void okEvent(ActionEvent e) {
		InterfaceAIFComponent comp = this.app.getTargetComponent();
		if (comp instanceof TCComponentBOMLine) {
			TCComponentBOMLine bomline = (TCComponentBOMLine) comp;
			try {
				TCComponentBOMLine parent = bomline.parent();
				if (parent != null) {
					MessageBox.post("请选择最顶层的BOMLILNE后再执行！", "Error",
							MessageBox.ERROR);
					return;
				}

				try {
					File tempFile = new File(pathText.getText());
					if (tempFile.exists() && tempFile.isDirectory()) {
						JFomBomExportOperation operetion = new JFomBomExportOperation(
								bomline, pathText.getText(),
								this.projCode.getText(), this.session);
						operetion.executeOperation();
						this.dispose();
					} else {
						MessageBox.post("请选择导出目录", "Error", MessageBox.ERROR);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					MessageBox.post("导出物料BOM失败", "Error", MessageBox.ERROR);
				}

			} catch (TCException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	/**
	 * 添加取消逻辑
	 * 
	 * @param e
	 */
	public void celEvent(ActionEvent e) {
		this.dispose();
	}

}
