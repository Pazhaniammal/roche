var CommonMethods = require('../util/CommonMethods');
var TumorBoardAnalyticsPage = require('../page/TumorBoardAnalyticsPage');

describe("Validate the analytics dashboard page", function () {

  it('1@Goto Url', () => {
    CommonMethods.loadUrl();
  });

  it('2@Get chart values', async () => {
    let values = await TumorBoardAnalyticsPage.getchartValue();
    console.log("Message", values);
  });

})