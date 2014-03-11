var page = new WebPage(),
    system = require('system'),
    address = 'http://localhost:63343/FrontEnd/index.html#/home';

page.open(address, function (status) {
    if (status === 'success') {
        page.includeJs("http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js");
        console.log("Loaded jQuery!");

        page.evaluate(function () {
            $('#searchField').val('Race 1');
            $('#seachButton').on('click', function() {
              setTimeout(function(){}, 5000);
            });
            $('#searchButton').click();

            console.log('hey');
        });

        phantom.exit();

    } else {
        console.log('something went wrong while opening the page, is the localhost running?');
    }


});

