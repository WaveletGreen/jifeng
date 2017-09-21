package com.connor.jifeng.plm.util;

import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponentContextList;
import com.teamcenter.rac.kernel.TCComponentQuery;
import com.teamcenter.rac.kernel.TCComponentQueryType;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.kernel.TCTextService;

public class SearchUtility {

	public static InterfaceAIFComponent[] searchComponentsCollection(
			TCSession session, String searchName, String[] keys, String[] values) {
		// ��Ϣ���
		InterfaceAIFComponent[] result = new InterfaceAIFComponent[0];

		try {
			TCTextService textService = session.getTextService();
			TCComponentQueryType querytype = (TCComponentQueryType) session
					.getTypeComponent("ImanQuery");
			TCComponentQuery query = (TCComponentQuery) querytype
					.find(searchName);
			if (query == null) {
				System.out.println("ͨ����ѯ������" + searchName + "���Ҳ�����");
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
			TCComponentContextList list = query.getExecuteResultsList(as, as1);
			if (list != null) {
				int count = list.getListCount();
				result = new InterfaceAIFComponent[count];

				for (int i = 0; i < count; i++) {
					result[i] = list.get(i).getComponent();
				}
			}
		} catch (TCException e) {
			// MessageBox.post(AIFDesktop.getActiveDesktop().getShell(),
			// "ͨ����ѯ������"
			// + searchName + "��ѯ��������.", "����", 1);
			System.out.println("ͨ����ѯ������" + searchName + "��ѯ��������.");
			return null;
		}

		return result;
	}
}
