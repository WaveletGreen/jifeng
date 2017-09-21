package com.connor.jifeng.plm.util;

public class JFomUtil {

	public static final String PROJ_QUERY = "cust_query_proj";

	/**************** �Զ���Ĺ����½��㲿���Ĺ��ܱ�� *****/
	public static final int BGTZD_ID = 1001;
	public static final int WLDCB_ID = 1002;
	public static final int GGGJB_ID = 1003;
	public static final String TIME_FORMAT = "yyyy-MM-dd";
	public static final String TIME_FORMAT2 = "yyyyMMddHHmmssSSS";
	public static final String TIME_FORMAT4 = "yyyy-M-dd 23:59";
	public static final String TIME_FORMAT3 = "yyyy-M-dd 00:01";

	/**************** �Զ����� ***************************/
	public static final String JF3_BGQduixiang = "JF3_BGQduixiang";
	public static final String JF3_BGHduixiang = "JF3_BGHduixiang";

	/**************** ���Ͽ���ѯ ***********************/
	public static final int tableColNum = 4;
	public static final int JF3_WL_PROPS_COUNT = 4;
	public static final String JF3_WL_CODE_PROP = "jf3_WLh";// ���Ϻ�
	public static final String JF3_WL_COUNT_PROP = "jf3_WLkczl";// ���Ͽ������
	public static final String JF3_WL_GG_PROP = "jf3_WLgg";// ���Ͽ����
	public static final String JF3_WL_MC_PROP = "jf3_WLmc";// ��������

	/**************** ճ������ **************************/
	public static final String JF3_PASTE_EXCEL_GRM = "TC_Attaches";
	public static final String JF3_PASTE_GRM = "JF3_FFshuju";
	public static final String JF3_PASTE_GRM2 = "JF3_GGtongzhi";
	public static final String JF3_PASTE_GRM3 = "JF3_GGduixiang";
	public static final String JF3_PASTE_GRM4 = "JF3_GGduixiang";
	public static final String JF3_PASTE_PICTURE_GRM = "IMAN_specification";
	public static final String JF3_PASTE_PARENT_TYPE = "JF3_SJFFJLRevision";// ���ݷ��ż�¼
	public static final String JF3_PASTE_PARENT_TYPE2 = "JF3_GGSQPSDRevision";// �����������󵥰汾
	public static final String JF3_PASTE_PARENT_TYPE3 = "JF3_GCGGTZD";// �����������󵥰汾
	public static final String JF3_PASTE_PROP1 = "item_id";
	public static final String JF3_PASTE_PROP2 = "item_revision_id";
	public static final String JF3_PASTE_PROP3 = "object_name";
	public static final String JF3_PASTE_PROP4 = "date_released";
	public static final int JF3_PASTE_EXCEL_READ_INDEX = 3;
	public static final String[] JF3_PASTE_PROPS = { JF3_PASTE_PICTURE_GRM,
			JF3_PASTE_PROP3, JF3_PASTE_PROP1, JF3_PASTE_PROP2, JF3_PASTE_PROP4 };
	public static final String JF3_DATASET_TYPE = "MSExcel";
	public static final String JF3_DATASET_TYPE2 = "Image";

	/**************** ���̸��ļ�¼ *********************/

	// public static final String JF3_GGGGJLLOV1 = "JF3_GGFQF_LOV";
	// public static final String JF3_GGGGJLLOV2 = "JF3_GGFL_LOV";
	public static final String JF3_INPUTFILESTREAM = "���̸��ļ�¼.xls";
	public static final String JF3_GGGGJLREF1 = "Cust_jf3_ggpsd_map1"; // ������ѡ��
	public static final String JF3_GGGGJLREF2 = "Cust_jf3_ggpsd_map2"; // ������ѡ��
	public static final String JF3_GGGGJLType = "JF3_GGSQPSDRevision"; // �����������󵥰汾
	public static final String JF3_GCGGTZDTYPE = "JF3_GCGGTZDRevision"; // ���̸���֪ͨ���汾
	public static final String JF3_GGGG_MASTER_FORM = "IMAN_master_form_rev";
	public static final String JF3_GGGG_ALLWORKFLOWS = "fnd0AllWorkflows";// ���������е�����
	public static final String JF3_GGGG_EPMTASK_ATTACHEMENTS = "attachments";// ������Ŀ�����root_target_attachments
	public static final String JF3_GGGG_GGFQF = "jf3_GGFQF";// ����
	public static final String JF3_GGGG_PART_STATUS = "jf3_GGHLJZT"; // �㲿��״̬jf3_GGHLJZT
	public static final String JF3_GGGG_GGPSD_EXCEL = "TC_Attaches"; // �������������¹ҵ�EXCEL�Ĺ�ϵ

	public static final String JF3_GGTZ_GRM = "JF3_GGtongzhi"; // ����֪ͨ ��ϵ
	public static final String JF3_GGDX_GRM = "JF3_GGduixiang"; // ���Ķ��� ��ϵ
	public static final String JF3_GGTZ_GGH_GRM = "JF3_BGHduixiang"; // ���̸��ĵ��д�Ÿ��ĺ����Ĺ�ϵ
	public static final String JF3_GGTZD_PROP_ITEM_ID = "item_id";
	public static final String JF3_GGTZD_PROP_DATE_RELEASED = "date_released";
	public static final String JF3_GGTZD_EXCEL = "TC_Attaches";
	// public static final String JF3_GGGGJLProp1 = "JF3_GGFQF";
	// public static final String JF3_GGGGJLProp2 = "JF3_GGFL";
	// public static final String JF3_PROJECT_NO = "item_id";
	// public static final String JF3_START_DATE = "start_date";
	// public static final String JF3_CLOSE_DATE = "close_date";

	// *******************************
	public static final String JF3_GGGG_GGTZ_GRM = "IMAN_reference";
	public static final String JF3_GGGG_GGTZ_GG_GRM = "IMAN_reference";
	public static final String JF3_GGGG_CREATEION_DATE = "creation_date";
	public static final String JF3_GGGG_RELEASED_DATE = "released_date";

	// *****************���ò�ѯ���**************
	public static final String JF3_QUERY_TYPE = "JF3_GGSQPSDRevision";
	public static final String JF3_QUERY_PROJECT_ID = "��Ŀ ID";
	public static final String JF3_QUERY_ITEM_TYPE = "����";
	public static final String JF3_QUERY_CREATE_DATE_AFT = "date_released_aft";
	public static final String JF3_QUERY_CREATE_DATE_BEF = "date_released_bef";
	public static final String JF3_QUERY_RELEASE_DATE_AFT = "creation_date_aft";
	public static final String JF3_QUERY_RELEASE_DATE_BEF = "creation_date_bef";
	public static final String JF3_QUERY_PROP_1 = "����";
	public static final String JF3_QUERY_PROP_2 = "����";
	public static final String JF3_QUERY_NAME = "cust_query_by_project";

	// ���̸���֪ͨ��EXCEL
	public static final String JF3_GGGGTZD_ECN = "ECN";// ECR���
	public static final String JF3_GGGGTZD_BGQMS = "BGQ";// ���ǰ����
	public static final String JF3_GGGGTZD_BGHMS = "BGH";// ���������

	/**************** BOM_TO_BOM **********************/

	public static final String JF3_BOM_DRAWING_WL_GRM = "TC_Is_Represented_By"; // ͼֽ�����Ϲ����Ĺ�ϵ
	public static final String JF3_BOM_DRAWING_WL_CP = "JF3_CPRevision"; // ��Ʒ����
	public static final String JF3_BOM_DRAWING_WL_BCP = "JF3_BCPRevision"; // ���Ʒ����
	public static final String JF3_BOM_DRAWING_WL_YCL = "JF3_YCLRevision"; // ԭ��������

	public static final String JF3_BOM_QUERY_NO = "bl_sequence_no";
	public static final String JF3_BOM_COUNT_NO = "bl_quantity";
	/**************** MATERIAL BOM STRACT EXPORT **********************/

	public static final String JF3_INDEX_STR = "��";

	public static final String JF3_EXPORT_BOM_EXCEL = "��������BOM.xlsx";
	public static final String JF3_EXPORT_BOM_PROP1 = "bl_item_item_id";// ���Ϻ�
	public static final String JF3_EXPORT_BOM_PROP4 = "bl_rev_object_name";// ��������
	public static final String JF3_EXPORT_BOM_PROP2 = "bl_line_object";// �õ��汾����
	public static final String JF3_EXPORT_BOM_PROP3 = "bl_quantity";// ����

	public static final String JF3_EXPORT_REV_PROP1 = "IMAN_master_form_rev";// ͨ���汾����õ��汾�����Ա�
	public static final String JF3_EXPORT_REV_PROP5 = "TC_Is_Represented_By";// �õ�ͼֽ

	public static final String JF3_EXPORT_REV_PROP2 = "jf3_khljh";// �ͻ������
	public static final String JF3_EXPORT_REV_PROP3 = "jf3_YCLgg";// ���Ϲ��
	public static final String JF3_EXPORT_REV_PROP4 = "jf3_YCLClph";// �����ƺ�

	public static final String JF3_DRAWING_REV_PROP1 = "item_id";// ͼ��
	public static final String JF3_DRAWING_REV_PROP2 = "IMAN_specification";// ͼƬ
	public static final String JF3_DRAWING_REV_PROP3 = "IMAN_master_form_rev";// �汾�����Ա�

	public static final String JF3_DRAWING_REV_PROP4 = "jf3_cl";// ��������
	public static final String JF3_DRAWING_REV_PROP5 = "jf3_clbz";// ���ϱ�׼
	public static final String JF3_DRAWING_REV_PROP6 = "jf3_zl";// ����

	public static final String JF3_DRAWING_PIC_TYPE = "Image";// �洢ͼƬ�����ݼ�����

	// ȡBOMLINE���������
	public static final String[] JF3_BOMLINE_MSG = { JF3_EXPORT_BOM_PROP1,
			JF3_EXPORT_BOM_PROP4, JF3_EXPORT_BOM_PROP2, JF3_EXPORT_BOM_PROP3,
			"JF3_mjjz", "JF3_mjmz" };
	// ��ȡ�����Ա�
	public static final String[] JF3_REV_MSG = { JF3_EXPORT_REV_PROP1,
			JF3_EXPORT_REV_PROP5, "item_id", "object_name", "object_type" };
	// ��ȡ�����Ա����������
	public static final String[] JF3_REV_MASTER_MSG = { JF3_EXPORT_REV_PROP2,
			JF3_EXPORT_REV_PROP3, JF3_EXPORT_REV_PROP4, "jf3_gys", "jf3_gymc",
			"jf3_clbz" };
	// ��ȡͼƬ�������Ա�
	public static final String[] JF3_DRAW_REV_MSG = { JF3_DRAWING_REV_PROP1,
			JF3_DRAWING_REV_PROP2, JF3_DRAWING_REV_PROP3 };
	// ��ȡ�����Ա����������
	public static final String[] JF3_DRAW_REV_MASTER_MSG = {
			JF3_DRAWING_REV_PROP4, JF3_DRAWING_REV_PROP5, JF3_DRAWING_REV_PROP6 };

}
