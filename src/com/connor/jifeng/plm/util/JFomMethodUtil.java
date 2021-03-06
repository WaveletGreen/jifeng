package com.connor.jifeng.plm.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentBOMLine;
import com.teamcenter.rac.kernel.TCComponentBOMViewRevision;
import com.teamcenter.rac.kernel.TCComponentBOMWindow;
import com.teamcenter.rac.kernel.TCComponentBOMWindowType;
import com.teamcenter.rac.kernel.TCComponentContextList;
import com.teamcenter.rac.kernel.TCComponentDataset;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentQuery;
import com.teamcenter.rac.kernel.TCComponentQueryType;
import com.teamcenter.rac.kernel.TCComponentTcFile;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCPreferenceService;
import com.teamcenter.rac.kernel.TCQueryClause;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.kernel.TCTextService;
import com.teamcenter.rac.kernel.TCTypeService;
import com.teamcenter.rac.util.FileUtility;
import com.teamcenter.rac.util.MessageBox;

public class JFomMethodUtil {

	public static TCPreferenceService service;
	public static TCSession session;

	static {
		if (session == null) {
			session = (TCSession) (AIFUtility.getCurrentApplication()
					.getSession());
		}
		if (service == null)
			service = session.getPreferenceService();
	}

	/**
	 * 获取多值得首选项
	 * 
	 * @param prefName
	 * @return
	 */
	public static String[] getPrefStrArray(String prefName) {
		String[] strs = service.getStringArray(
				TCPreferenceService.TC_preference_site, prefName);
		if (strs == null) {
			strs = new String[] { "" };
		}
		return strs;
	}

	/**
	 * 肯定没写完
	 * @param rev
	 * @return
	 */
	public static TCComponentBOMLine getTopline(TCComponentItemRevision rev) {
		TCComponentBOMLine topLine = null;

		return topLine;
	}

	public static List<Integer> getPrefIntArray(String prefName) {
		List<Integer> intList = new ArrayList<>();
		String[] strs = service.getStringArray(
				TCPreferenceService.TC_preference_site, prefName);
		if (strs != null) {
			for (String str : strs) {
				try {
					int index = Integer.parseInt(str.trim());
					intList.add(index);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}
		}
		return intList;
	}

	public static HashMap<String, String> getPrefStrArrayReturn(
			String prefName, String split) {
		HashMap<String, String> map = new HashMap<String, String>();
		// map.put("", " ");
		String[] strs = service.getStringArray(
				TCPreferenceService.TC_preference_site, prefName);
		if (strs != null) {
			for (String str : strs) {
				String temp[] = str.split(split);
				map.put(temp[1], temp[0]);
			}
		}
		return map;
	}

	public static HashMap<String, String> getPrefStrArray(String prefName,
			String split) {
		HashMap<String, String> map = new HashMap<String, String>();

		// map.put("", " ");

		String[] strs = service.getStringArray(
				TCPreferenceService.TC_preference_site, prefName);
		if (strs != null) {
			for (String str : strs) {
				String temp[] = str.split(split);
				map.put(temp[0], temp[1]);
			}
		}
		return map;
	}

	/**
	 * 获取单值得首选项
	 * 
	 * @param prefName
	 * @return
	 */
	public static String getPrefStr(String prefName) {
		String str = service.getString(TCPreferenceService.TC_preference_site,
				prefName);
		if (str == null) {
			str = new String("");
		}
		return str;
	}

	/**
	 * 通过查询,查找符合条件的版本
	 * 
	 * @param session
	 * @param searchName
	 * @param keys
	 * @param values
	 * @return
	 */
	public static InterfaceAIFComponent[] searchComponentsCollection(
			TCSession session, String searchName, String[] keys, String[] values) {
		// 信息输出
		InterfaceAIFComponent[] result = new InterfaceAIFComponent[0];

		try {
			// 得到查询的服务
			TCTextService textService = session.getTextService();
			// 获取querytype
			// 参数固定写法ImanQuery
			TCComponentQueryType querytype = (TCComponentQueryType) session
					.getTypeComponent("ImanQuery");
			// 通过querytype找到searchName的查询构建器
			TCComponentQuery query = (TCComponentQuery) querytype
					.find(searchName);
			if (query == null) {
				// MessageBox.post("通过查询构建器" + searchName + "不存在", "错误", 1);
				System.out.println("通过查询构建器" + searchName + "不存在");
				return null;
			}
			querytype.clearCache();
			String[] as = new String[keys.length];
			for (int i = 0; i < keys.length; i++) {
				as[i] = textService.getTextValue(keys[i]);
			}

			String[] as1 = new String[values.length];
			for (int i = 0; i < values.length; i++) {
				as1[i] = textService.getTextValue(values[i]);
			}

			query.clearCache();
			/**通过查询得到查询的信息描述*/
			TCQueryClause[] clauses = query.describe();

			// 遍历查询信息，检查查询条目是否正确
			for (int i = 0; i < clauses.length; i++) {
				// 得到查询属性名称
				clauses[i].getAttributeName();
				// 得到用户条目
				clauses[i].getUserEntryName();
				// 得到用户本地化条目
				clauses[i].getUserEntryNameDisplay();

			}

			TCComponentContextList list = query.getExecuteResultsList(as, as1);
			if (list != null) {
				int count = list.getListCount();
				result = new InterfaceAIFComponent[count];

				for (int i = 0; i < count; i++) {
					result[i] = list.get(i).getComponent();
				}
			}
		} catch (TCException e) {
			e.printStackTrace();
			MessageBox.post("通过查询构建器" + searchName + "查询发生错误.", "错误", 1);
		}

		return result;
	}

	/***************** BOM相关 ************成品 *********************************/

	public static TCComponentBOMWindow getBomwindow(TCComponentItemRevision rev) {
		TCComponentBOMWindow window = null;
		try {
			TCTypeService service = session.getTypeService();
			// TCComponentBOMViewRevisionType bvr_type =
			// (TCComponentBOMViewRevisionType)
			// service.getTypeComponent("PSBOMViewRevision");
			// 先获取windowtype
			TCComponentBOMWindowType winType = (TCComponentBOMWindowType) service
					.getTypeComponent("BOMWindow");
			// 通过type创建一个空的window
			window = winType.create(null);
			// 将我们的对象和版本发送到空的window
			window.setWindowTopLine(rev.getItem(), rev, null, null);

		} catch (TCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return window;
	}

	/**
	 * 获取顶层的BOMLINE
	 * 
	 * @param rev
	 * @return
	 */
	public static TCComponentBOMLine getTopLineByRev(TCComponentItemRevision rev) {
		TCComponentBOMLine line = null;
		try {
			TCTypeService service = session.getTypeService();
			// TCComponentBOMViewRevisionType bvr_type =
			// (TCComponentBOMViewRevisionType)
			// service.getTypeComponent("PSBOMViewRevision");

			TCComponentBOMWindowType winType = (TCComponentBOMWindowType) service
					.getTypeComponent("BOMWindow");
			TCComponentBOMWindow window = winType.create(null);
			// 通过将对象和版本发送到bomwindow可以获取顶层的bomline
			line = window.setWindowTopLine(rev.getItem(), rev, null, null);
			// 获取bomline下的子line
			// AIFComponentContext[] childS = line.getChildren();
			// for (AIFComponentContext context : childS) {
			// // 获取到context的对象
			// TCComponentBOMLine childLine = (TCComponentBOMLine) context
			// .getComponent();
			// }

		} catch (TCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return line;
	}

	/**
	 * 通过递归函数获取到整个bom的结构
	 * 
	 * 又肯定没写完
	 * 
	 * @param line
	 * @throws TCException
	 */
	public void getBomStruct(TCComponentBOMLine line) throws TCException {
		// 获取子
		AIFComponentContext[] childS = line.getChildren();
		for (AIFComponentContext context : childS) {
			// 获取到context的对象
			TCComponentBOMLine childLine = (TCComponentBOMLine) context
					.getComponent();
			//你只是获取了BOM结构，但谁用啊？
			getBomStruct(childLine);
		}
	}

	/**
	 * 总结************************************************** 操作一个对象的BOM的四个步骤
	 * 1、获取到 TCComponentBOMWindowType 可以通过session获取 2、通过TCComponentBOMWindowType
	 * 来create一个空的window 3、将我们的对象发送到 window，可以获取到对象的的bom结构的顶层的bomline
	 * 4、通过顶层的bomline，getChildren递归得到BOM的整个结构的信息
	 ********************************************************/

	/**
	 * if (window != null) { TCComponentBOMViewRevision bvr = window.askBvr();
	 * if (bvr != null) { TCComponent[] status = bvr
	 * .getReferenceListProperty("release_status_list"); if (status != null &&
	 * status.length != 0) { isReleased = true; } }
	 */
	public static TCComponentBOMLine getReleasedTopLineByRev(
			TCComponentItemRevision rev) {
		TCComponentBOMLine line = null;
		try {
			TCTypeService service = session.getTypeService();
			// TCComponentBOMViewRevisionType bvr_type =
			// (TCComponentBOMViewRevisionType)
			// service.getTypeComponent("PSBOMViewRevision");

			TCComponentBOMWindowType winType = (TCComponentBOMWindowType) service
					.getTypeComponent("BOMWindow");
			TCComponentBOMWindow window = winType.create(null);
			if (window != null) {
				TCComponentBOMViewRevision bvr = window.askBvr();
				if (bvr != null) {
					TCComponent[] status = bvr
							.getReferenceListProperty("release_status_list");
					if (status == null || status.length == 0) {
						return null;
					}
				}
			} else {
				return null;
			}
			//放错地方了？
			line = window.setWindowTopLine(rev.getItem(), rev, null, null);

		} catch (TCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return line;
	}

	/**
	 * 搭建bom
	 * 
	 * @param selectIndex
	 * @throws TCException
	 */
	public static void createBom(TCComponentItemRevision parentRev,
			List<TCComponentItemRevision> childRevList,
			List<String> queryNoList, List<String> countNoList)
			throws TCException {
		if (childRevList == null || childRevList.size() == 0) {
			return;
		}
		if (parentRev == null)
			return;
		String[] setProps = { JFomUtil.JF3_BOM_QUERY_NO,
				JFomUtil.JF3_BOM_COUNT_NO };
		TCTypeService service = session.getTypeService();
		TCComponentBOMWindowType winType = (TCComponentBOMWindowType) service
				.getTypeComponent("BOMWindow");
		//创建一个新的空BOM用于存放新结构
		TCComponentBOMWindow view = winType.create(null);
		TCComponentBOMLine line = view.setWindowTopLine(parentRev.getItem(),
				parentRev, null, null);
		AIFComponentContext[] childrenContext = line.getChildren();

		view.lock();
		// 移出所有的子bomline
		if (childrenContext.length != 0) {
			for (AIFComponentContext child : childrenContext) {
				line.lock();
				// 移除旧的子line
				line.remove("", (TCComponent) child.getComponent());
				line.save();
				line.unlock();
			}
			// return;
		}
		for (int i = 0; i < childRevList.size(); i++) {
			TCComponentItemRevision rev = childRevList.get(i);
			line.lock();
			// 通过line的add方法向line下添加一个新的子line，返回值是新的子line

			TCComponentBOMLine childBomLine = line.add(rev.getItem(), rev,
					null, false, "");
			line.save();
			line.unlock();
			childBomLine.lock();

			childBomLine.setProperties(setProps,
					new String[] { queryNoList.get(i), countNoList.get(i) });
			childBomLine.save();
			childBomLine.unlock();

		}
		// 搭建bom，或者对bomline进行设置属性操作的时候，不允许关闭window
		// 只有通过window进行save的时候，才能将bomline上设置的属性保存进去；否则设置到
		// bomline上的新属性不能成功的保存到数据库
		view.save();
		view.unlock();
		view.close();

	}

	/**
	 * 下载文件
	 * 
	 * @param comps
	 * @return
	 * @throws TCException
	 * @throws IOException
	 */
	public static String downLoadFile(TCComponent comp) {
		if (comp == null) {
			return "";
		}
		String value = "";
		String tempPath = System.getenv("TEMP");
		// MessageBox.post(" tempPath =
		// "+tempPath,"INFO",MessageBox.INFORMATION);
		if (tempPath == null) {
			tempPath = "";
		} else if (!tempPath.endsWith("\\")) {
			tempPath = tempPath + "\\";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		// for(TCComponent comp : comps){
		try {
			if (comp instanceof TCComponentDataset) {
				TCComponentTcFile[] tcFiles = ((TCComponentDataset) comp)
						.getTcFiles();
				File file = null;
				if (tcFiles != null && tcFiles.length != 0) {
					file = tcFiles[0].getFmsFile();
					String fileName = file.getName();
					String fileDix = fileName.substring(
							fileName.lastIndexOf("."), fileName.length());
					fileName = tempPath + sdf.format(new Date()) + fileDix;
					File dirFile = new File(fileName);
					FileUtility.copyFile(file, dirFile);

					return fileName;
				}
			}
		} catch (TCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// }
		return value;
	}

	/**
	 * 检查对象是否已经发布
	 * 
	 * @param comp
	 * @return
	 * @throws TCException
	 */
	public static boolean isCompReleased(TCComponent comp) throws TCException {
		TCComponent[] comps = comp.getRelatedComponents("release_status_list");
		if (comps != null && comps.length > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 是否有BOM
	 * 
	 * @param rev
	 * @return
	 */
	public static boolean isRevHadBom(TCComponentItemRevision rev) {
		boolean isHad = false;
		if (rev != null) {
			TCComponentBOMWindow window = null;
			try {
				TCTypeService service = session.getTypeService();
				TCComponentBOMWindowType winType = (TCComponentBOMWindowType) service
						.getTypeComponent("BOMWindow");
				window = winType.create(null);
				TCComponentBOMLine topLine = window.setWindowTopLine(
						rev.getItem(), rev, null, null);
				if (topLine != null) {
					if (topLine.getChildren().length > 0) {
						isHad = true;
					}
				}
				window.close();
			} catch (TCException e) {
				e.printStackTrace();
			}
		}
		return isHad;
	}

	/**
	 * 判断已经发布的对象是否有BOM
	 * 
	 * @param rev
	 * @return
	 */
	public static boolean isRevBomReleased(TCComponentItemRevision rev) {
		boolean isReleased = false;
		try {
			if (isCompReleased(rev)) {
				TCComponentBOMWindow window = getBomwindow(rev);
				// TCComponentBOMLine line = window.getTopBOMLine();
				// if(line.getChildren().length!=0){
				// isReleased = true;
				// }
				if (window != null) {
					TCComponentBOMViewRevision bvr = window.askBvr();

					if (bvr != null) {
						TCComponent[] status = bvr
								.getReferenceListProperty("release_status_list");
						if (status != null && status.length != 0) {
							isReleased = true;
						}
					}
					window.close();
				}
			}
		} catch (TCException e) {
			e.printStackTrace();
			isReleased = true;
		}
		return isReleased;

	}

	/**
	 * 获取所有的选择的对象的版本
	 * 
	 * @param comps
	 * @return
	 */
	public static List<TCComponentItemRevision> getAllRevComp(
			InterfaceAIFComponent comps[]) {
		List<TCComponentItemRevision> revList = new ArrayList<TCComponentItemRevision>();
		for (InterfaceAIFComponent comp : comps) {
			if (comp instanceof TCComponentItemRevision) {
				// revList.add((TCComponentItemRevision) comp);
				try {
					revList.add(((TCComponentItemRevision) comp).getItem()
							.getLatestItemRevision());
				} catch (TCException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (comp instanceof TCComponentItem) {
				try {
					revList.add(((TCComponentItem) comp)
							.getLatestItemRevision());
				} catch (TCException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				return null;
			}
		}
		return revList;
	}

	/**
	 * 获取所有的选择的对象的版本--->只是获取最新发布的版本吧？当我一个Item又多个版本呢？
	 * 
	 * @param comps
	 * @return
	 */
	public static List<TCComponentItemRevision> getAllRevComp2(
			InterfaceAIFComponent comps[]) {
		List<TCComponentItemRevision> revList = new ArrayList<TCComponentItemRevision>();
		for (InterfaceAIFComponent comp : comps) {
			if (comp instanceof TCComponentItemRevision) {
				// revList.add((TCComponentItemRevision) comp);
				try {
					// ((TCComponentItemRevision) comp).getItem().get
					revList.add(((TCComponentItemRevision) comp).getItem()
							.getLatestItemRevision());
				} catch (TCException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (comp instanceof TCComponentItem) {
				try {
					//你这里只是获取最新版本而已，并没有获取所有的版本，应该是getChildren()才对吧
					revList.add(((TCComponentItem) comp)
							.getLatestItemRevision());
				} catch (TCException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				return null;
			}
		}
		return revList;
	}

}
