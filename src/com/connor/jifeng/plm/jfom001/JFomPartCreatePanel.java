package com.connor.jifeng.plm.jfom001;

import com.teamcenter.rac.commands.newalternateid.AdditionalIdInfoPanel;
import com.teamcenter.rac.commands.newalternateid.AdditionalRevInfoPanel;
import com.teamcenter.rac.commands.newitem.*;
import com.teamcenter.rac.commands.newpart.NewPartPanel;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.wizard.AbstractControlWizardPanel;
import java.awt.Frame;
import java.util.ArrayList;

// Referenced classes of package com.teamcenter.rac.commands.newpart:
//            PartTypePanel, PartInfoPanel

public class JFomPartCreatePanel extends NewPartPanel
{

    public JFomPartCreatePanel(Frame frame, TCSession tcsession, NewItemDialog newitemdialog)
    {
        super(frame, tcsession, newitemdialog);
    }

    public JFomPartCreatePanel(Frame frame, TCSession tcsession, NewItemDialog newitemdialog, Boolean boolean1)
    {
        super(frame, tcsession, newitemdialog, boolean1);
    }

    public ItemTypePanel createTypePanel(Frame frame, TCSession tcsession, boolean flag)
    {
        return new JFomPartCreateTypePanel(frame, session, this, flag);
    }

    public ItemInfoPanel createInfoPanel(Frame frame, TCSession tcsession, boolean flag)
    {
        return new JFomPartCreateInfoPanel(frame, tcsession, this, flag);
    }

    public com.teamcenter.rac.util.wizard.AbstractControlWizardPanel.ValidationStatus beforeUnloadCurrentPanel()
    {
        com.teamcenter.rac.util.wizard.AbstractControlWizardPanel.ValidationStatus validationstatus = com.teamcenter.rac.util.wizard.AbstractControlWizardPanel.ValidationStatus.FLAG_PROCEED;
        if(step == getItemMasterFormStep() && !itemMasterPanel.soaSupported && !saveForms(AbstractNewItemPanel.IM_FORM))
            validationstatus = com.teamcenter.rac.util.wizard.AbstractControlWizardPanel.ValidationStatus.FLAG_STOP;
        else
        if(step == getItemRevMasterFormStep() && !itemRevMasterPanel.soaSupported && !saveForms(AbstractNewItemPanel.IRM_FORM))
        {
            validationstatus = com.teamcenter.rac.util.wizard.AbstractControlWizardPanel.ValidationStatus.FLAG_STOP;
        } else
        {
            NewItemAlternateIdInfoPanel newitemalternateidinfopanel = (NewItemAlternateIdInfoPanel)stepPanels.get(getAltIdInfoStep());
            boolean flag = newitemalternateidinfopanel.isRequired(getItemType());
            if(!flag && isLastPanelForAltId() && getContext() != null && !saveAltIds())
                validationstatus = com.teamcenter.rac.util.wizard.AbstractControlWizardPanel.ValidationStatus.FLAG_STOP;
        }
        return validationstatus;
    }

    public com.teamcenter.rac.util.wizard.AbstractControlWizardPanel.ValidationStatus beforeLoadPanel(int i)
    {
        if(newComponent == null && (i == getAltIdInfoStep() || i == getAttachFilesStep() && (stepPanels.get(i) instanceof ItemAttachFilesPanel) || i == getAdditionalAltIdInfoStep() || i == getAdditionalAltRevInfoStep() || i == getSubmitToWorkflowStep()))
        {
            boolean flag = createNewComponent();
            if(!flag)
                return com.teamcenter.rac.util.wizard.AbstractControlWizardPanel.ValidationStatus.FLAG_STOP;
        }
        if(i == getItemMasterFormStep())
        {
            ItemMasterFormPanel itemmasterformpanel = (ItemMasterFormPanel)stepPanels.get(getItemMasterFormStep());
            itemmasterformpanel.loadFormPanel();
        }
        if(i == getItemRevMasterFormStep())
        {
            ItemRevMasterFormPanel itemrevmasterformpanel = (ItemRevMasterFormPanel)stepPanels.get(getItemRevMasterFormStep());
            itemrevmasterformpanel.loadFormPanel();
        }
        if(i == getAttachFilesStep() && (stepPanels.get(i) instanceof ItemAttachFilesPanel))
        {
            ItemAttachFilesPanel itemattachfilespanel = (ItemAttachFilesPanel)stepPanels.get(i);
            validateNextButton(i);
            if(!itemattachfilespanel.isRequired())
                return com.teamcenter.rac.util.wizard.AbstractControlWizardPanel.ValidationStatus.FLAG_SKIP;
            itemattachfilespanel.loadAttachFilesPanel();
        }
        if(i == getSubmitToWorkflowStep() && (stepPanels.get(i) instanceof SubmitToWorkflowPanel))
        {
            SubmitToWorkflowPanel submittoworkflowpanel = (SubmitToWorkflowPanel)stepPanels.get(i);
            validateNextButton(i);
            if(!submittoworkflowpanel.isRequired())
                return com.teamcenter.rac.util.wizard.AbstractControlWizardPanel.ValidationStatus.FLAG_SKIP;
            submittoworkflowpanel.initPanel();
        }
        if(newIds == null && (i == getAdditionalAltIdInfoStep() || i == getAdditionalAltRevInfoStep()))
        {
            boolean flag1 = createNewIds();
            if(!flag1)
                return com.teamcenter.rac.util.wizard.AbstractControlWizardPanel.ValidationStatus.FLAG_STOP;
        }
        if(i == getAltIdInfoStep())
        {
            NewItemAlternateIdInfoPanel newitemalternateidinfopanel = (NewItemAlternateIdInfoPanel)stepPanels.get(i);
            if(!newitemalternateidinfopanel.loadContexts())
            {
                contextFlag = false;
                validateNextButton(i);
                return com.teamcenter.rac.util.wizard.AbstractControlWizardPanel.ValidationStatus.FLAG_SKIP;
            }
        }
        if(i == getAdditionalAltIdInfoStep())
        {
            AdditionalIdInfoPanel additionalidinfopanel = (AdditionalIdInfoPanel)stepPanels.get(i);
            additionalidinfopanel.loadPanel(newIds[0]);
        }
        if(i == getAdditionalAltRevInfoStep())
        {
            AdditionalRevInfoPanel additionalrevinfopanel = (AdditionalRevInfoPanel)stepPanels.get(i);
            additionalrevinfopanel.loadPanel(newIds[1]);
        }
        return com.teamcenter.rac.util.wizard.AbstractControlWizardPanel.ValidationStatus.FLAG_PROCEED;
    }

    public boolean beforeSetPanel(int i)
    {
        return true;
    }
}