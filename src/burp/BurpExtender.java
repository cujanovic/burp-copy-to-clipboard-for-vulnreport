package burp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JMenuItem;
import java.util.ArrayList;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.Base64;

public class BurpExtender implements IBurpExtender, IContextMenuFactory
{
		private IBurpExtenderCallbacks c2c4VRCallbacks;
		private IContextMenuInvocation c2c4VRInvocation;

		private String vulnReportStart(String string) {
				return "<~=~=~=~=~=~=~=StartVulnReport:" + string + "=~=~=~=~=~=~=~>";
		}

		private String vulnReportEnd(String string) {
				return "<~=~=~=~=~=~=~=EndVulnReport:" + string + "=~=~=~=~=~=~=~>";
		}

		private String vulnReportWrap(String string, String string2) {
				return this.vulnReportStart(string) + string2 + this.vulnReportEnd(string);
		}

		private void setClip(String string) {
				StringSelection stringSelection = new StringSelection(string);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
		}

		@Override
		public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks)
		{
				callbacks.setExtensionName("Copy to Clipboard for VulnReport");
				callbacks.issueAlert("Successfully Initialized VulnReport Plugin");
				callbacks.printOutput("Successfully Initialized VulnReport Plugin.\nhttps://github.com/cujanovic/burp-copy-to-clipboard-for-vulnreport\nBased on: https://github.com/salesforce/vulnreport/blob/master/vulnreport.jar");
				c2c4VRCallbacks = callbacks;
				callbacks.registerContextMenuFactory(this);
		}

		@Override
		public List<JMenuItem> createMenuItems(IContextMenuInvocation invocation)
		{
				List<JMenuItem> menuList = new ArrayList<>();
				c2c4VRInvocation = invocation;
				if(c2c4VRInvocation.getInvocationContext() == IContextMenuInvocation.CONTEXT_PROXY_HISTORY || c2c4VRInvocation.getInvocationContext() == IContextMenuInvocation.CONTEXT_MESSAGE_EDITOR_REQUEST || c2c4VRInvocation.getInvocationContext() == IContextMenuInvocation.CONTEXT_MESSAGE_EDITOR_RESPONSE || c2c4VRInvocation.getInvocationContext() == IContextMenuInvocation.CONTEXT_MESSAGE_VIEWER_REQUEST || c2c4VRInvocation.getInvocationContext() == IContextMenuInvocation.CONTEXT_MESSAGE_VIEWER_RESPONSE || c2c4VRInvocation.getInvocationContext() == IContextMenuInvocation.CONTEXT_SCANNER_RESULTS || c2c4VRInvocation.getInvocationContext() == IContextMenuInvocation.CONTEXT_INTRUDER_ATTACK_RESULTS) {
					JMenuItem Copy2Clipboard4VulnReport = new JMenuItem("Copy to Clipboard for VulnReport");
					Copy2Clipboard4VulnReport.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent arg0) {
									if(arg0.getActionCommand().equals("Copy to Clipboard for VulnReport")) {
										CopyToClipboardForVulnReport(c2c4VRInvocation.getSelectedMessages());
									}
							}
          });
						menuList.add(Copy2Clipboard4VulnReport);
				}
				return menuList;
		}

		private void CopyToClipboardForVulnReport(IHttpRequestResponse[] varArrayHttpRequestResponse)
		{
			StringBuffer stringBuffer = new StringBuffer();
			try {
					for (int i = 0; i < varArrayHttpRequestResponse.length; ++i) {
							byte[] arrayByte;
							IExtensionHelpers helpers = c2c4VRCallbacks.getHelpers();
							IRequestInfo reqinfo = helpers.analyzeRequest(varArrayHttpRequestResponse[i]);
							stringBuffer.append(this.vulnReportWrap("URL",reqinfo.getUrl().toString()));
							byte[] arrayByte2 = varArrayHttpRequestResponse[i].getRequest();
							if (arrayByte2 != null) {
									stringBuffer.append(this.vulnReportWrap("Request", Base64.getEncoder().encodeToString(arrayByte2)));
							}
							if ((arrayByte = varArrayHttpRequestResponse[i].getResponse()) == null) continue;
							stringBuffer.append(this.vulnReportWrap("Response", Base64.getEncoder().encodeToString(arrayByte)));
					}
			}
			catch (Exception exception) {
					exception.printStackTrace();
			}
			this.setClip(stringBuffer.toString());
		}
}
