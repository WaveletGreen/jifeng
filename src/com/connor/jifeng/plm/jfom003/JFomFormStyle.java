package com.connor.jifeng.plm.jfom003;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.connor.jifeng.plm.util.JFomUtil;
import com.connor.jifeng.plm.util.SqlUtil;
import com.teamcenter.rac.aif.AbstractAIFDialog;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCProperty;
import com.teamcenter.rac.stylesheet.AbstractRendering;
import com.teamcenter.rac.util.MessageBox;

public class JFomFormStyle extends AbstractRendering implements ActionListener,
		MouseListener, KeyListener {

	// private static final long serialVersionUID = 2L;

	private JPanel rootPanel;
	private JPanel topPanel;
	private JPanel buttomPanel;
	private JScrollPane scrollPanel;
	private JLabel emptyLable;
	private JButton addButton;
	private JButton delButton;
	private JTable partsTable;
	private Object[] titleNames = { "���Ϻ�", "����", "���", "����" };
	private HashMap<Integer, String> clorMap;
	private HashMap<Integer, Integer> fontMap;
	private List<JFomFormBean> valueLists;
	private DefaultTableModel dtm;
	private int recordIndex = 0;
	private TCComponent formComp;
	private TCProperty[] properties;

	public JFomFormStyle(TCComponent arg0) throws Exception {
		super(arg0);
		this.formComp = arg0;
		loadRendering();
	}

	/**
	 * ����FORM��ʱ���ȡ����
	 */
	public void getFormVlue() {

		try {
			// formComp.getProperties(new
			// String[]{"cd9_wl_code","cd9_wl_number"});
			properties = formComp.getTCProperties(new String[] {
					JFomUtil.JF3_WL_CODE_PROP, JFomUtil.JF3_WL_COUNT_PROP,
					JFomUtil.JF3_WL_GG_PROP, JFomUtil.JF3_WL_MC_PROP });
			if (properties == null
					|| properties.length != JFomUtil.JF3_WL_PROPS_COUNT) {
				return;
			}
			String[] wlCodeStrs = properties[0].getStringArrayValue();
			String[] wlCountInts = new String[wlCodeStrs.length];// =
																	// properties[1].getStringArrayValue();
			String[] wlGuiGeStrs = new String[wlCodeStrs.length];// properties[2].getStringArrayValue();
			String[] wlMingChengStrs = new String[wlCodeStrs.length];// properties[3].getStringArrayValue();
			for (int i = 0; i < wlCodeStrs.length; i++) {
				String wlCodeStr = wlCodeStrs[i];
				JFomFormBean bean = new JFomFormBean();
				getSumCount(wlCodeStr, bean);
				if (bean.getWlName() != null)
					wlMingChengStrs[i] = bean.getWlName();
				else
					wlMingChengStrs[i] = "";
				if (bean.getWlCount() != null)
					wlCountInts[i] = bean.getWlCount();
				else
					wlCountInts[i] = "";
				if (bean.getWlGuige() != null)
					wlGuiGeStrs[i] = bean.getWlGuige();
				else
					wlGuiGeStrs[i] = "";

			}
			if (wlCodeStrs == null || wlCountInts == null
					|| wlCodeStrs.length != wlCountInts.length) {
				return;
			}
			for (int i = 0; i < wlCodeStrs.length; i++) {
				valueLists.add(new JFomFormBean(wlCodeStrs[i],
						wlMingChengStrs[i], wlGuiGeStrs[i], wlCountInts[i]));
			}

		} catch (TCException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ��������
	 */
	public void saveWLProps() {
		String[] codeStrs = new String[valueLists.size()];
		String[] countStrs = new String[valueLists.size()];
		String[] ggStrs = new String[valueLists.size()];
		String[] nameStrs = new String[valueLists.size()];

		for (int i = 0; i < valueLists.size(); i++) {
			JFomFormBean bean = valueLists.get(i);
			codeStrs[i] = bean.getWlCode();
			countStrs[i] = bean.getWlCount();
			ggStrs[i] = bean.getWlGuige();
			nameStrs[i] = bean.getWlName();
		}
		try {
			properties[0].setStringValueArray(codeStrs);
			properties[1].setStringValueArray(countStrs);
			properties[2].setStringValueArray(ggStrs);
			properties[3].setStringValueArray(nameStrs);
			formComp.setTCProperties(properties);
		} catch (TCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ����
	 * 
	 * @param wlCode
	 * @return
	 */
	public void getSumCount(String wlCode, JFomFormBean bean) {
		float sumCount = 0.0f;
		// String sqlStr =
		// "select IMG01,IMA02,IMA021,SUM_IMG10,IMG02,IMD02 from  v_stock_plm where IMG01=?";
		String sqlStr = "select IMA02,IMA021 ,SUM_IMG10,IMD02 from v_stock_plm where IMG01=?";
		SqlUtil.getConnection();
		try {
			ResultSet rs = SqlUtil.read(sqlStr, new Object[] { wlCode });
			String wlName = null;
			String wlGG = null;
			List<String> wlCounts = new ArrayList<String>();
			List<String> wlCK = new ArrayList<String>();
			if (rs != null) {
				while (rs.next()) {
					String count = rs.getString(3);
					String ck = rs.getString(4);
					sumCount = sumCount + Float.parseFloat(count);
					wlName = rs.getString(1);
					wlGG = rs.getString(2);
					wlCounts.add(count);
					wlCK.add(ck);
				}
			}
			String countTemp = sumCount + "";
			if (countTemp.endsWith(".0")) {
				countTemp = countTemp.substring(0, countTemp.lastIndexOf(".0"));
			}
			bean.setWlCode(wlCode);
			bean.setWlName(wlName);
			bean.setWlGuige(wlGG);
			bean.setWlCount(countTemp);
			bean.setCountList(wlCounts);
			bean.setCkList(wlCK);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SqlUtil.free();

	}

	/**
	 * ��ȡ���е�
	 * 
	 * @param wlCode
	 * @return
	 */
	public JFomFormBean getCounts(String wlCode) {
		JFomFormBean bean = new JFomFormBean();

		String sql = "select IMA02,IMA021,SUM_IMG10,IMD02 from  v_stock_plm where IMG01=?";

		return bean;
	}

	/**
	 * ��������ת��
	 * 
	 * @param str
	 * @return
	 */
	public int stringToInt(String str) {
		int count = 0;
		try {
			count = Integer.parseInt(str.trim());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * ǩ�벢����
	 */
	@Override
	public boolean checkForSave(Object obj) {
		// TODO Auto-generated method stub
		saveWLProps();
		System.out.println("ǩ�벢����");
		return super.checkForSave(obj);
	}

	/**
	 * ����
	 */
	@Override
	public void save() {
		// TODO Auto-generated method stub
		saveWLProps();
		System.out.println("����");
		super.save();
	}

	public void init() {
		clorMap = new HashMap<Integer, String>();
		fontMap = new HashMap<Integer, Integer>();
		valueLists = new ArrayList<JFomFormBean>();
		getFormVlue();
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
		rootPanel = new JPanel(new BorderLayout());
		addButton = new JButton("���");
		addButton.setEnabled(true);
		delButton = new JButton("ɾ��");
		delButton.setEnabled(true);
		emptyLable = new JLabel("    ");

		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addRow(e);
			}
		});

		delButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delRow(e);
			}
		});
		// TODO ���Table�����
		this.partsTable = getjTable();

		this.topPanel = new JPanel(new BorderLayout());
		scrollPanel = new JScrollPane();
		scrollPanel.getViewport().add(partsTable, null);
		scrollPanel.validate();

		this.topPanel.add(BorderLayout.CENTER, scrollPanel);

		this.buttomPanel = new JPanel(new FlowLayout());
		this.buttomPanel.add(addButton);
		this.buttomPanel.add(emptyLable);
		this.buttomPanel.add(delButton);

		this.rootPanel = new JPanel(new BorderLayout());
		this.rootPanel.add(BorderLayout.CENTER, topPanel);
		this.rootPanel.add(BorderLayout.SOUTH, buttomPanel);

		this.add(rootPanel, "Center");
	}

	private void addRow(ActionEvent e) {
		// JFomFormBean bean = new JFomFormBean();
		// this.recordIndex++;
		// bean.setWlCode("WL" + this.recordIndex);
		// valueLists.add(bean);
		// // partsTable.setModel(getTableModel());
		// Object[] objs = { bean.getWlCode(), bean.getWlName(),
		// bean.getWlGuige(), bean.getWlCount() };
		// dtm.addRow(objs);
		// dtm.fireTableStructureChanged();
		// dtm.fireTableDataChanged();
		// scrollPanel.validate();
		new writeWLCodeDialog();

	}

	private void delRow(ActionEvent e) {
		int index = partsTable.getSelectedRow();
		if (index == -1) {
			MessageBox.post("��ѡ�������ִ��ɾ��������", "Warning", MessageBox.WARNING);
			return;
		}
		valueLists.remove(index);
		dtm.removeRow(index);
		dtm.fireTableStructureChanged();
		dtm.fireTableDataChanged();
		scrollPanel.validate();
	}

	/***************************************************/

	/**
	 * JTABLEͨ�÷���
	 * 
	 * @param partsTable
	 * @param titleNames
	 * @return
	 */
	public JTable getjTable(JTable partsTable, DefaultTableModel dtm,
			Object[] titleNames, Object[][] values) {
		int simpleLen = 105;
		int totleLen = 900;
		if (partsTable == null) {
			partsTable = new JTable(getTableModel(dtm, titleNames, values)) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};

			if (simpleLen * titleNames.length >= totleLen) {
				for (int i = 0; i < titleNames.length; i++) {
					partsTable.getColumnModel().getColumn(i)
							.setPreferredWidth(105);
				}
				partsTable.setAutoResizeMode(0);
				// System.out.println("not auto size");
			} else {
				// System.out.println("auto size");
				partsTable.setAutoResizeMode(1);
			}
			// partsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		}
		return partsTable;
	}

	/**
	 * �����������������������õķ���
	 * 
	 * @return
	 */
	public JTable getjTable() {
		if (partsTable == null) {
			partsTable = new JTable(getTableModel()) {
				public boolean isCellEditable(int row, int column) {
					// if (row == (partsTable.getRowCount() - 1)) {
					// if (column == 0)
					// return true;
					// else
					// return false;
					// } else {
					// return false;
					// }
					return false;
				}
			};
			partsTable.addMouseListener(this);
			partsTable.addKeyListener(this);

			if (105 * titleNames.length >= 900) {
				for (int i = 0; i < titleNames.length; i++) {
					this.partsTable.getColumnModel().getColumn(i)
							.setPreferredWidth(105);
				}
				partsTable.setAutoResizeMode(0);
				System.out.println("not auto size");
			} else {
				System.out.println("auto size");
				partsTable.setAutoResizeMode(1);
			}
			// partsTable.setDefaultRenderer(Object.class, new
			// DefaultTableCellRenderer() {
			// @Override
			// public Component getTableCellRendererComponent(JTable table,
			// Object value, boolean isSelected,
			// boolean hasFocus, int row, int column) {
			//
			// Component comp = super.getTableCellRendererComponent(table,
			// value, isSelected, hasFocus, row,
			// column);
			// // System.out.println(""+comp.getBackground().toString());
			// if (fontMap.containsKey(row)) {
			// Color color = Color.decode(clorMap.get(row));
			// Font f = new Font("Menu.font", fontMap.get(row), 14);
			// // comp.setBackground(color);
			// comp.setFont(f);
			// }
			//
			// return comp;
			// }
			// });
			partsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		}
		return partsTable;
	}

	public DefaultTableModel getTableModel(DefaultTableModel dtm,
			Object[] columNameObjects, Object[][] objects) {
		// Object[] columNameObjects = this.titleNames; //// ,
		// Object[][] objects = getValues(this.valueLists);
		if (dtm == null) {
			dtm = new DefaultTableModel(objects, columNameObjects);
		}
		return dtm;
	}

	/**
	 * ������������ķ���
	 * 
	 * @return
	 */
	public DefaultTableModel getTableModel() {
		Object[] columNameObjects = this.titleNames; // // ,
		Object[][] objects = getValues(this.valueLists);
		// List<String> idList = getAllChildItemId();
		// for (int i = 0; i < objects.length; i++) {
		// if (idList.contains(objects[i][0]) || idList.contains(objects[i][2]))
		// {
		// clorMap.put(i, "#0000ff");
		// fontMap.put(i, Font.BOLD);
		// } else {
		// clorMap.put(i, "#ccff99");
		// fontMap.put(i, Font.PLAIN);
		// }
		// }
		if (dtm == null) {
			dtm = new DefaultTableModel(objects, columNameObjects);
		}

		return dtm;
	}

	public List<String> getAllChildItemId() {
		List<String> idList = new ArrayList<String>();

		return idList;
	}

	public Object[][] getValues(List<JFomFormBean> beanList) {
		Object[][] objects = new Object[beanList.size()][JFomUtil.tableColNum];

		for (int i = 0; i < beanList.size(); i++) {
			// objects[i][0] =beanList.get(i).getIndex();
			objects[i][0] = beanList.get(i).getWlCode();
			objects[i][1] = beanList.get(i).getWlName();
			objects[i][2] = beanList.get(i).getWlGuige();
			objects[i][3] = beanList.get(i).getWlCount();
		}
		return objects;
	}

	/***************************************************/

	/**
	 * ��������ϱ���Ľ���
	 * 
	 * @author hub
	 * 
	 */
	private class writeWLCodeDialog extends AbstractAIFDialog {

		private JPanel panel;
		private JPanel panel2;
		private JPanel panel3;
		private JLabel lable;
		private JTextField textField;
		private JButton okButton;
		private JButton celButton;

		public writeWLCodeDialog() {
			super(true);
			init();
		}

		public void init() {
			this.setTitle("���������ϱ���");
			this.setPreferredSize(new Dimension(300, 130));

			lable = new JLabel("���ϱ��룺");
			textField = new JTextField(24);
			panel = new JPanel(new BorderLayout());
			panel2 = new JPanel(new FlowLayout());
			panel3 = new JPanel(new FlowLayout());

			okButton = new JButton("ȷ��");
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					okAction(e);
				}
			});
			celButton = new JButton("ȡ��");
			celButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					celAction(e);
				}
			});
			panel2.add(lable);
			panel2.add(textField);
			panel3.add(okButton);
			panel3.add(celButton);
			panel.add(panel3, BorderLayout.SOUTH);
			panel.add(panel2, BorderLayout.CENTER);
			this.add(panel, "Center");
			// this.setAlwaysOnTop(true);
			this.pack();
			this.centerToScreen();
			this.showDialog();
		}

		private void okAction(ActionEvent e) {
			String text = this.textField.getText();
			if (!text.trim().isEmpty()) {

				JFomFormBean bean = new JFomFormBean();
				recordIndex++;
				getSumCount(text, bean);
				// bean.setWlCode("WL_" + recordIndex + "_" + text);
				// bean.setWlGuige("guige");
				// bean.setWlName("name");
				// bean.setWlCount("110");
				if (bean.getWlName() == null) {
					MessageBox.post("��ERP�в�ѯ����������[" + text + "]�Ŀ��", "����",
							MessageBox.ERROR);
					return;
				}
				this.dispose();
				valueLists.add(bean);
				// partsTable.setModel(getTableModel());
				Object[] objs = { bean.getWlCode(), bean.getWlName(),
						bean.getWlGuige(), bean.getWlCount() };
				dtm.addRow(objs);
				dtm.fireTableStructureChanged();
				dtm.fireTableDataChanged();
				scrollPanel.validate();
			} else
				this.dispose();
		}

		private void celAction(ActionEvent e) {
			this.dispose();
		}

	}

	/**
	 * ��ʾ���Ͽ�����Ϣ
	 * 
	 * @author Administrator
	 * 
	 */
	private class showERPCountDialog extends AbstractAIFDialog {
		private Object[] ckNames;
		private Object[][] ckCounts;
		private String wlCode;
		private JPanel panel;
		private JPanel panel2;
		private JPanel panel3;
		private JButton simpleCopyButton;
		private JButton copyButton;
		private JButton closeButton;
		private JTable table;
		private DefaultTableModel defaultTM;

		public showERPCountDialog(String wlCode, Object[] ckNames,
				Object[][] ckCounts) {
			super(true);
			this.wlCode = wlCode;
			this.ckNames = ckNames;
			this.ckCounts = ckCounts;
			init();
		}

		public void init() {
			this.setTitle("�������");
			this.setPreferredSize(new Dimension(900, 150));
			table = getjTable(table, defaultTM, ckNames, ckCounts);

			panel = new JPanel(new BorderLayout());
			panel2 = new JPanel(new FlowLayout());
			panel3 = new JPanel(new BorderLayout());

			simpleCopyButton = new JButton("���Ƶ������");
			simpleCopyButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					simpleCopyAction(e);
				}
			});
			copyButton = new JButton("����ȫ�����");
			copyButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					totleCopyAction(e);
				}
			});
			closeButton = new JButton("�ر�");
			closeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					closeAction(e);
				}
			});

			panel2.add(simpleCopyButton);
			panel2.add(copyButton);
			panel2.add(closeButton);
			panel3.add(new JScrollPane(table), BorderLayout.CENTER);

			panel.add(panel2, BorderLayout.SOUTH);
			panel.add(panel3, BorderLayout.CENTER);
			this.add(panel, "Center");
			// this.setAlwaysOnTop(true);
			this.pack();
			this.centerToScreen();
			this.showDialog();
		}

		private void simpleCopyAction(ActionEvent e) {
			int colIndex = table.getSelectedColumn();
			if (colIndex != -1) {
				setClipboard(ckCounts[0][colIndex] + "");
			}

		}

		private void totleCopyAction(ActionEvent e) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < ckNames.length; i++) {
				sb.append(ckNames[i]);
				if (i != (ckNames.length - 1))
					sb.append("|");
			}
			sb.append("\r\n");
			for (int i = 0; i < ckCounts[0].length; i++) {
				sb.append(ckCounts[0][i]);
				if (i != (ckCounts[0].length - 1))
					sb.append("|");
			}
			setClipboard(sb.toString());
		}

		private void closeAction(ActionEvent e) {
			this.dispose();
		}
	}

	public void setClipboard(String str) {
		StringSelection ss = new StringSelection(str);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
	}

	/**************************************************/
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

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

	public static void main(String[] args) {

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {// keyCode 10
		// TODO Auto-generated method stub
		int colIndex = partsTable.getSelectedColumn();
		int rowIndex = partsTable.getSelectedRow();
		if (colIndex == 0 && rowIndex == (partsTable.getRowCount() - 1)
				&& e.getKeyCode() == 10) {

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int clickCount = e.getClickCount();
		int selectColIndex = partsTable.getSelectedColumn();
		int selectRowIndex = partsTable.getSelectedRow();

		if (clickCount == 2) {
			if (!partsTable.isEnabled()) {
				MessageBox.post("����ǩ��������ִ�д˲�����", "Error", MessageBox.ERROR);
				return;
			}
			if (selectColIndex == -1 || selectRowIndex == -1) {
				MessageBox.post("��ѡ������ٽ��в�����", "Error", MessageBox.ERROR);
				return;
			}
			String wlCode = (String) partsTable.getValueAt(selectRowIndex, 0);
			// MessageBox.post("���� ERP��ڲ�ѯ...... " + clickCount, "Info",
			// MessageBox.INFORMATION);
			JFomFormBean bean = new JFomFormBean();
			getSumCount(wlCode, bean);
			if (bean.getWlCount() == null) {
				MessageBox.post("������[" + wlCode + "]��ERP�в����ڿ�棡", "����",
						MessageBox.ERROR);
				return;
			}
			Object[] names = new Object[bean.getCountList().size() + 1];// {
																		// "���ϱ���",
																		// "�ֿ�1",
																		// "�ֿ�2",
																		// "�ֿ�3",
																		// "�ֿ�4",
																		// "�ֿ�5"
																		// };
			Object[] value = new Object[bean.getCountList().size() + 1];
			names[0] = "���ϱ���";
			value[0] = wlCode;
			for (int i = 1; i < names.length; i++) {
				names[i] = bean.getCkList().get(i - 1);
				value[i] = bean.getCountList().get(i - 1);
			}
			Object[][] values = { value };
			new showERPCountDialog(wlCode, names, values);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}
