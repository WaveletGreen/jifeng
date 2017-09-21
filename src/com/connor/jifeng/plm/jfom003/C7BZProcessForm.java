package com.connor.jifeng.plm.jfom003;

import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.stylesheet.AbstractRendering;
import com.teamcenter.rac.util.ButtonLayout;
import com.teamcenter.rac.util.MessageBox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class C7BZProcessForm extends AbstractRendering implements ActionListener{
	

	public C7BZProcessForm(TCComponent arg0) throws Exception {
		super(arg0);
		loadRendering();
	}
	//		public C7BZProcessForm() {
//		super(arg0);
//		// TODO Auto-generated constructor stub
//		init();
//	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel lable_1 ;
	private JLabel lable_2;
	private JLabel lable_3;
	private JLabel lable_4;
	private JButton button_1;
	private JButton button_ok;
	private JButton button_cel;
	private JPanel root_panel;
	private JTextField text_name ;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JTextField text_path;
	//����bomline
	//private TCComponentBOMLine line;
	/**
	 * ���췽��
	 * @param line ����BOMline
	 */
//	public C7BZProcessForm(){
//	
//		//��ʼ������
//		init();
//	}
	/**
	 * ��ʼ������
	 */
	private void init(){
		//this.setTitle("��������");
		//this.setPreferredSize(new Dimension(350, 180));
		
		setLayout(new BorderLayout());
	    setBackground(Color.WHITE);
	
		lable_1 = new JLabel("���ƣ�");
		lable_2 = new JLabel("·����");
		lable_3 = new JLabel(".xlsx");
		button_1 = new JButton("...");
		button_ok = new JButton("ȷ��");
		button_cel = new JButton("ȡ��");
	//·��ѡ��ť����
	button_1.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			btn_path_click(e);
		}
	 }
	);
	//����ȷ����ť����
	button_ok.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			String name = text_name.getText();
			String path = text_path.getText();
			if(name != null && !name.trim().equals("")&&path != null && !path.trim().equals("")){
				
				close_window();
			}else{
				MessageBox.post("�ļ����Ʋ��ԣ������ļ�·�����ԣ�","��ʾ",MessageBox.INFORMATION);
				
				
			}
		}
	 }
	);
	//����ȡ����ť
	button_cel.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			close_window();
		}
	 }
	);
	
	
	text_name = new JTextField(15);
	text_name.setText("DBOM����");
	text_path = new JTextField(20);
	text_path.enable(false);
	root_panel = new JPanel(new ButtonLayout(2, 1, 2));
	panel_1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	panel_1.add(lable_2);
	panel_1.add(text_path);
	panel_1.add(button_1);
	
	panel_2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	panel_2.add(lable_1);
	panel_2.add(text_name);
	panel_2.add(lable_3);
	
	lable_4 = new JLabel("            ");
	panel_3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	panel_3.add(button_ok);
	panel_3.add(lable_4);
	panel_3.add(button_cel);
	
	root_panel.add(panel_2);
	root_panel.add(panel_1);
	root_panel.add(panel_3);
	
	this.add(root_panel,"Center");
	//this.setAlwaysOnTop(true);
	//this.pack();
	//this.centerToScreen();
	//this.showDialog();
	
	}
	/**
	 * �رս���
	 */
	private void close_window(){
		//this;	
	}
	/**
	 * ·��ѡ�����
	 * @param e
	 */
	private void btn_path_click(ActionEvent e)
	{
		Preferences pref = Preferences.userRoot().node(this.getClass().getName()); 
		String lastPath = pref.get("lastPath", "");
		JFileChooser chooser = null; 
        if(!lastPath.equals("")){ 
        	chooser = new JFileChooser(lastPath); 
        } 
        else{
        	 chooser=new JFileChooser();
        }
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);	
        chooser.setMultiSelectionEnabled(false);
        chooser.setDialogTitle("ѡ��·��");	
        chooser.showDialog(this, "ȷ��");
        if(chooser.getSelectedFile()!=null) 
		{			
			String choose_path = chooser.getSelectedFile().getPath();	
			text_path.setText(choose_path);
			pref.put("lastPath",choose_path);
		}
        
	}
	
	
	public static void main(String[] args) {
		//Test test = new Test();
	}
	
	@Override
	public void loadRendering() throws TCException {
		// TODO Auto-generated method stub
		init();
	}
	@Override
	public void saveRendering() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


}