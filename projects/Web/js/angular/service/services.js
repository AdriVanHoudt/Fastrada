'use strict';

/* Services */


// Demonstrate how to register services
// In this case it is a simple value service.
angular.module('fastradaApp.services', [])
    .service('dataFetcher', ['$http', function ($http) {
        return {
            getRaceData: function () {
                return $http.get('json/data.json').then(
                    function (response) {
                        // success handler
                        return response.data;
                    }, function (response) {
                        // error handler
                        alert("Something went wrong while receiving data - " + response.status);
                        return null;
                    });
            }

        };
    }]);