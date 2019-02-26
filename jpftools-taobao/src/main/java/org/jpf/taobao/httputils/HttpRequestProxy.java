/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年6月1日 下午2:17:59 类说明
 */

package org.jpf.taobao.httputils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;


/**
 * 
 * @author bird email:lihongfu-84@163.com
 *
 *         2008-11-4 09:49:48
 */
public class HttpRequestProxy {
  // 超时间隔
  private static int connectTimeOut = 60000;
  // 让connectionmanager管理httpclientconnection时是否关闭连接
  private static boolean alwaysClose = false;
  // 返回数据编码格式
  private String encoding = "UTF-8";

  private final HttpClient client = new HttpClient(new SimpleHttpConnectionManager());

  public HttpClient getHttpClient() {
    return client;
  }

  /**
   * 用法： HttpRequestProxy hrp = new HttpRequestProxy();
   * hrp.doRequest("http://www.163.com",null,null,"gbk");
   * 
   * @param url 请求的资源ＵＲＬ
   * @param postData POST请求时form表单封装的数据 没有时传null
   * @param header request请求时附带的头信息(header) 没有时传null
   * @param encoding response返回的信息编码格式 没有时传null
   * @return response返回的文本数据
   * @throws CustomException
   */
  public String doRequest(String url, Map postData, Map header, String encoding) throws Exception {
    String responseString = null;
    // 头部请求信息
    Header[] headers = null;
    if (header != null) {
      Set entrySet = header.entrySet();
      int dataLength = entrySet.size();
      headers = new Header[dataLength];
      int i = 0;
      for (Iterator itor = entrySet.iterator(); itor.hasNext();) {
        Map.Entry entry = (Map.Entry) itor.next();
        headers[i++] = new Header(entry.getKey().toString(), entry.getValue().toString());
      }
    }
    // post方式
    if (postData != null) {
      PostMethod postRequest = new PostMethod(url.trim());
      if (headers != null) {
        for (int i = 0; i < headers.length; i++) {
          postRequest.setRequestHeader(headers[i]);
        }
      }
      Set entrySet = postData.entrySet();
      int dataLength = entrySet.size();
      NameValuePair[] params = new NameValuePair[dataLength];
      int i = 0;
      for (Iterator itor = entrySet.iterator(); itor.hasNext();) {
        Map.Entry entry = (Map.Entry) itor.next();
        params[i++] = new NameValuePair(entry.getKey().toString(), entry.getValue().toString());
      }
      postRequest.setRequestBody(params);
      try {
        responseString = this.executeMethod(postRequest, encoding);
      } catch (Exception e) {
        throw e;
      } finally {
        postRequest.releaseConnection();
      }
    }
    // get方式
    if (postData == null) {
      GetMethod getRequest = new GetMethod(url.trim());
      if (headers != null) {
        for (int i = 0; i < headers.length; i++) {
          getRequest.setRequestHeader(headers[i]);
        }
      }
      try {
        responseString = this.executeMethod(getRequest, encoding);
      } catch (Exception e) {
        e.printStackTrace();
        throw e;
      } finally {
        getRequest.releaseConnection();
      }
    }

    return responseString;
  }

  private String executeMethod(HttpMethod request, String encoding) throws Exception {
    String responseContent = null;
    InputStream responseStream = null;
    BufferedReader rd = null;
    try {
      this.getHttpClient().executeMethod(request);
      if (encoding != null) {
        responseStream = request.getResponseBodyAsStream();
        rd = new BufferedReader(new InputStreamReader(responseStream, encoding));
        String tempLine = rd.readLine();
        StringBuffer tempStr = new StringBuffer();
        String crlf = System.getProperty("line.separator");
        while (tempLine != null) {
          tempStr.append(tempLine);
          tempStr.append(crlf);
          tempLine = rd.readLine();
        }
        responseContent = tempStr.toString();
      } else
        responseContent = request.getResponseBodyAsString();

      Header locationHeader = request.getResponseHeader("location");
      // 返回代码为302,301时，表示页面己经重定向，则重新请求location的url，这在
      // 一些登录授权取cookie时很重要
      if (locationHeader != null) {
        String redirectUrl = locationHeader.getValue();
        this.doRequest(redirectUrl, null, null, null);
      }
    } catch (HttpException e) {
      throw new Exception(e.getMessage());
    } catch (IOException e) {
      throw new Exception(e.getMessage());

    } finally {
      if (rd != null)
        try {
          rd.close();
        } catch (IOException e) {
          throw new Exception(e.getMessage());
        }
      if (responseStream != null)
        try {
          responseStream.close();
        } catch (IOException e) {
          throw new Exception(e.getMessage());

        }
    }
    return responseContent;
  }


  /**
   * 特殊请求数据,这样的请求往往会出现redirect本身而出现递归死循环重定向 所以单独写成一个请求方法
   * 比如现在请求的url为：http://localhost:8080/demo/index.jsp 返回代码为302
   * 头部信息中location值为:http://localhost:8083/demo/index.jsp
   * 这时httpclient认为进入递归死循环重定向，抛出CircularRedirectException异常
   * 
   * @param url
   * @return
   * @throws CustomException
   */
  public String doSpecialRequest(String url, int count, String encoding) throws Exception {
    String str = null;
    InputStream responseStream = null;
    BufferedReader rd = null;
    GetMethod getRequest = new GetMethod(url);
    // 关闭httpclient自动重定向动能
    getRequest.setFollowRedirects(false);
    try {

      this.client.executeMethod(getRequest);
      Header header = getRequest.getResponseHeader("location");
      if (header != null) {
        // 请求重定向后的ＵＲＬ，count同时加1
        this.doSpecialRequest(header.getValue(), count + 1, encoding);
      }
      // 这里用count作为标志位，当count为0时才返回请求的ＵＲＬ文本,
      // 这样就可以忽略所有的递归重定向时返回文本流操作，提高性能
      if (count == 0) {
        getRequest = new GetMethod(url);
        getRequest.setFollowRedirects(false);
        this.client.executeMethod(getRequest);
        responseStream = getRequest.getResponseBodyAsStream();
        rd = new BufferedReader(new InputStreamReader(responseStream, encoding));
        String tempLine = rd.readLine();
        StringBuffer tempStr = new StringBuffer();
        String crlf = System.getProperty("line.separator");
        while (tempLine != null) {
          tempStr.append(tempLine);
          tempStr.append(crlf);
          tempLine = rd.readLine();
        }
        str = tempStr.toString();
      }

    } catch (HttpException e) {
      throw new Exception(e.getMessage());
    } catch (IOException e) {
      throw new Exception(e.getMessage());
    } finally {
      getRequest.releaseConnection();
      if (rd != null)
        try {
          rd.close();
        } catch (IOException e) {
          throw new Exception(e.getMessage());
        }
      if (responseStream != null)
        try {
          responseStream.close();
        } catch (IOException e) {
          throw new Exception(e.getMessage());
        }
    }
    return str;
  }

  /**
   * 
   * @category 获取HTTPRESPONSE返回字符串
   * @author 吴平福
   * @param strURL
   * @return
   * @throws Exception update 2017年6月1日
   */
  public static String getHttpUrlResponse(String strURL) throws Exception {
    HttpRequestProxy hrp = new HttpRequestProxy();
    Map header = new HashMap();
    header.put("User-Agent",
        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 1.7; .NET CLR 1.1.4322; CIBA; .NET CLR 2.0.50727)");
    return hrp.doRequest(strURL, null, header, null);

  }

  public static void main(String[] args) throws Exception {
    HttpRequestProxy hrp = new HttpRequestProxy();
    Map header = new HashMap();
    header.put("User-Agent",
        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 1.7; .NET CLR 1.1.4322; CIBA; .NET CLR 2.0.50727)");
    String str = hrp.doRequest(
        "https://s.taobao.com/search?q=女用棒震动&js=1&stats_click=search_radio_all%3A1&initiative_id=staobaoz_20170703&ie=utf8",
        null, header, null);
    // System.out.println(str.contains("row_CRXU1587647"));

    int iPosBegin = str.indexOf("g_page_config = ");
    int iPosEnd = str.indexOf("g_srp_loadCss();");
    str = str.substring(iPosBegin, iPosEnd);
    System.out.println(str);
  }

}


