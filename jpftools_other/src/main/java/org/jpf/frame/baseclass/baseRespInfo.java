/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2015年2月8日 下午11:07:00 类说明
 */

package org.jpf.frame.baseclass;

import org.jpf.utils.web.ResponseInfo;

public class baseRespInfo {
  public baseRespInfo() {
    iRet = ResponseInfo.REQUEST_SUCESS;
    reStr = ResponseInfo.RESP_SUCCESS;
  }

  public int iRet;
  public long iValue;
  public String reStr;
  public String strErrInfo;
  private String strDataMsg = "";

  public void setStrDataMsg(String strMsg) {
    strMsg = strMsg.trim();
    if (strMsg.length() > 254) {
      strMsg = strMsg.substring(0, 254);
    }
    strDataMsg = strMsg;
  }

  public String getStrDataMsg() {
    return strDataMsg;
  }
}
