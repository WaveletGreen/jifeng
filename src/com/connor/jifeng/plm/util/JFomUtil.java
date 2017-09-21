package com.connor.jifeng.plm.util;

public class JFomUtil {

	public static final String PROJ_QUERY = "cust_query_proj";

	/**************** 自定义的关于新建零部件的功能编号 *****/
	public static final int BGTZD_ID = 1001;
	public static final int WLDCB_ID = 1002;
	public static final int GGGJB_ID = 1003;
	public static final String TIME_FORMAT = "yyyy-MM-dd";
	public static final String TIME_FORMAT2 = "yyyyMMddHHmmssSSS";
	public static final String TIME_FORMAT4 = "yyyy-M-dd 23:59";
	public static final String TIME_FORMAT3 = "yyyy-M-dd 00:01";

	/**************** 自动升版 ***************************/
	public static final String JF3_BGQduixiang = "JF3_BGQduixiang";
	public static final String JF3_BGHduixiang = "JF3_BGHduixiang";

	/**************** 物料库存查询 ***********************/
	public static final int tableColNum = 4;
	public static final int JF3_WL_PROPS_COUNT = 4;
	public static final String JF3_WL_CODE_PROP = "jf3_WLh";// 物料号
	public static final String JF3_WL_COUNT_PROP = "jf3_WLkczl";// 物料库存总量
	public static final String JF3_WL_GG_PROP = "jf3_WLgg";// 物料库存规格
	public static final String JF3_WL_MC_PROP = "jf3_WLmc";// 物料名称

	/**************** 粘贴操作 **************************/
	public static final String JF3_PASTE_EXCEL_GRM = "TC_Attaches";
	public static final String JF3_PASTE_GRM = "JF3_FFshuju";
	public static final String JF3_PASTE_GRM2 = "JF3_GGtongzhi";
	public static final String JF3_PASTE_GRM3 = "JF3_GGduixiang";
	public static final String JF3_PASTE_GRM4 = "JF3_GGduixiang";
	public static final String JF3_PASTE_PICTURE_GRM = "IMAN_specification";
	public static final String JF3_PASTE_PARENT_TYPE = "JF3_SJFFJLRevision";// 数据发放记录
	public static final String JF3_PASTE_PARENT_TYPE2 = "JF3_GGSQPSDRevision";// 更改申请评审单版本
	public static final String JF3_PASTE_PARENT_TYPE3 = "JF3_GCGGTZD";// 更改申请评审单版本
	public static final String JF3_PASTE_PROP1 = "item_id";
	public static final String JF3_PASTE_PROP2 = "item_revision_id";
	public static final String JF3_PASTE_PROP3 = "object_name";
	public static final String JF3_PASTE_PROP4 = "date_released";
	public static final int JF3_PASTE_EXCEL_READ_INDEX = 3;
	public static final String[] JF3_PASTE_PROPS = { JF3_PASTE_PICTURE_GRM,
			JF3_PASTE_PROP3, JF3_PASTE_PROP1, JF3_PASTE_PROP2, JF3_PASTE_PROP4 };
	public static final String JF3_DATASET_TYPE = "MSExcel";
	public static final String JF3_DATASET_TYPE2 = "Image";

	/**************** 工程更改记录 *********************/

	// public static final String JF3_GGGGJLLOV1 = "JF3_GGFQF_LOV";
	// public static final String JF3_GGGGJLLOV2 = "JF3_GGFL_LOV";
	public static final String JF3_INPUTFILESTREAM = "工程更改记录.xls";
	public static final String JF3_GGGGJLREF1 = "Cust_jf3_ggpsd_map1"; // 发起方首选项
	public static final String JF3_GGGGJLREF2 = "Cust_jf3_ggpsd_map2"; // 分类首选项
	public static final String JF3_GGGGJLType = "JF3_GGSQPSDRevision"; // 更改申请评审单版本
	public static final String JF3_GCGGTZDTYPE = "JF3_GCGGTZDRevision"; // 工程更改通知单版本
	public static final String JF3_GGGG_MASTER_FORM = "IMAN_master_form_rev";
	public static final String JF3_GGGG_ALLWORKFLOWS = "fnd0AllWorkflows";// 关联的所有的流程
	public static final String JF3_GGGG_EPMTASK_ATTACHEMENTS = "attachments";// 根流程目标对象root_target_attachments
	public static final String JF3_GGGG_GGFQF = "jf3_GGFQF";// 发起方
	public static final String JF3_GGGG_PART_STATUS = "jf3_GGHLJZT"; // 零部件状态jf3_GGHLJZT
	public static final String JF3_GGGG_GGPSD_EXCEL = "TC_Attaches"; // 更改申请评审单下挂的EXCEL的关系

	public static final String JF3_GGTZ_GRM = "JF3_GGtongzhi"; // 更改通知 关系
	public static final String JF3_GGDX_GRM = "JF3_GGduixiang"; // 更改对象 关系
	public static final String JF3_GGTZ_GGH_GRM = "JF3_BGHduixiang"; // 工程更改单中存放更改后对象的关系
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

	// *****************配置查询相关**************
	public static final String JF3_QUERY_TYPE = "JF3_GGSQPSDRevision";
	public static final String JF3_QUERY_PROJECT_ID = "项目 ID";
	public static final String JF3_QUERY_ITEM_TYPE = "类型";
	public static final String JF3_QUERY_CREATE_DATE_AFT = "date_released_aft";
	public static final String JF3_QUERY_CREATE_DATE_BEF = "date_released_bef";
	public static final String JF3_QUERY_RELEASE_DATE_AFT = "creation_date_aft";
	public static final String JF3_QUERY_RELEASE_DATE_BEF = "creation_date_bef";
	public static final String JF3_QUERY_PROP_1 = "描述";
	public static final String JF3_QUERY_PROP_2 = "名称";
	public static final String JF3_QUERY_NAME = "cust_query_by_project";

	// 工程更改通知单EXCEL
	public static final String JF3_GGGGTZD_ECN = "ECN";// ECR编号
	public static final String JF3_GGGGTZD_BGQMS = "BGQ";// 变更前描述
	public static final String JF3_GGGGTZD_BGHMS = "BGH";// 变更后描述

	/**************** BOM_TO_BOM **********************/

	public static final String JF3_BOM_DRAWING_WL_GRM = "TC_Is_Represented_By"; // 图纸和物料关联的关系
	public static final String JF3_BOM_DRAWING_WL_CP = "JF3_CPRevision"; // 成品类型
	public static final String JF3_BOM_DRAWING_WL_BCP = "JF3_BCPRevision"; // 半成品类型
	public static final String JF3_BOM_DRAWING_WL_YCL = "JF3_YCLRevision"; // 原材料类型

	public static final String JF3_BOM_QUERY_NO = "bl_sequence_no";
	public static final String JF3_BOM_COUNT_NO = "bl_quantity";
	/**************** MATERIAL BOM STRACT EXPORT **********************/

	public static final String JF3_INDEX_STR = "√";

	public static final String JF3_EXPORT_BOM_EXCEL = "导出物料BOM.xlsx";
	public static final String JF3_EXPORT_BOM_PROP1 = "bl_item_item_id";// 物料号
	public static final String JF3_EXPORT_BOM_PROP4 = "bl_rev_object_name";// 物料名称
	public static final String JF3_EXPORT_BOM_PROP2 = "bl_line_object";// 得到版本对象
	public static final String JF3_EXPORT_BOM_PROP3 = "bl_quantity";// 数量

	public static final String JF3_EXPORT_REV_PROP1 = "IMAN_master_form_rev";// 通过版本对象得到版本主属性表单
	public static final String JF3_EXPORT_REV_PROP5 = "TC_Is_Represented_By";// 得到图纸

	public static final String JF3_EXPORT_REV_PROP2 = "jf3_khljh";// 客户零件号
	public static final String JF3_EXPORT_REV_PROP3 = "jf3_YCLgg";// 材料规格
	public static final String JF3_EXPORT_REV_PROP4 = "jf3_YCLClph";// 材料牌号

	public static final String JF3_DRAWING_REV_PROP1 = "item_id";// 图号
	public static final String JF3_DRAWING_REV_PROP2 = "IMAN_specification";// 图片
	public static final String JF3_DRAWING_REV_PROP3 = "IMAN_master_form_rev";// 版本主属性表单

	public static final String JF3_DRAWING_REV_PROP4 = "jf3_cl";// 材料名称
	public static final String JF3_DRAWING_REV_PROP5 = "jf3_clbz";// 材料标准
	public static final String JF3_DRAWING_REV_PROP6 = "jf3_zl";// 重量

	public static final String JF3_DRAWING_PIC_TYPE = "Image";// 存储图片的数据集类型

	// 取BOMLINE上面的属性
	public static final String[] JF3_BOMLINE_MSG = { JF3_EXPORT_BOM_PROP1,
			JF3_EXPORT_BOM_PROP4, JF3_EXPORT_BOM_PROP2, JF3_EXPORT_BOM_PROP3,
			"JF3_mjjz", "JF3_mjmz" };
	// 获取主属性表单
	public static final String[] JF3_REV_MSG = { JF3_EXPORT_REV_PROP1,
			JF3_EXPORT_REV_PROP5, "item_id", "object_name", "object_type" };
	// 获取主属性表单上面的属性
	public static final String[] JF3_REV_MASTER_MSG = { JF3_EXPORT_REV_PROP2,
			JF3_EXPORT_REV_PROP3, JF3_EXPORT_REV_PROP4, "jf3_gys", "jf3_gymc",
			"jf3_clbz" };
	// 获取图片和主属性表单
	public static final String[] JF3_DRAW_REV_MSG = { JF3_DRAWING_REV_PROP1,
			JF3_DRAWING_REV_PROP2, JF3_DRAWING_REV_PROP3 };
	// 获取主属性表单上面的属性
	public static final String[] JF3_DRAW_REV_MASTER_MSG = {
			JF3_DRAWING_REV_PROP4, JF3_DRAWING_REV_PROP5, JF3_DRAWING_REV_PROP6 };

}
