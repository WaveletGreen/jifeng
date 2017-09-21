package com.connor.jifeng.plm.jfom015;

import java.io.InputStream;

import com.teamcenter.rac.aif.AbstractAIFOperation;
import com.teamcenter.rac.kernel.TCComponentProject;
import com.teamcenter.rac.kernel.TCSession;

public class JF3ExportApqpDocumentReportOperation extends AbstractAIFOperation {

	public JF3ExportApqpDocumentReportOperation(TCComponentProject project,
			TCSession sessioln) {

	}

	@Override
	public void executeOperation() throws Exception {
		// TODO Auto-generated method stub

		InputStream ins = JF3ExportApqpDocumentReportOperation.class
				.getResourceAsStream("APQP资料清单点检表.xlsx");

	}

}
