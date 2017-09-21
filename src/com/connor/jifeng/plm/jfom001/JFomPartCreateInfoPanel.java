package com.connor.jifeng.plm.jfom001;

import com.teamcenter.rac.commands.newitem.AbstractNewItemPanel;
import com.teamcenter.rac.commands.newitem.ItemInfoPanel;
import com.teamcenter.rac.commands.newpart.PartInfoPanel;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

import java.awt.Frame;

public class JFomPartCreateInfoPanel extends PartInfoPanel
{

    public JFomPartCreateInfoPanel(Frame frame, TCSession tcsession, AbstractNewItemPanel abstractnewitempanel)
    {
        super(frame, tcsession, abstractnewitempanel, true);
        MessageBox.post("222222","info",MessageBox.INFORMATION);
    }

    public JFomPartCreateInfoPanel(Frame frame, TCSession tcsession, AbstractNewItemPanel abstractnewitempanel, boolean flag)
    {
        super(frame, tcsession, abstractnewitempanel, flag);
        MessageBox.post("222222","info",MessageBox.INFORMATION);
    }
}


/*
	DECOMPILATION REPORT

	Decompiled from: D:\PLM\Siemens\Teamcenter10\portal\plugins\com.teamcenter.rac.common_10000.1.0.jar
	Total time: 10 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/