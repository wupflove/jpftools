package org.jpf.taobao.zhanguibao;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.UrlUtils;


/**
 * Hello world!
 *
 */
public class App {
  public static void main(String[] args) {

    getTaobaoDetail(
        "https://item.taobao.com/item.htm?spm=a21bo.50862.201875.3.27v4fh&scm=1007.12493.69999.100200300000001&id=544502541545&pvid=8f35b184-c12d-4089-84e4-b9610effb5de");
    // getTaobaoDetail("https://detail.tmall.com/item.htm?id=546537478311");

  }

  public static BrowserVersion getBrowserVersion() {

    BrowserVersion bv = BrowserVersion.BEST_SUPPORTED;

    // 设置语言，否则不知道传过来是什么编码
    bv.setUserLanguage("zh_cn");
    bv.setSystemLanguage("zh_cn");
    bv.setBrowserLanguage("zh_cn");

    // 源码里是写死Win32的，不知道到生产环境（linux）会不会变，稳妥起见还是硬设
    bv.setPlatform("Win32");

    return bv;
  }

  public static WebClient newWebClient() {
    WebClient wc = new WebClient(getBrowserVersion());
    wc.getOptions().setUseInsecureSSL(true); // 允许使用不安全的SSL连接。如果不打开，站点证书过期的https将无法访问
    wc.getOptions().setJavaScriptEnabled(true); // 启用JS解释器
    wc.getOptions().setCssEnabled(false); // 禁用css支持
    // 禁用一些异常抛出
    wc.getOptions().setThrowExceptionOnScriptError(false);
    wc.getOptions().setThrowExceptionOnFailingStatusCode(false);

    wc.getOptions().setDoNotTrackEnabled(false); // 随请求发送DoNotTrack
    wc.setJavaScriptTimeout(4000); // 设置JS超时，这里是1s
    wc.getOptions().setTimeout(10000); // 设置连接超时时间 ，这里是5s。如果为0，则无限期等待
    wc.setAjaxController(new NicelyResynchronizingAjaxController()); // 设置ajax控制器

    return wc;
  }

  private static String getGoodsTitle(Elements elementsTitle) {
    String strTitle = "";
    if (elementsTitle != null && elementsTitle.size() == 1) {
      strTitle = elementsTitle.get(0).toString();
      if (strTitle != null && strTitle.trim().length() > 0) {
        strTitle = strTitle.substring(8, strTitle.lastIndexOf("-")).trim();
        System.out.println("title:" + strTitle);
      }

    }
    return strTitle;
  }

  /**
   * 淘宝详情抓取：分析淘宝的页面，商品详情是异步从cdn加载的，我们只要找到这个cdn的url，直接请求获取response即可。
   * 
   * @category @author 吴平福
   * @param url
   * @return update 2017年4月22日
   */
  public static String getTaobaoDetail(String url) {
    WebClient wc = newWebClient();
    String detail = "";
    try {
      WebRequest request = new WebRequest(UrlUtils.toUrlUnsafe(url));
      // request.setAdditionalHeaders(searchRequestHeader);
      Page page = wc.getPage(request);
      if (page.isHtmlPage()) {
        HtmlPage htmlPage = (HtmlPage) page;
        String html = htmlPage.asXml();
        Document doc = Jsoup.parse(html);
        Elements elementsTitle = doc.head().getElementsByTag("title");
        getGoodsTitle(elementsTitle);
        Elements scripts = doc.head().getElementsByTag("script");
        // System.out.println(scripts.html());
        Element g_config = scriptElementLookup(scripts, "var g_config = {");
        // System.out.println(g_config.getElementsByAttribute("descUrl"));
        String text = g_config.toString();
        // System.out.println(text);
        // System.out.println(text.replaceAll("[\\n]", ""));
        // 找到商品描述url
        String detailUrl = null;
        String[] texts = text.split("\\n");
        System.out.println(texts.length);
        for (String line : texts) {
          System.out.println(line);
          if (line.trim().startsWith("descUrl")) {
            detailUrl = "http:" + getFirstMatch(line,
                "'//dsc.taobaocdn.com/i[0-9]+/[0-9]+/[0-9]+/[0-9]+/.+[0-9]+'//s+:");

            detailUrl = detailUrl.replaceAll("//s+:", "").replace("'", "");
            break;
          }
        }
        // detailUrl=url;
        System.out.println(detailUrl);
        if (StringUtils.isNotBlank(detailUrl))
          detail = wc.getPage(detailUrl).getWebResponse().getContentAsString()
              .replace("var desc='", "").replace("';", "");
        System.out.println(detail);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      wc.close();
    }
    return detail;
  }

  private static Element scriptElementLookup(Elements elements, String lookupFor) {
    Element script = null;
    for (Element elm : elements) {
      if (elm.toString().contains(lookupFor)) {
        script = elm;
        break;
      }
    }
    return script;
  }

  public static String getFirstMatch(String str, String regex) {
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(str);
    String ret = null;
    if (matcher.find()) {
      ret = matcher.group();
    }
    return ret;
  }

  /**
   * 天猫详情抓取： 淘宝天猫是截然两种风格，没找到像淘宝详情页一样的cdn地址，只能从页面上去抓取了。 使用js模拟滚动，然后等待js执行完毕。至于多久真的看RP。。。
   * 
   * @category @author 吴平福
   * @param url
   * @return update 2017年4月22日
   */
  public String getTmallDetail(String url) {
    WebClient wc = newWebClient();

    String detail = "";

    try {
      WebRequest request = new WebRequest(UrlUtils.toUrlUnsafe(url));

      // request.setAdditionalHeaders(searchRequestHeader);

      wc.getCurrentWindow().getTopWindow().setOuterHeight(Integer.MAX_VALUE);
      wc.getCurrentWindow().getTopWindow().setInnerHeight(Integer.MAX_VALUE);

      Page page = wc.getPage(request);
      page.getEnclosingWindow().setOuterHeight(Integer.MAX_VALUE);
      page.getEnclosingWindow().setInnerHeight(Integer.MAX_VALUE);

      if (page.isHtmlPage()) {
        HtmlPage htmlPage = (HtmlPage) page;
        ScriptResult sr = htmlPage.executeJavaScript(
            String.format("javascript:window.scrollBy(0,%d);", Integer.MAX_VALUE));
        // 执行页面所有渲染相关的JS
        int left = 0;
        do {
          left = wc.waitForBackgroundJavaScript(10);
          // System.out.println(left);
        } while (left > 7); // 有6-7个时间超长的js任务

        htmlPage = (HtmlPage) sr.getNewPage();
        detail = htmlPage.getElementById("description").asXml()
            .replaceAll("src=\"//.{0,100}.png\" data-ks-lazyload=", "src="); // 移除懒加载
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      wc.close();
    }
    return detail;
  }


}
