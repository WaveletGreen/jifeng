package com.connor.jifeng.plm.jfom001;

import com.teamcenter.rac.kernel.*;
import org.apache.log4j.Logger;

public class JFomPartCreateHelper
{

    public JFomPartCreateHelper()
    {
    }

    public static boolean isValidRevisionType(TCComponent tccomponent)
    {
        try
        {
            return (tccomponent instanceof TCComponentItemRevision) && tccomponent.isTypeOf("Part Revision");
        }
        catch(TCException tcexception)
        {
            Logger.getLogger(JFomPartCreateHelper.class).error(tcexception.getLocalizedMessage(), tcexception);
        }
        return false;
    }

    public static boolean isValidType(TCComponent tccomponent)
    {
        try
        {
            return (tccomponent instanceof TCComponentItem) && tccomponent.isTypeOf("Part");
        }
        catch(TCException tcexception)
        {
            Logger.getLogger(JFomPartCreateHelper.class).error(tcexception.getLocalizedMessage(), tcexception);
        }
        return false;
    }

    public static boolean isValidRevisionType(TCSession tcsession, String s)
    {
        try
        {
            TCComponentItemType tccomponentitemtype = (TCComponentItemType)tcsession.getTypeComponent(s);
            return tccomponentitemtype != null && tccomponentitemtype.isTypeOf("Part Revision");
        }
        catch(TCException tcexception)
        {
            Logger.getLogger(JFomPartCreateHelper.class).error(tcexception.getLocalizedMessage(), tcexception);
        }
        return false;
    }

    public static boolean isValidType(TCSession tcsession, String s)
    {
        try
        {
            TCComponentItemType tccomponentitemtype = (TCComponentItemType)tcsession.getTypeComponent(s);
            return tccomponentitemtype != null && tccomponentitemtype.isTypeOf("Part");
        }
        catch(TCException tcexception)
        {
            Logger.getLogger(JFomPartCreateHelper.class).error(tcexception.getLocalizedMessage(), tcexception);
        }
        return false;
    }
}


/*
	DECOMPILATION REPORT

	Decompiled from: D:\PLM\Siemens\Teamcenter10\portal\plugins\com.teamcenter.rac.common_10000.1.0.jar
	Total time: 22 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/