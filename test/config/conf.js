
exports.config = {
    seleniumAddress: 'http://localhost:4444/wd/hub',

    capabilities: {
      'browserName': 'chrome',
      },

  framework: 'jasmine',

  specs: [  
    '../e2e/TC_001_Validate_the_analytics_dashboard_page.js',
  ],

  jasmineNodeOpts: {
    defaultTimeoutInterval: 90000
  },

  params: {
    baseUrl: 'https://d3angularpoc.web.app/tablencharts',
    username:"dev@instance.com",
    password:"test123$"
  },

  onPrepare: () => {
    let globals = require('protractor');
    let browser = globals.browser;
   browser.manage().window().maximize();
 }
}