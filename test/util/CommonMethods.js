var CommonMethods = function() {

  this.isAlertPresent = async function() {
    await browser.switchTo().alert();
    return true;
  }

  this.clickWhenClickable = async function(element) {
    return browser.wait(() => {
      return element.click().then(
        () => {
          return true;
        },
        () => {
          return false;
        });
    });
  }

  this.elementVisible = async function(element) {
    const EC = ExpectedConditions;
    const condition = EC.presenceOf(element);
    return browser.wait(condition, 50000, 'Element is not present');
  }

  this.textToPresentInElement = async function(elementLocator, elementValue) {
    const EC = ExpectedConditions;
    const condition = EC.textToBePresentInElement(elementLocator, elementValue);
    return browser.wait(condition, 50000, 'Text is not present in the element');
  }

  this.urlToBeLoaded = async function(partialUrl) {
    const EC = ExpectedConditions;
    const condition = EC.urlContains(partialUrl);
    return browser.wait(condition, 500000, 'Url not loaded');
  }

  this.scrollDown = function() {
    browser.executeScript('window.scrollTo(0, document.body.scrollHeight)');
  }

  this.clearInputValue = async function(element) {
    element.sendKeys(Key.CONTROL + 'a');
    return element.sendKeys(Key.SHIFT, Key.END, Key.BACK_SPACE);
  }

  this.loadUrl = async function() {
    browser.driver.manage().deleteAllCookies();
    browser.driver
      .manage()
      .window()
      .maximize();
    await browser.get(browser.params.baseUrl);
  }

  this.scrollIntoView = function(element) {
    browser.executeScript('arguments[0].scrollIntoView(true);', element);
  }

}

module.exports = new CommonMethods();

