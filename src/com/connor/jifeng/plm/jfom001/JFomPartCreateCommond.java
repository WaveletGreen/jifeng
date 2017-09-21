package com.connor.jifeng.plm.jfom001;

import java.awt.Frame;

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.commands.newpart.NewPartCommand;

public class JFomPartCreateCommond extends NewPartCommand{

	public JFomPartCreateCommond(Frame frame, AbstractAIFApplication abstractaifapplication, String s, String s1,
			boolean flag) {
		super(frame, abstractaifapplication, s, s1, flag);
		this.objectType = "Part";
		this.defaultSelectionObjectType="Part";
		// TODO Auto-generated constructor stub
	}

    public JFomPartCreateCommond(Frame frame, AbstractAIFApplication abstractaifapplication)
    {
        super(frame, abstractaifapplication);
    }

    public JFomPartCreateCommond(Frame frame, InterfaceAIFComponent ainterfaceaifcomponent[])
    {
        super(frame, ainterfaceaifcomponent);
    }

    public JFomPartCreateCommond(Frame frame, AbstractAIFApplication abstractaifapplication, String s, String s1, boolean flag, InterfaceAIFComponent ainterfaceaifcomponent[])
    {
        super(frame, abstractaifapplication, s, s1, flag, ainterfaceaifcomponent);
    }

	
}
