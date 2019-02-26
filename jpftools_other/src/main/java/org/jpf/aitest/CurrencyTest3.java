/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2016年10月12日 下午5:00:24 类说明
 */

package org.jpf.aitest;

import org.junit.Test;

import junit.framework.TestCase;


/**
 * 
 */
public class CurrencyTest3 extends TestCase {


  @Test
  public void Selenium2FirefoxTest() {
    // Instantiate a webDriver implementation
    // WebDriver webdriver = new FirefoxDriver();
    // webdriver.get("https://github.com");
    // Assert.assertEquals("GitHub - Social Coding", webdriver.getTitle());
    // webdriver.quit();
  }

  /**
      * 
      */
  @Test
  public void Selenium2ChromeTest() {
    System.setProperty("webdriver.chrome.driver",
        "src/main/resources/drivers/chrome/chromedriver-mac");

    // Instantiate a webDriver implementation
    // WebDriver webdriver = new ChromeDriver();

    // webdriver.get("https://github.com");

    // Assert.assertEquals("GitHub - Social Coding", webdriver.getTitle());
  }

  /**
   * 
   */
  @Test
  public void Selenium2ExplorerTest() {
    /*
     * WebDriver webdriver = new InternetExplorerDriver(); DesiredCapabilities
     * capability=DesiredCapabilities.internetExplorer(); capability.setCapability(
     * InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_ IGNORING_SECURITY_DOMAINS, true); WebDriver
     * webdriver = new InternetExplorerDriver(capability); webdriver.get("https://github.com");
     * 
     * Assert.assertEquals("GitHub - Social Coding", webdriver.getTitle());
     */
  }
}
