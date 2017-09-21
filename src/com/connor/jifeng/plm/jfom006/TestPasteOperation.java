package com.connor.jifeng.plm.jfom006;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.commands.paste.PasteOperation;
import com.teamcenter.rac.common.IMultiTarget;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCException;

public class TestPasteOperation extends PasteOperation implements IMultiTarget {

	public TestPasteOperation(
			AbstractAIFUIApplication abstractaifuiapplication,
			AIFComponentContext aifcomponentcontext) {
		super(abstractaifuiapplication, aifcomponentcontext);
		// TODO Auto-generated constructor stub
	}

	public TestPasteOperation(
			AbstractAIFUIApplication abstractaifuiapplication,
			AIFComponentContext[] aaifcomponentcontext) {
		super(abstractaifuiapplication, aaifcomponentcontext);
		// TODO Auto-generated constructor stub
	}

	public TestPasteOperation(
			AbstractAIFUIApplication abstractaifuiapplication,
			TCComponent tccomponent,
			InterfaceAIFComponent interfaceaifcomponent, String s) {
		super(abstractaifuiapplication, tccomponent, interfaceaifcomponent, s);
		// TODO Auto-generated constructor stub
	}

	public TestPasteOperation(
			AbstractAIFUIApplication abstractaifuiapplication,
			TCComponent tccomponent,
			InterfaceAIFComponent[] ainterfaceaifcomponent, String s,
			boolean flag) {
		super(abstractaifuiapplication, tccomponent, ainterfaceaifcomponent, s,
				flag);
		// TODO Auto-generated constructor stub
	}

	public TestPasteOperation(
			AbstractAIFUIApplication abstractaifuiapplication,
			TCComponent tccomponent,
			InterfaceAIFComponent[] ainterfaceaifcomponent, String s) {
		super(abstractaifuiapplication, tccomponent, ainterfaceaifcomponent, s);
		// TODO Auto-generated constructor stub
	}

	public TestPasteOperation(AIFComponentContext aifcomponentcontext,
			boolean flag) {
		super(aifcomponentcontext, flag);
		// TODO Auto-generated constructor stub
	}

	public TestPasteOperation(AIFComponentContext aifcomponentcontext) {
		super(aifcomponentcontext);
		// TODO Auto-generated constructor stub
	}

	public TestPasteOperation(AIFComponentContext[] aaifcomponentcontext) {
		super(aaifcomponentcontext);
		// TODO Auto-generated constructor stub
	}

	public TestPasteOperation(TCComponent tccomponent,
			InterfaceAIFComponent[] ainterfaceaifcomponent, String s,
			Object obj, boolean flag, boolean flag1) {
		super(tccomponent, ainterfaceaifcomponent, s, obj, flag, flag1);
		// TODO Auto-generated constructor stub
	}

	public TestPasteOperation(TCComponent tccomponent,
			InterfaceAIFComponent[] ainterfaceaifcomponent, String s,
			Object obj, boolean flag) {
		super(tccomponent, ainterfaceaifcomponent, s, obj, flag);
		// TODO Auto-generated constructor stub
	}

	public TestPasteOperation(TCComponent tccomponent,
			InterfaceAIFComponent[] ainterfaceaifcomponent, String s) {
		super(tccomponent, ainterfaceaifcomponent, s);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executeOperation() throws TCException {
		// TODO Auto-generated method stub
		super.executeOperation();
	}

}
