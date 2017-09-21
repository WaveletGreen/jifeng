package com.connor.jifeng.plm.jfom008;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.connor.jifeng.plm.util.JFomMethodUtil;
import com.teamcenter.rac.aif.AbstractAIFDialog;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentType;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;
import com.teamcenter.rac.util.PropertyLayout;

public class JFomExchangeBomDialog {

	private HashMap<String, JFomExchangeBomBean> parentChildMap;
	private TCComponentItemRevision selectItemRev;
	private HashMap<TCComponentItemRevision, JFomExchangeBomNewBean> createBomMap;
	private Boolean isSelect = false;
	private Boolean isOverCreateBom = false;
	private Boolean isBomReleased = false;
	private int selectIndex = 0;
	private String revObjectString = null;
	private String[] childStrings = null;
	private String firstParent;
	private JFomExchangeBomBean firstBean;

	public JFomExchangeBomDialog(AbstractAIFUIApplication app,
			TCSession session,
			HashMap<String, JFomExchangeBomBean> parentChildMap,
			String firstParent, JFomExchangeBomBean firstBean) {
		this.parentChildMap = parentChildMap;
		this.createBomMap = new HashMap<TCComponentItemRevision, JFomExchangeBomNewBean>();
		this.firstParent = firstParent;
		this.firstBean = firstBean;
		exchangeBom();

		//
		if (isBomReleased) {
			MessageBox.post("需要转换的物料BOM中存在已经发布的子BOM，请手动删除BOM后再执行此操作！", "错误",
					MessageBox.ERROR);
			return;
		}
		if (!isOverCreateBom) {
			Set<Entry<TCComponentItemRevision, JFomExchangeBomNewBean>> set = createBomMap
					.entrySet();
			Boolean isExchangeOk = true;
			for (Entry<TCComponentItemRevision, JFomExchangeBomNewBean> entry : set) {
				try {
					JFomMethodUtil.createBom(entry.getKey(), entry.getValue()
							.getChildRevs(), entry.getValue().getQueryNoList(),
							entry.getValue().getCountList());
				} catch (TCException e) {
					e.printStackTrace();
					isExchangeOk = false;
				}
			}
			if (isExchangeOk)
				MessageBox.post("BOM转换成功！", "提示", MessageBox.INFORMATION);
			else
				MessageBox.post("BOM转换失败！", "错误", MessageBox.ERROR);
		} else {
			MessageBox.post("BOM转换退出！", "提示", MessageBox.WARNING);
		}
	}

	public void exchangeBom() {
		Set<Entry<String, JFomExchangeBomBean>> set = parentChildMap.entrySet();
		int i = 0;
		TCComponentItemRevision rev = null;
		// for (Entry<String, JFomExchangeBomBean> entry : set) {
		// String parentuid = entry.getKey();
		// JFomExchangeBomBean bean = entry.getValue();
		String parentuid = this.firstParent;
		JFomExchangeBomBean bean = this.firstBean;
		// MessageBox.post("parent ="+entry.getKey()+" | material count = "+
		// entry.getValue().getMaterialRevs().size()+" | children count = " +
		// entry.getValue().getChildrenUIDs().size() ,"",MessageBox.ERROR);
		List<String> childrenUid = bean.getChildrenUIDs();
		List<TCComponentItemRevision> wlRevList = bean.getMaterialRevs();
		try {
			String[][] objectStrings = TCComponentType.getPropertiesSet(
					wlRevList, new String[] { "object_string" });
			if (objectStrings.length == 0) {

			} else if (objectStrings.length == 1) {
				rev = wlRevList.get(0);
				if (isDiscardStatus(rev, false)) {
					return;
				}
			} else {
				if (bean.getRev() != null)
					revObjectString = bean.getRev().getStringProperty(
							"object_string");
				else
					revObjectString = "";
				childStrings = new String[objectStrings.length];
				for (int w = 0; w < objectStrings.length; w++) {
					childStrings[w] = objectStrings[w][0];
				}
				// 死循环对物料选择进行废弃状态判断
				while (true) {
					new Thread() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							SelectRev selectRev = new SelectRev(
									revObjectString, childStrings);
							super.run();
						}
					}.start();
					isSelect = true;
					while (isSelect) {
						System.out.println();
						try {
							new Thread().sleep(1000);
							System.out.println("Sleeping ............");
							// MessageBox.post("222","1",MessageBox.INFORMATION);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (isOverCreateBom)
						return;
					rev = wlRevList.get(selectIndex);
					if (isDiscardStatus(rev, true)) {
						// MessageBox.post("所选的物料已经废弃，请重新选择","警告",MessageBox.ERROR);
						// MessageBox.post("所选的物料已经废弃，请重新选择xxxx","警告",MessageBox.ERROR);
						continue;
					} else {
						if (isOverCreateBom)
							return;
						break;
					}
				}
			}
		} catch (TCException e) {
			e.printStackTrace();
		}
		createStruct(rev, childrenUid, bean.getQueryNoList(),
				bean.getCountList());
		// 只需要取第一个
		// break;
		// }

	}

	/**
	 * 
	 * @param rev
	 *            父
	 * @param childrenList
	 *            子
	 */
	public void createStruct(TCComponentItemRevision rev,
			List<String> childrenList, List<String> qeryNoList,
			List<String> countNoList) {
		if (isOverCreateBom)
			return;

		boolean isReleased = JFomMethodUtil.isRevBomReleased(rev);
		int myOperateCode = 0;
		if (isReleased) {

		} else {
			IsHadBom(rev);
			myOperateCode = operateCode;
			if (isOverCreateBom) {
				return;
			}
		}

		List<TCComponentItemRevision> childList = new ArrayList<TCComponentItemRevision>();

		if (!isReleased && myOperateCode == 1)
			for (String uid : childrenList) {
				JFomExchangeBomBean bean = parentChildMap.get(uid);

				List<String> childrenUid = bean.getChildrenUIDs();
				List<TCComponentItemRevision> wlRevList = bean
						.getMaterialRevs();
				try {
					String[][] objectStrings = TCComponentType
							.getPropertiesSet(wlRevList,
									new String[] { "object_string" });
					// MessageBox.post("3333 = > "+
					// objectStrings.length,"1",MessageBox.INFORMATION);
					if (objectStrings.length == 0) {

					} else if (objectStrings.length == 1) {
						childList.add(wlRevList.get(0));
						if (isDiscardStatus(wlRevList.get(0), false))
							return;
						createStruct(wlRevList.get(0), childrenUid,
								bean.getQueryNoList(), bean.getCountList());
					} else {
						if (bean.getRev() != null)
							revObjectString = bean.getRev().getStringProperty(
									"object_string");
						else
							revObjectString = "";
						childStrings = new String[objectStrings.length];
						for (int w = 0; w < objectStrings.length; w++) {
							childStrings[w] = objectStrings[w][0];
						}
						// TODO
						// SelectRev selectRev = new SelectRev(revObjectString,
						// childStrings);
						// new Thread(selectRev).start();
						while (true) {
							new Thread() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									SelectRev selectRev = new SelectRev(
											revObjectString, childStrings);
									super.run();
								}
							}.start();
							;
							isSelect = true;
							while (isSelect) {
								System.out.println();
								try {
									new Thread().sleep(1000);
									System.out.println("Sleeping ............");
									// MessageBox.post("222","1",MessageBox.INFORMATION);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							if (isOverCreateBom)
								return;
							if (isDiscardStatus(wlRevList.get(selectIndex),
									true)) {
								// MessageBox.post("所选的物料已经废弃，请重新选择","警告",MessageBox.ERROR);
								// MessageBox.post("所选的物料已经废弃，请重新选择xxxx","警告",MessageBox.ERROR);

								continue;
							}
							if (isOverCreateBom)
								return;
							childList.add(wlRevList.get(selectIndex));
							createStruct(wlRevList.get(selectIndex),
									childrenUid, bean.getQueryNoList(),
									bean.getCountList());
							break;
						}
					}

				} catch (TCException e) {
					e.printStackTrace();
				}
			}
		// if (JFomMethodUtil.isRevBomReleased(rev)) {
		// //isBomReleased = true;
		// createBomMap.put(rev, new ArrayList<TCComponentItemRevision>());
		// }else{
		// //IsHadBom(rev);
		// if(myOperateCode == 1){
		// createBomMap.put(rev, childList);
		// }else if(myOperateCode ==0 ){
		// createBomMap.put(rev, new ArrayList<TCComponentItemRevision>());
		// }
		// }
		JFomExchangeBomNewBean newBean = new JFomExchangeBomNewBean();
		newBean.setChildRevs(childList);
		newBean.setQueryNoList(qeryNoList);
		newBean.setCountList(countNoList);
		// newBean.setQueryNoList()
		createBomMap.put(rev, newBean);
		// 一边搭建筛选，一边搭建BOM
		// try {
		//
		// JFomMethodUtil.createBom(rev,childList);
		// } catch (TCException e) {
		// e.printStackTrace();
		// }
	}

	public Boolean isdiscard = false;

	/**
	 * 获取是否是废弃状态
	 * 
	 * @param comp
	 * @return
	 */
	public Boolean isDiscardStatus(final TCComponent comp,
			final Boolean isShowDialog) {
		this.isdiscard = false;
		Boolean isd = false;
		try {
			TCComponent[] compList = comp.getTCProperty("release_status_list")
					.getReferenceValueArray();

			if (compList != null) {
				for (int i = 0; i < compList.length; i++) {
					TCComponent compS = compList[i];
					String name = compS.getTCProperty("object_name")
							.getStringValue();
					// MessageBox.post("STATUS = " + name, "INFO",
					// MessageBox.ERROR);
					if (name.equals("Obsolete") || name.equals("废弃")) {
						isd = true;
					}
				}
			}
		} catch (TCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (isd) {
			new Thread() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					DiscardeStatusShowDialog discard = new DiscardeStatusShowDialog(
							comp, "", isShowDialog);
					super.run();
				}
			}.start();

			isSelect = true;
			while (isSelect) {
				System.out.println();
				try {
					new Thread().sleep(1000);
					System.out.println("Sleeping ............");
					// MessageBox.post("222","1",MessageBox.INFORMATION);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (isShowDialog)
			isd = this.isdiscard;
		return isd;

	}

	public int operateCode = 0;

	public void IsHadBom(final TCComponent comp) {
		operateCode = 1;
		if (JFomMethodUtil.isRevHadBom((TCComponentItemRevision) comp)) {
			String itemID = "";
			try {
				itemID = ((TCComponentItemRevision) comp)
						.getStringProperty("item_id");
			} catch (TCException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			final String itemIDT = itemID;
			new Thread() {
				@Override
				public void run() {
					ShowHadBomDialog discard = new ShowHadBomDialog(itemIDT);
					super.run();
				}
			}.start();

			isSelect = true;
			while (isSelect) {
				System.out.println();
				try {
					new Thread().sleep(1000);
					System.out.println("Sleeping ............");
					// MessageBox.post("222","1",MessageBox.INFORMATION);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	/**
	 * 显示有BOM视图存在
	 * 
	 * @author Administrators
	 * 
	 */
	private class ShowHadBomDialog extends AbstractAIFDialog implements
			Runnable {
		private JPanel rootPanel;
		private JLabel label;
		private JButton okButton;
		private JButton celButton;
		private JButton continueButton;
		private JPanel panel2;
		private String discardNo;

		// private Boolean isShowDialog;

		public ShowHadBomDialog(String discardNo) {
			super(true);

			this.discardNo = discardNo;
			// this.isShowDialog = isShowDialog;
			init();
		}

		public void init() {
			this.setTitle("图纸BOM转换物料BOM");
			this.setPreferredSize(new Dimension(440, 110));
			this.label = new JLabel("所选择的物料[" + discardNo
					+ "]已经存在BOM视图，是否继续覆盖该BOM视图?");
			okButton = new JButton("覆盖");

			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					okEvent(e);
				}
			});
			// if(isShowDialog)
			// okButton.setEnabled(isShowDialog);
			celButton = new JButton("退出");
			celButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					celEvent(e);
				}
			});

			continueButton = new JButton("保持");
			continueButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					continueEvent(e);
				}
			});

			panel2 = new JPanel(new FlowLayout());
			panel2.add(okButton);
			panel2.add(new JLabel("    "));
			panel2.add(continueButton);
			panel2.add(new JLabel("    "));
			panel2.add(celButton);

			rootPanel = new JPanel(new BorderLayout());
			rootPanel.add(label, BorderLayout.CENTER);
			rootPanel.add(panel2, BorderLayout.SOUTH);
			this.add(rootPanel, "Center");
			this.pack();
			this.centerToScreen();
			this.showDialog();
		}

		/**
		 * 确定事件
		 * 
		 * @param e
		 */
		public void okEvent(ActionEvent e) {
			// this.celButton.setEnabled(false);
			// selectIndex = wlCombox.getSelectedIndex();
			isSelect = false;
			operateCode = 1;
			// isdiscard = true;
			// this.celButton.setEnabled(true);
			this.dispose();
		}

		/**
		 * 取消事件 取消事件默认选择为第一个
		 * 
		 * @param e
		 */
		public void celEvent(ActionEvent e) {
			this.dispose();
			isSelect = false;
			operateCode = -1;
			// isdiscard = false;
			isOverCreateBom = true;
		}

		/**
		 * 保持
		 * 
		 * @param e
		 */
		public void continueEvent(ActionEvent e) {
			this.dispose();
			isSelect = false;
			operateCode = 0;
			// isdiscard = false;
			// isOverCreateBom = true;
		}
	}

	/**
	 * 废弃状态的对话框
	 * 
	 * @author Administrators
	 * 
	 */
	private class DiscardeStatusShowDialog extends AbstractAIFDialog implements
			Runnable {
		private JPanel rootPanel;
		private JLabel label;
		private JButton okButton;
		private JButton celButton;
		private JPanel panel2;
		private String discardNo;
		private TCComponent comp;
		private Boolean isShowDialog;

		public DiscardeStatusShowDialog(TCComponent comp, String discardNo,
				Boolean isShowDialog) {
			super(true);
			this.comp = comp;
			this.discardNo = discardNo;
			this.isShowDialog = isShowDialog;
			init();
		}

		public void init() {
			this.setTitle("图纸BOM转换物料BOM");
			this.setPreferredSize(new Dimension(440, 110));
			this.label = new JLabel("所选择的物料已经废弃，请重新选择!");
			okButton = new JButton("重新选择");

			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					okEvent(e);
				}
			});
			// if(isShowDialog)
			okButton.setEnabled(isShowDialog);
			celButton = new JButton("取消退出");
			celButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					celEvent(e);
				}
			});

			panel2 = new JPanel(new FlowLayout());
			panel2.add(okButton);
			panel2.add(new JLabel("     "));
			panel2.add(celButton);
			rootPanel = new JPanel(new BorderLayout());
			rootPanel.add(label, BorderLayout.CENTER);
			rootPanel.add(panel2, BorderLayout.SOUTH);
			this.add(rootPanel, "Center");
			this.pack();
			this.centerToScreen();
			this.showDialog();
		}

		/**
		 * 确定事件
		 * 
		 * @param e
		 */
		public void okEvent(ActionEvent e) {
			// this.celButton.setEnabled(false);
			// selectIndex = wlCombox.getSelectedIndex();
			isSelect = false;
			isdiscard = true;
			// this.celButton.setEnabled(true);
			this.dispose();
		}

		/**
		 * 取消事件 取消事件默认选择为第一个
		 * 
		 * @param e
		 */
		public void celEvent(ActionEvent e) {
			this.dispose();
			isSelect = false;
			isdiscard = false;
			isOverCreateBom = true;
		}

	}

	/**
	 * 选择物料的对话框
	 * 
	 * @author Administrator
	 * 
	 */
	private class SelectRev extends AbstractAIFDialog implements Runnable {
		private JPanel rootPanel;
		private JTextField drawing;
		private JComboBox<String> wlCombox;
		private JButton okButton;
		private JButton celButton;
		private JPanel panel1;
		private JPanel panel2;
		private String parentObjectString;
		private String[] childObjectStrings;

		public SelectRev(String parentObjectString, String[] childObjectStrings) {
			super(true);
			this.parentObjectString = parentObjectString;
			this.childObjectStrings = childObjectStrings;
			init();
		}

		@Override
		public void run() {
			System.out.println("bbbbb");
			super.run();
		}

		public void init() {
			this.setTitle("图纸BOM转换物料BOM");
			this.setPreferredSize(new Dimension(450, 220));
			drawing = new JTextField(30);
			drawing.setEditable(false);
			drawing.setText(parentObjectString);
			wlCombox = new JComboBox<String>(childObjectStrings);
			panel1 = new JPanel(new PropertyLayout());
			panel1.add("1.1.right.top.preferred.preferred", new JLabel("     "));
			panel1.add("2.1.right.top.preferred.preferred", new JLabel("     "));
			panel1.add("2.2.left.top.preferred.preferred", new JLabel("图纸"));
			panel1.add("3.1.right.top.preferred.preferred", new JLabel("     "));
			panel1.add("3.2.right.top.preferred.preferred", drawing);
			panel1.add("4.1.right.top.preferred.preferred", new JLabel("     "));
			panel1.add("4.2.right.top.preferred.preferred", new JLabel("物料"));
			panel1.add("5.1.right.top.preferred.preferred", new JLabel("     "));
			panel1.add("5.2.right.top.preferred.preferred", wlCombox);

			okButton = new JButton("确定");
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					okEvent(e);
				}
			});
			celButton = new JButton("取消");
			celButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					celEvent(e);
				}
			});

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
		 * 确定事件
		 * 
		 * @param e
		 */
		public void okEvent(ActionEvent e) {
			this.celButton.setEnabled(false);
			selectIndex = wlCombox.getSelectedIndex();
			isSelect = false;
			// this.celButton.setEnabled(true);
			this.dispose();
		}

		/**
		 * 取消事件 取消事件默认选择为第一个
		 * 
		 * @param e
		 */
		public void celEvent(ActionEvent e) {
			this.dispose();
			isSelect = false;
			isOverCreateBom = true;
		}
	}
}
