'use strict';

/* Services */


// Demonstrate how to register services
// In this case it is a simple value service.
angular.module('fastradaApp.services', [])
    /*
        Data fetcher service that retrieves data from external sources
    */
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
    }])

    /*
        Query handeling service that sends out a broadcast message when a user searches for a certain race,
        when the broadcast is sent it invokes the method in the home controller that updates the screen
     */
    .service('queryHandler', ['$rootScope', function ($rootScope) {
        var currentRace = "Race 1";
        return {
            setCurrentRace: function(race) {
                $rootScope.$broadcast("newRaceQuery");
                currentRace = race;
            },
            getCurrentRace: function() {
                return currentRace;
            }
        }
    }]);