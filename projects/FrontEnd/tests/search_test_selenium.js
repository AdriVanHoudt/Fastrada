describe('angularjs homepage', function() {
    it('should greet the named user', function() {
        browser.get('http://localhost:63343/FrontEnd/index.html#/home');

        element(by.id('speed')).click();
        element(by.id('rpm')).click();
        element(by.id('temperature')).click();

        element(by.model('query')).sendKeys('Race 2');
        element(by.id('searchButton')).click();

        var title = element(by.id('title'));
        expect(title.getText()).toEqual('Race 2');

        element(by.id('speed')).click();
        element(by.id('rpm')).click();
        element(by.id('temperature')).click();
    });
});