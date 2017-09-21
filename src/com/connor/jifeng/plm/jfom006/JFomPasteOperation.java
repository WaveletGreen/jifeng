package com.connor.jifeng.plm.jfom006;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.connor.jifeng.plm.util.ExcelUtil;
import com.connor.jifeng.plm.util.JFomUtil;
import com.teamcenter.rac.aif.AIFClipboard;
import com.teamcenter.rac.aif.AIFDesktop;
import com.teamcenter.rac.aif.AIFPortal;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.aif.kernel.AbstractAIFSession;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.commands.newproxylink.NewProxyLinkCommand;
import com.teamcenter.rac.commands.newproxylink.NewProxyLinkOperation;
import com.teamcenter.rac.commands.open.OpenCommand;
import com.teamcenter.rac.commands.paste.PasteOperation;
import com.teamcenter.rac.common.Activator;
import com.teamcenter.rac.common.IMultiTarget;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentDataset;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentTcFile;
import com.teamcenter.rac.kernel.TCComponentTempProxyLink;
import com.teamcenter.rac.kernel.TCComponentTraceLink;
import com.teamcenter.rac.kernel.TCComponentType;
import com.teamcenter.rac.kernel.TCComponentUser;
import com.teamcenter.rac.kernel.TCComponentWorkContext;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCPreferenceService;
import com.teamcenter.rac.kernel.TCProperty;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.ConfirmationDialog;
import com.teamcenter.rac.util.FileUtility;
import com.teamcenter.rac.util.MessageBox;
import com.teamcenter.rac.util.OSGIUtil;
import com.teamcenter.rac.util.Registry;
import com.teamcenter.rac.util.log.Debug;
//import com.teamcenter.rac.kernel.SystemMonitorInfo;
//import com.teamcenter.rac.kernel.TCSystemMonitorService;

public class JFomPasteOperation extends PasteOperation implements IMultiTarget {

	public JFomPasteOperation(
			AbstractAIFUIApplication abstractaifuiapplication,
			TCComponent tccomponent,
			InterfaceAIFComponent interfaceaifcomponent, String s) {
		super(abstractaifuiapplication, tccomponent, interfaceaifcomponent, s);
		statusUse = false;
		cookieName = "PasteConfirm";
		openAfterPasteFlag = false;
		AIFComponentContext aifcomponentcontext = new AIFComponentContext(
				tccomponent, interfaceaifcomponent, s);
		contextArray = (new AIFComponentContext[] { aifcomponentcontext });
	}

	public JFomPasteOperation(
			AbstractAIFUIApplication abstractaifuiapplication,
			TCComponent tccomponent,
			InterfaceAIFComponent ainterfaceaifcomponent[], String s) {
		super(abstractaifuiapplication, tccomponent, ainterfaceaifcomponent, s);
		statusUse = false;
		cookieName = "PasteConfirm";
		openAfterPasteFlag = false;
		parentComp = tccomponent;
		childrenComps = ainterfaceaifcomponent;
		contextString = s;
	}

	public JFomPasteOperation(
			AbstractAIFUIApplication abstractaifuiapplication,
			TCComponent tccomponent,
			InterfaceAIFComponent ainterfaceaifcomponent[], String s,
			boolean flag) {
		super(abstractaifuiapplication, tccomponent, ainterfaceaifcomponent, s,
				flag);
		statusUse = false;
		cookieName = "PasteConfirm";
		openAfterPasteFlag = false;
		parentComp = tccomponent;
		childrenComps = ainterfaceaifcomponent;
		contextString = s;
		openAfterPasteFlag = flag;
	}

	public JFomPasteOperation(TCComponent tccomponent,
			InterfaceAIFComponent ainterfaceaifcomponent[], String s) {
		super(tccomponent, ainterfaceaifcomponent, s);
		statusUse = false;
		cookieName = "PasteConfirm";
		openAfterPasteFlag = false;
		parentComp = tccomponent;
		childrenComps = ainterfaceaifcomponent;
		contextString = s;
	}

	public JFomPasteOperation(
			AbstractAIFUIApplication abstractaifuiapplication,
			AIFComponentContext aifcomponentcontext) {
		super(abstractaifuiapplication, aifcomponentcontext);
		statusUse = false;
		cookieName = "PasteConfirm";
		openAfterPasteFlag = false;
		contextArray = (new AIFComponentContext[] { aifcomponentcontext });
	}

	public JFomPasteOperation(AIFComponentContext aifcomponentcontext) {
		super(aifcomponentcontext);
		statusUse = false;
		cookieName = "PasteConfirm";
		openAfterPasteFlag = false;
		contextArray = (new AIFComponentContext[] { aifcomponentcontext });
	}

	public JFomPasteOperation(AIFComponentContext aifcomponentcontext,
			boolean flag) {
		super(aifcomponentcontext, flag);
		statusUse = false;
		cookieName = "PasteConfirm";
		openAfterPasteFlag = false;
		contextArray = (new AIFComponentContext[] { aifcomponentcontext });
		openAfterPasteFlag = flag;
	}

	public JFomPasteOperation(
			AbstractAIFUIApplication abstractaifuiapplication,
			AIFComponentContext aaifcomponentcontext[]) {
		this(aaifcomponentcontext);
	}

	public JFomPasteOperation(AIFComponentContext aaifcomponentcontext[]) {
		super(aaifcomponentcontext);
		statusUse = false;
		cookieName = "PasteConfirm";
		openAfterPasteFlag = false;
		contextArray = aaifcomponentcontext;
	}

	public JFomPasteOperation(TCComponent tccomponent,
			InterfaceAIFComponent ainterfaceaifcomponent[], String s,
			Object obj, boolean flag) {
		this(tccomponent, ainterfaceaifcomponent, s, obj, flag, false);
	}

	public JFomPasteOperation(TCComponent tccomponent,
			InterfaceAIFComponent ainterfaceaifcomponent[], String s,
			Object obj, boolean flag, boolean flag1) {
		super(tccomponent, ainterfaceaifcomponent, s, obj, flag);
		statusUse = false;
		cookieName = "PasteConfirm";
		openAfterPasteFlag = false;
		parentComp = tccomponent;
		childrenComps = ainterfaceaifcomponent;
		contextString = s;
		operationResultNew = obj;
		statusUse = flag;
		openAfterPasteFlag = flag1;
	}

	/**
	 * 添加对粘贴操作的报表功能
	 * 
	 * @param parentComp
	 * @param childrenComps
	 * @param grmStr
	 */
	public void checkChildren(TCComponent parentComp,
			InterfaceAIFComponent[] childrenComps, String grmStr) {
		if (parentComp != null && childrenComps != null) {
			if (parentComp.getType().equals(JFomUtil.JF3_PASTE_PARENT_TYPE)
					&& grmStr.equals(JFomUtil.JF3_PASTE_GRM)) {
				List<JFomPasteBean> beanListBef = null;
				String excelPath = null;
				try {
					TCProperty parentProp = parentComp
							.getTCProperty(JFomUtil.JF3_PASTE_EXCEL_GRM);
					excelPath = getTcproptertyToStr(parentProp,
							JFomUtil.JF3_DATASET_TYPE);
					beanListBef = getProductInfo(excelPath);
				} catch (TCException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				List<TCComponentItemRevision> revList = new ArrayList<TCComponentItemRevision>();
				for (int i = 0; i < childrenComps.length; i++) {
					if (childrenComps[i] instanceof TCComponentItemRevision) {
						revList.add((TCComponentItemRevision) childrenComps[i]);
					}
				}
				if (revList.size() != 0) {
					try {
						List<JFomPasteBean> beanListAft = new ArrayList<JFomPasteBean>();
						TCProperty[][] propValues = TCComponentType
								.getTCPropertiesSet(revList,
										JFomUtil.JF3_PASTE_PROPS);
						for (int i = 0; i < propValues.length; i++) {
							// TODO 添加逻辑
							// JFomPasteBean bean =
							String picPath = getTcproptertyToStr(
									propValues[i][0],
									JFomUtil.JF3_DATASET_TYPE2);
							String nameStr = getTcproptertyToStr(
									propValues[i][1], "");
							String itemIDStr = getTcproptertyToStr(
									propValues[i][2], "");
							String revIDStr = getTcproptertyToStr(
									propValues[i][3], "");
							String dateStr = getTcproptertyToStr(
									propValues[i][4], "");
							JFomPasteBean bean = new JFomPasteBean("",
									itemIDStr, nameStr, picPath, itemIDStr
											+ "/" + nameStr, revIDStr, dateStr);
							beanListAft.add(bean);
						}
						List<JFomPasteBean> beanListAll = addNewBean(
								beanListBef, beanListAft);
						writeAllBeanExcel(beanListAll, excelPath);
					} catch (TCException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else if (parentComp.getType().equals(
					JFomUtil.JF3_PASTE_PARENT_TYPE2)
					&& grmStr.equals(JFomUtil.JF3_PASTE_GRM2)) {
				for (int u = 0; u < childrenComps.length; u++) {
					if (childrenComps[u].getType().equals(
							JFomUtil.JF3_PASTE_PARENT_TYPE3)) {
						// TODO
						try {
							TCComponent[] comps1 = parentComp
									.getReferenceListProperty(JFomUtil.JF3_PASTE_GRM3);
							TCComponentItemRevision rev = ((TCComponentItem) childrenComps[u])
									.getLatestItemRevision();
							if (comps1 != null && comps1.length != 0) {
								rev.add(JFomUtil.JF3_BGQduixiang, comps1);
							}
						} catch (TCException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	/**
	 * 将粘贴后的所有的对象写入到excel中
	 * 
	 * @param beanListAll
	 * @param excelPath
	 */
	public void writeAllBeanExcel(List<JFomPasteBean> beanListAll,
			String excelPath) {
		// excelDataset

		String outExcelPath = null;
		if (excelPath.endsWith(".xls")) {
			outExcelPath = excelPath.replace(".xls", "_1.xls");
			ExcelUtil util = new ExcelUtil();
			try {
				util.writeExcel(excelPath, outExcelPath, beanListAll, 3);
				changeDataSet(excelDataset, "excel", "MSExcel", outExcelPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TCException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * 改变数据集的命名引用
	 * 
	 * @param tccomponentDataset
	 * @param quote
	 * @param type
	 * @param path
	 * @throws TCException
	 */
	public static void changeDataSet(TCComponentDataset tccomponentDataset,
			String quote, String type, String path) throws TCException {
		String myPath[] = { path };
		String myQuote[] = { quote };// 引用"excel"
		String myType[] = { type };// 类型"MSExcelX"
		String myPlain[] = { "Plain" };

		// 删除数据集的引用
		deleDateSetRef(tccomponentDataset);
		// 数据集的替换
		tccomponentDataset.setFiles(myPath, myType, myPlain, myQuote);

	}

	/**
	 * 删除数据集的命名引用
	 * 
	 * @param dataset
	 * @throws TCException
	 */
	private static void deleDateSetRef(TCComponentDataset dataset)
			throws TCException {

		TCComponentTcFile[] tcFiles = dataset.getTcFiles();
		for (int i = 0; i < tcFiles.length; i++) {
			// 得到数据集的引用类型
			String str_temp = getNamedRefType(dataset, tcFiles[i]);
			// 删除引用
			dataset.removeNamedReference(str_temp);
		}

	}

	/**
	 * 得到数据集的引用类型
	 * 
	 * @param datasetComponent
	 * @param tccomponent
	 * @return
	 * @throws TCException
	 */
	private static String getNamedRefType(TCComponentDataset datasetComponent,
			TCComponentTcFile tccomponent) throws TCException {
		String s;
		s = "";
		TCProperty tcproperty;
		TCProperty tcproperty1;
		TCComponent atccomponent[];
		String as[];
		int i;
		int j;
		int k;
		try {
			tcproperty = datasetComponent.getTCProperty("ref_list");
			tcproperty1 = datasetComponent.getTCProperty("ref_names");
			if (tcproperty == null || tcproperty1 == null)
				return s;
		} catch (TCException tcexception) {
			return s;
		}
		atccomponent = tcproperty.getReferenceValueArray();
		as = tcproperty1.getStringValueArray();
		if (atccomponent == null || as == null)
			return s;
		i = atccomponent.length;
		if (i != as.length)
			return s;
		j = -1;
		k = 0;
		do {
			if (k >= i)
				break;
			if (tccomponent == atccomponent[k]) {
				j = k;
				break;
			}
			k++;
		} while (true);
		if (j != -1)
			s = as[j];
		return s;
	}

	/**
	 * 获取属性
	 * 
	 * @param prop
	 * @param isEmpty
	 * @return
	 */
	public String getTcproptertyToStr(TCProperty prop, String compType) {
		if (prop == null) {
			return "";
		}
		String tempPath = System.getenv("TEMP");
		if (tempPath == null) {
			tempPath = "";
		} else if (!tempPath.endsWith("\\")) {
			tempPath = tempPath + "\\";
		}
		String propValue = new String();
		int propType = prop.getPropertyType();
		switch (propType) {
		case TCProperty.PROP_untyped_relation:
			TCComponent[] comps = prop.getReferenceValueArray();
			try {
				propValue = downLoadFile(comps, compType);
			} catch (TCException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case TCProperty.PROP_int:
			propValue = "" + prop.getIntValue();
			break;
		case TCProperty.PROP_string:
			propValue = prop.getStringValue();
			break;
		case TCProperty.PROP_date:

			SimpleDateFormat sdf = new SimpleDateFormat(JFomUtil.TIME_FORMAT);
			Date date = prop.getDateValue();
			if (date != null)
				propValue = sdf.format(date);
			else
				propValue = "";
			break;

		}

		return propValue;
	}

	/**
	 * 下载文件
	 * 
	 * @param comps
	 * @return
	 * @throws TCException
	 * @throws IOException
	 */
	public String downLoadFile(TCComponent[] comps, String compType)
			throws TCException, IOException {
		if (comps == null) {
			return "";
		}
		String value = "";
		String tempPath = System.getenv("TEMP");
		// MessageBox.post(" tempPath = "+tempPath,"INFO",MessageBox.INFORMATION);
		if (tempPath == null) {
			tempPath = "";
		} else if (!tempPath.endsWith("\\")) {
			tempPath = tempPath + "\\";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(JFomUtil.TIME_FORMAT2);
		for (TCComponent comp : comps) {
			if (comp instanceof TCComponentDataset) {
				if (!comp.getType().equals(compType)) {
					continue;
				}
				TCComponentTcFile[] tcFiles = ((TCComponentDataset) comp)
						.getTcFiles();
				File file = null;
				if (tcFiles != null) {
					file = tcFiles[0].getFmsFile();
					String fileName = file.getName();
					String fileDix = fileName.substring(
							fileName.lastIndexOf("."), fileName.length());
					fileName = tempPath + sdf.format(new Date()) + fileDix;
					File dirFile = new File(fileName);
					FileUtility.copyFile(file, dirFile);
					if (compType.equals(JFomUtil.JF3_DATASET_TYPE)) {
						this.excelDataset = (TCComponentDataset) comp;
					}
					return fileName;
				}
			}
		}
		return value;
	}

	/**
	 * 获取原有的EXCEL中的信息
	 * 
	 * @param excelPath
	 * @return
	 */
	public List<JFomPasteBean> getProductInfo(String excelPath) {
		ExcelUtil excelUtil03 = new ExcelUtil();
		List<JFomPasteBean> pasteBefMsgBeans = new ArrayList<JFomPasteBean>();
		try {
			List<List<String>> msgStrList = excelUtil03.readExcel(excelPath);
			if (msgStrList == null) {
				return pasteBefMsgBeans;
			}
			for (int i = JFomUtil.JF3_PASTE_EXCEL_READ_INDEX; i < msgStrList
					.size(); i++) {
				List<String> msgList = msgStrList.get(i);
				if (msgList.get(1).trim().isEmpty())
					continue;
				String itemID = "";
				if (msgList.get(3).contains("/"))
					itemID = msgList.get(3).split("/")[0];
				else
					itemID = msgList.get(3);
				JFomPasteBean bean = new JFomPasteBean(msgList.get(0), itemID,
						msgList.get(1), msgList.get(2), msgList.get(3),
						msgList.get(4), msgList.get(6));
				pasteBefMsgBeans.add(bean);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pasteBefMsgBeans;
	}

	/**
	 * 将新添加的对象加入到粘贴前的缓存
	 * 
	 * @param beanListBef
	 * @param beanListAft
	 * @return
	 */
	public List<JFomPasteBean> addNewBean(List<JFomPasteBean> beanListBef,
			List<JFomPasteBean> beanListAft) {
		// MessageBox.post(beanListBef.size()+" | "+beanListAft.size(),"INFO",MessageBox.INFORMATION);
		List<String> befRecord = new ArrayList<String>();
		List<String> aftRecord = new ArrayList<String>();
		for (int i = 0; i < beanListBef.size(); i++) {
			String record = beanListBef.get(i).getItemID() + "/"
					+ beanListBef.get(i).getItemRevisionId();
			befRecord.add(record);
		}
		for (int i = 0; i < beanListAft.size(); i++) {
			String record = beanListAft.get(i).getItemID() + "/"
					+ beanListAft.get(i).getItemRevisionId();
			aftRecord.add(record);
		}
		for (int i = 0; i < aftRecord.size(); i++) {
			if (befRecord.contains(aftRecord.get(i))) {
				System.out.println("该物料[" + aftRecord.get(i) + "]已经存在，不需要再添加");
			} else {
				JFomPasteBean bean = beanListAft.get(i);
				int index = beanListBef.size() + 1;
				bean.setIndexStr(index + "");
				beanListBef.add(bean);
			}
		}
		return beanListBef;
	}

	public void executeOperation() throws TCException {
		// MessageBox.post("XXXXXX","INFO",MessageBox.INFORMATION);
		try {
			checkChildren(parentComp, childrenComps, contextString);
		} catch (Exception ex) {

			System.out.println("黏贴客制化功能出现异常！");
		}
		// super.executeOperation();
		byte byte0 = 32;
		boolean flag = false;
		SystemMonitorInfo systemmonitorinfo = new SystemMonitorInfo();
		TCSession tcsession = (TCSession) getSession();
		TCSystemMonitorService tcsystemmonitorservice = tcsession
				.getTCSystemMonitorService();
		Registry registry = Registry.getRegistry(this);
		if (parentComp != null && childrenComps != null) {
			try {
				flag = tcsystemmonitorservice.is_enabled(byte0);
				if (flag)
					systemmonitorinfo = tcsystemmonitorservice.start(byte0);
				executeByChunk();
			} catch (TCException tcexception) {
				if (flag) {
					systemmonitorinfo.getMetricvalues()[0]
							.numericid32(tcexception.hashCode());
					systemmonitorinfo.getMetricvalues()[1].counter32(0);
					systemmonitorinfo.setString32(childrenComps[0].toString());
					tcsystemmonitorservice.stop(byte0,
							systemmonitorinfo.getHandle(), 1,
							systemmonitorinfo.getInfosize(),
							systemmonitorinfo.getMetriccount(),
							systemmonitorinfo.getMetrictypes(),
							systemmonitorinfo.getString32(),
							systemmonitorinfo.getMetricvalues());
				}
				throw tcexception;
			}
			if (flag) {
				systemmonitorinfo.getMetricvalues()[0].numericid32(0);
				systemmonitorinfo.getMetricvalues()[1]
						.counter32(childrenComps.length);
				systemmonitorinfo.setString32(childrenComps[0].toString());
				try {
					tcsystemmonitorservice.stop(byte0,
							systemmonitorinfo.getHandle(), 0,
							systemmonitorinfo.getInfosize(),
							systemmonitorinfo.getMetriccount(),
							systemmonitorinfo.getMetrictypes(),
							systemmonitorinfo.getString32(),
							systemmonitorinfo.getMetricvalues());
				} catch (Exception _ex) {
				}
			}
			if (openAfterPasteFlag)
				try {
					OpenCommand opencommand = (OpenCommand) registry
							.newInstanceForEx("openCommand", new Object[] {
									AIFUtility.getActiveDesktop(),
									childrenComps });
					opencommand.executeModeless();
				} catch (Exception exception) {
					logger.error(exception.getLocalizedMessage(), exception);
				}
			return;
		}
		Object obj = null;
		Object obj1 = null;
		ArrayList arraylist = new ArrayList();
		boolean flag1 = false;
		try {
			ArrayList arraylist1 = new ArrayList();
			for (int i = 0; i < contextArray.length; i++) {
				TCComponent tccomponent2 = (TCComponent) contextArray[i]
						.getParentComponent();
				if (contextArray[i].getComponent() instanceof TCComponentTempProxyLink) {
					TCComponentTempProxyLink tccomponenttempproxylink = (TCComponentTempProxyLink) contextArray[i]
							.getComponent();
					setStatus(MessageFormat.format(
							registry.getString("pastingInto"), new Object[] {
									tccomponenttempproxylink.toString(),
									tccomponent2.toString() }));
					NewProxyLinkCommand newproxylinkcommand = new NewProxyLinkCommand(
							tccomponenttempproxylink.getSession(),
							AIFDesktop.getActiveDesktop(),
							tccomponenttempproxylink.getAppId(),
							new String[] { tccomponenttempproxylink.getObjId() },
							new InterfaceAIFComponent[] { tccomponent2 },
							Boolean.FALSE);
					newproxylinkcommand.executeModal();
					NewProxyLinkOperation newproxylinkoperation = newproxylinkcommand
							.getOperation();
					if (newproxylinkoperation != null) {
						TCComponent atccomponent[] = newproxylinkoperation
								.getNewObjectLinks();
						if (atccomponent != null && atccomponent.length > 0) {
							TCComponent tccomponent = atccomponent[0];
							String s = tccomponent2
									.getPreferredPasteRelation(tccomponent);
							contextArray[i] = new AIFComponentContext(
									tccomponent2, tccomponent, s);
						} else {
							contextArray[i] = null;
						}
					} else {
						contextArray[i] = null;
					}
				} else {
					TCComponent tccomponent1 = (TCComponent) contextArray[i]
							.getComponent();
					setStatus(MessageFormat.format(
							registry.getString("pastingInto"),
							new Object[] { tccomponent1.toString(),
									tccomponent2.toString() }));
					contextArray[i] = processPaste(contextArray[i], arraylist1);
					arraylist.add(tccomponent1);
					if (!flag1
							&& (tccomponent1 instanceof TCComponentWorkContext))
						flag1 = true;
				}
			}

			pasteInChunk(arraylist1);
		} catch (Exception exception1) {
			if (Debug.isOn("action,copy,cut,paste"))
				Debug.printStackTrace(exception1);
			if (flag) {
				systemmonitorinfo.getMetricvalues()[0].numericid32(exception1
						.hashCode());
				systemmonitorinfo.getMetricvalues()[1].counter32(0);
				systemmonitorinfo.setString32(childrenComps[0].toString());
				tcsystemmonitorservice.stop(byte0,
						systemmonitorinfo.getHandle(), 1,
						systemmonitorinfo.getInfosize(),
						systemmonitorinfo.getMetriccount(),
						systemmonitorinfo.getMetrictypes(),
						systemmonitorinfo.getString32(),
						systemmonitorinfo.getMetricvalues());
			}
			throw new TCException(exception1);
		}
		if (flag) {
			systemmonitorinfo.getMetricvalues()[0].numericid32(0);
			systemmonitorinfo.getMetricvalues()[1]
					.counter32(childrenComps.length);
			systemmonitorinfo.setString32(childrenComps[0].toString());
			try {
				tcsystemmonitorservice.stop(byte0,
						systemmonitorinfo.getHandle(), 0,
						systemmonitorinfo.getInfosize(),
						systemmonitorinfo.getMetriccount(),
						systemmonitorinfo.getMetrictypes(),
						systemmonitorinfo.getString32(),
						systemmonitorinfo.getMetricvalues());
			} catch (Exception _ex) {
			}
		}
		if (flag1)
			clearWorkContextProperty(tcsession);
		if (openAfterPasteFlag && !arraylist.isEmpty()) {
			InterfaceAIFComponent ainterfaceaifcomponent[] = (InterfaceAIFComponent[]) arraylist
					.toArray(new InterfaceAIFComponent[arraylist.size()]);
			try {
				OpenCommand opencommand1 = (OpenCommand) registry
						.newInstanceForEx("openCommand", new Object[] {
								AIFUtility.getActiveDesktop(),
								ainterfaceaifcomponent });
				opencommand1.executeModeless();
			} catch (Exception exception2) {
				logger.error(exception2.getLocalizedMessage(), exception2);
			}
		}
	}

	protected AIFComponentContext processPaste(
			AIFComponentContext aifcomponentcontext, List list)
			throws TCException {
		TCComponent tccomponent = (TCComponent) aifcomponentcontext
				.getParentComponent();
		return tccomponent.pasteOperation(aifcomponentcontext);
	}

	protected void pasteInChunk(List list) throws TCException {
	}

	private void executeByChunk() throws TCException {
		if (parentComp == null || childrenComps == null)
			return;
		TCSession tcsession = parentComp.getSession();
		Registry registry = Registry.getRegistry(this);
		AIFComponentContext aaifcomponentcontext[] = new AIFComponentContext[childrenComps.length];
		boolean flag = false;
		try {
			StringBuilder stringbuilder = new StringBuilder();
			for (int i = 0; i < childrenComps.length; i++) {
				stringbuilder.append(i != 0 ? ", " : "");
				stringbuilder.append(childrenComps[i]);
			}

			setStatus(MessageFormat.format(
					registry.getString("pastingInto"),
					new Object[] { stringbuilder.toString(),
							parentComp.toString() }));
			for (int j = 0; j < childrenComps.length; j++) {
				aaifcomponentcontext[j] = new AIFComponentContext(parentComp,
						childrenComps[j], contextString);
				if (!flag
						&& (childrenComps[j] instanceof TCComponentWorkContext))
					flag = true;
			}

			boolean flag1 = isConfirmPasteEnabled();
			boolean flag2 = false;
			if (flag1)
				flag2 = isExplicitPasteOperation();
			if (flag2) {
				int k = ConfirmationDialog.post(AIFDesktop.getActiveDesktop(),
						registry.getString("pasteConfirmTitle"),
						registry.getString("pasteConfirmText"), false,
						cookieName);
				if (k == 2)
					if ((parentComp instanceof TCComponentTraceLink)
							&& ((TCComponent) childrenComps[0])
									.isTypeOf("Fnd0CustomNoteRevision"))
						pasteToNoteProperty();
					else
						storeOperationResult(parentComp
								.pasteOperation(aaifcomponentcontext));
			} else if ((parentComp instanceof TCComponentTraceLink)
					&& ((TCComponent) childrenComps[0])
							.isTypeOf("Fnd0CustomNoteRevision"))
				pasteToNoteProperty();
			else
				storeOperationResult(parentComp
						.pasteOperation(aaifcomponentcontext));
		} catch (TCException tcexception) {
			int ai[] = tcexception.getErrorCodes();
			String s = contextString;
			if (ai[0] == 38015) {
				if (s == null || s.isEmpty())
					s = parentComp.getDefaultPasteRelation();
				String s1 = parentComp.getType();
				Object aobj[] = { s1, s };
				String s2 = MessageFormat.format(registry
						.getString("formattedAttachmentPanelPasteError"), aobj);
				MessageBox.post(s2.trim(), registry.getString("warning.TITLE"),
						4);
			} else {
				throw tcexception;
			}
		}
		if (flag)
			clearWorkContextProperty(tcsession);
	}

	private void clearWorkContextProperty(AbstractAIFSession abstractaifsession) {
		if (abstractaifsession != null
				&& (abstractaifsession instanceof TCSession)) {
			TCSession tcsession = (TCSession) abstractaifsession;
			TCComponentUser tccomponentuser = tcsession.getUser();
			if (tccomponentuser != null)
				tccomponentuser.clearCache("work_contexts");
		}
	}

	private boolean isConfirmPasteEnabled() {
		TCPreferenceService tcpreferenceservice = (TCPreferenceService) OSGIUtil
				.getService(Activator.getDefault(), TCPreferenceService.class);
		Boolean boolean1 = tcpreferenceservice
				.getLogicalValue("TC_confirm_paste_operation");
		return boolean1 != null && boolean1.booleanValue();
	}

	private boolean isExplicitPasteOperation() {
		Vector vector = null;
		boolean flag = false;
		AIFClipboard aifclipboard = AIFPortal.getClipboard();
		java.awt.datatransfer.Transferable transferable = aifclipboard
				.getContents(this);
		try {
			if (transferable != null)
				vector = (Vector) transferable
						.getTransferData(new java.awt.datatransfer.DataFlavor(
								Vector.class, "AIF Vector"));
		} catch (Exception exception) {
			logger.error(exception.getLocalizedMessage(), exception);
			vector = null;
		}
		if (vector != null) {
			flag = true;
			for (int i = 0; i < childrenComps.length; i++) {
				int j = 0;
				for (int k = 0; k < vector.size(); k++) {
					if (((TCComponent) vector.get(k)).equals(childrenComps[i]))
						break;
					j++;
				}

				if (j != vector.size())
					continue;
				flag = false;
				break;
			}

			vector.clear();
			vector = null;
		}
		return flag;
	}

	private void pasteToNoteProperty() throws TCException {
		TCProperty tcproperty = parentComp.getTCProperty("fnd0CustomNotes");
		if (tcproperty != null) {
			TCComponent atccomponent[] = tcproperty.getReferenceValueArray();
			ArrayList arraylist = new ArrayList();
			for (int i = 0; i < atccomponent.length; i++)
				arraylist.add(atccomponent[i]);

			for (int j = 0; j < childrenComps.length; j++)
				arraylist.add((TCComponent) childrenComps[j]);

			TCComponent atccomponent1[] = new TCComponent[arraylist.size()];
			atccomponent1 = (TCComponent[]) arraylist
					.toArray(new TCComponent[arraylist.size()]);
			tcproperty.setReferenceValueArray(atccomponent1);
		}
	}

	public AIFComponentContext[] getTargetContexts() {
		return contextArray;
	}

	public Map getPartialFailedTargets() {
		return Collections.EMPTY_MAP;
	}

	private TCComponentDataset excelDataset;
	private AIFComponentContext contextArray[];
	private InterfaceAIFComponent childrenComps[];
	private TCComponent parentComp;
	private String contextString;
	protected Object operationResultNew;
	protected boolean statusUse;
	private String cookieName;
	private boolean openAfterPasteFlag;
	public static final int relationNotFoundErrorCode = 38015;
	private static final Logger logger = Logger
			.getLogger(JFomPasteOperation.class);

}

/*
 * DECOMPILATION REPORT
 * 
 * Decompiled from:
 * F:\Teamcenter10.1.4Env\portal\plugins\com.teamcenter.rac.common_10000.1.0.jar
 * Total time: 566 ms Jad reported messages/errors: Exit status: 0 Caught
 * exceptions:
 */