package com.connor.jifeng.plm.jfom001;

import com.teamcenter.rac.commands.newitem.ItemTypePanel;
import com.teamcenter.rac.commands.newpart.PartHelper;
import com.teamcenter.rac.commands.newpart.PartTypePanel;
import com.teamcenter.rac.common.TCUtilities;
import com.teamcenter.rac.common.create.DisplayTypeInfo;
import com.teamcenter.rac.kernel.TCSession;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;

// Referenced classes of package com.teamcenter.rac.commands.newpart:
//            PartHelper, NewPartPanel

public class JFomPartCreateTypePanel extends PartTypePanel
{

    public JFomPartCreateTypePanel(java.awt.Frame frame, TCSession tcsession, JFomPartCreatePanel newpartpanel)
    {
        super(frame, tcsession, newpartpanel);
    }

    public JFomPartCreateTypePanel(java.awt.Frame frame, TCSession tcsession, JFomPartCreatePanel newpartpanel, boolean flag)
    {
        super(frame, tcsession, newpartpanel, flag);
    }

    public void loadItemTypes()
    {
        List list = null;
        list = TCUtilities.getDisplayableTypeNamesWithDisplayInfo(session, "Part");
        if(list != null && !list.isEmpty())
        {
            for(Iterator iterator = list.iterator(); iterator.hasNext();)
            {
                DisplayTypeInfo displaytypeinfo = (DisplayTypeInfo)iterator.next();
                if(JFomPartCreateHelper.isValidType(session, displaytypeinfo.getTypeName()))
                    typeListModel.addElement(displaytypeinfo);
            }

        }
        typeListBox.revalidate();
        typeListBox.repaint();
    }
}
