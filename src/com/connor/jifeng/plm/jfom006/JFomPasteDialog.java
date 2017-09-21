package com.connor.jifeng.plm.jfom006;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.InterfaceAIFOperationExecutionListener;
import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.common.AbstractProgressDialog;
import com.teamcenter.rac.common.Activator;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentTempProxyLink;
import com.teamcenter.rac.kernel.TCComponentUser;
import com.teamcenter.rac.kernel.TCPreferenceService;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.services.ISessionService;
import com.teamcenter.rac.util.MessageBox;
import com.teamcenter.rac.util.OSGIUtil;
import com.teamcenter.rac.util.Registry;
import java.awt.Frame;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import org.apache.log4j.Logger;

public class JFomPasteDialog
  extends AbstractProgressDialog
{
  private static final Logger logger = Logger.getLogger(JFomPasteDialog.class);
  private AIFComponentContext[] targets;
  protected TCSession session;
  private boolean confirmationFlag = true;
  private boolean failBackFlag = false;
  private HashSet<InterfaceAIFComponent> failBackContainer = new HashSet();
  private boolean openAfterPasteFlag = false;
  private Registry m_reg;
  
  public JFomPasteDialog(AIFComponentContext[] paramArrayOfAIFComponentContext, Frame paramFrame, boolean paramBoolean)
  {
    this(paramArrayOfAIFComponentContext, paramFrame, paramBoolean, false);
  }
  
  public JFomPasteDialog(AIFComponentContext[] paramArrayOfAIFComponentContext, Frame paramFrame, boolean paramBoolean1, boolean paramBoolean2)
  {
    super(paramFrame);
    try
    {
      this.session = ((TCSession)Activator.getDefault().getSessionService().getSession(TCSession.class.getName()));
    }
    catch (Exception localException)
    {
      logger.error(localException.getClass().getName(), localException);
    }
    this.targets = paramArrayOfAIFComponentContext;
    this.confirmationFlag = paramBoolean1;
    this.openAfterPasteFlag = paramBoolean2;
    initializeDialog();
  }
  
  public JFomPasteDialog(AIFComponentContext[] paramArrayOfAIFComponentContext, Frame paramFrame, boolean paramBoolean, Map<String, String> paramMap)
  {
    super(paramFrame);
    try
    {
      this.session = ((TCSession)Activator.getDefault().getSessionService().getSession(TCSession.class.getName()));
    }
    catch (Exception localException)
    {
      logger.error(localException.getClass().getName(), localException);
    }
    this.targets = paramArrayOfAIFComponentContext;
    this.confirmationFlag = paramBoolean;
    initializeDialog();
  }
  
  public JFomPasteDialog(AIFComponentContext[] paramArrayOfAIFComponentContext, boolean paramBoolean)
  {
    this.session = ((TCSession)paramArrayOfAIFComponentContext[0].getComponent().getSession());
    this.targets = paramArrayOfAIFComponentContext;
    this.confirmationFlag = paramBoolean;
    initializeDialog();
  }
  
  protected Registry getReg()
  {
    if (this.m_reg == null) {
      this.m_reg = Registry.getRegistry(JFomPasteDialog.class);
    }
    return this.m_reg;
  }
  
  private void initializeDialog()
  {
    setCommandIcon(getReg().getImageIcon("paste.ICON"));
    setSuccessIcon(getReg().getImageIcon("paste.ICON"));
    if (this.confirmationFlag) {
      setConfirmationText(getReg().getString("confirmationText"));
    } else {
      setConfirmationText(getReg().getString("noConfirmationText"));
    }
    setDisplaySuccessComponents(true);
    setTCComponents(this.targets);
    setConfirmationFlag(this.confirmationFlag);
    setModal(false);
  }
  
  protected void getOperations(final AIFComponentContext paramAIFComponentContext)
  {
    JFomPasteOperation localPasteOperation = new JFomPasteOperation(paramAIFComponentContext, this.openAfterPasteFlag);
    localPasteOperation.addOperationListener(new InterfaceAIFOperationExecutionListener()
    {
      public void startOperation(String paramAnonymousString) {}
      
      public void endOperation() {}
      
      public void exceptionThrown(Exception paramAnonymousException)
      {
        if (JFomPasteDialog.this.failBackFlag)
        {
          InterfaceAIFComponent localInterfaceAIFComponent = paramAIFComponentContext.getComponent();
          if (localInterfaceAIFComponent != null) {
            JFomPasteDialog.this.addToFailBackContainer(localInterfaceAIFComponent);
          }
        }
      }
    });
    addOperation(localPasteOperation);
  }
  
  protected void getOperations(TCComponent paramTCComponent, AIFComponentContext[] paramArrayOfAIFComponentContext)
  {
    if (paramArrayOfAIFComponentContext != null)
    {
      final InterfaceAIFComponent[] arrayOfInterfaceAIFComponent = new InterfaceAIFComponent[paramArrayOfAIFComponentContext.length];
      for (int i = 0; i < paramArrayOfAIFComponentContext.length; i++) {
        arrayOfInterfaceAIFComponent[i] = paramArrayOfAIFComponentContext[i].getComponent();
      }
      String str = (String)paramArrayOfAIFComponentContext[0].getContext();
      Registry localRegistry = Registry.getRegistry(this);
      JFomPasteOperation localPasteOperation = (JFomPasteOperation)localRegistry.newInstanceFor("pasteOperation", new Object[] { null, paramTCComponent, arrayOfInterfaceAIFComponent, str, Boolean.valueOf(this.openAfterPasteFlag) });
      localPasteOperation.addOperationListener(new InterfaceAIFOperationExecutionListener()
      {
        public void startOperation(String paramAnonymousString) {}
        
        public void endOperation() {}
        
        public void exceptionThrown(Exception paramAnonymousException)
        {
          if (JFomPasteDialog.this.failBackFlag) {
            for (int i = 0; i < arrayOfInterfaceAIFComponent.length; i++) {
              if (arrayOfInterfaceAIFComponent[i] != null) {
                JFomPasteDialog.this.addToFailBackContainer(arrayOfInterfaceAIFComponent[i]);
              }
            }
          }
        }
      });
      addOperation(localPasteOperation);
    }
  }
  
  public void setFailBackFlag(boolean paramBoolean)
  {
    this.failBackFlag = paramBoolean;
  }
  
  protected boolean successfulCompletion()
  {
    boolean bool = super.successfulCompletion();
    if (bool) {
      return bool;
    }
    this.failBackContainer.addAll(super.getErrorComponents());
    if ((this.failBackFlag) && (!this.failBackContainer.isEmpty()) && (this.session != null))
    {
      try
      {
        TCComponentUser localTCComponentUser = this.session.getUser();
        TCComponentFolder localTCComponentFolder = localTCComponentUser.getNewStuffFolder();
        Iterator localIterator = this.failBackContainer.iterator();
        while (localIterator.hasNext())
        {
        	InterfaceAIFComponent localObject = (InterfaceAIFComponent)localIterator.next();
          String str = localTCComponentFolder.getPreferredPasteRelation((InterfaceAIFComponent)localObject);
          AIFComponentContext localAIFComponentContext = new AIFComponentContext(localTCComponentFolder, (InterfaceAIFComponent)localObject, str);
          this.session.performOperation(new JFomPasteOperation(localAIFComponentContext));
        }
        Object localObject = getReg().getString("pasteFailed.INFO");
        MessageBox.post(this, (String)localObject, null, 2);
      }
      catch (Exception localException)
      {
        MessageBox.post(this, localException);
      }
      this.failBackContainer.clear();
    }
    return bool;
  }
  
  protected int getChunkNumber()
  {
    int i = -1;
    String str1;
    try
    {
      TCPreferenceService localTCPreferenceService = (TCPreferenceService)OSGIUtil.getService(Activator.getDefault(), TCPreferenceService.class);;
      str1 = localTCPreferenceService.getString(4, "copyPasteChunkNumber");
      if ((str1 != null) && (str1.trim().length() > 0)) {
        i = Integer.parseInt(str1);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    if (i <= 0) {
      i = 100;
    }
    if (this.targets != null)
    {
      InterfaceAIFComponent[] arrayOfInterfaceAIFComponent = new InterfaceAIFComponent[this.targets.length];
      str1 = getContextString(this.targets[0]);
      int j = 1;
      for (int k = 0; k < this.targets.length; k++)
      {
        arrayOfInterfaceAIFComponent[k] = this.targets[k].getComponent();
        String str2 = getContextString(this.targets[k]);
        if ((arrayOfInterfaceAIFComponent[k] instanceof TCComponentTempProxyLink)) {
          j = 0;
        } else if ((str1 != null) && (!str1.equals(str2))) {
          j = 0;
        }
      }
      if (j == 0) {
        i = 1;
      }
    }
    return i;
  }
  
  protected AbstractAIFUIApplication getApp()
  {
    return null;
  }
  
  protected String getContextString(AIFComponentContext paramAIFComponentContext)
  {
    return (String)paramAIFComponentContext.getContext();
  }
  
  protected TCSession getSession()
  {
    return this.session;
  }
  
  protected boolean getFailBackFlag()
  {
    return this.failBackFlag;
  }
  
  protected void addToFailBackContainer(InterfaceAIFComponent paramInterfaceAIFComponent)
  {
    this.failBackContainer.add(paramInterfaceAIFComponent);
  }
  
  public void startExecute()
  {
    getSession().suspendEvent();
  }
  
  public void endExecute()
  {
    getSession().resumeEvent();
  }
}
