var TumorBoardAnalyticsPage = function () {
  var chartValue = element.all(by.css('.lables'));

  this.getchartValue = async function () {
    if (expect(chartValue.isDisplayed()).toBeTruthy()) {
    return chartValue.getText();
    }
  };

}

module.exports = new TumorBoardAnalyticsPage();