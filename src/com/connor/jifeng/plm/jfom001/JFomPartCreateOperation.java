package com.connor.jifeng.plm.jfom001;

import com.teamcenter.rac.commands.newpart.NewPartOperation;

public class JFomPartCreateOperation extends NewPartOperation
{

    public JFomPartCreateOperation()
    {
    }

    public JFomPartCreateOperation(JFomPartCreateCommond newpartcommand)
    {
        super(newpartcommand);
    }

    public JFomPartCreateOperation(JFomPartCreateDialog newpartdialog)
    {
        super(newpartdialog);
    }
}
