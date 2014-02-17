'use strict';

/* Controllers */

angular.module('fastradaApp.controllers').
    controller('HomeCtrl', ['$scope', 'dataFetcher', 'queryHandler', function ($scope, dataFetcher, queryHandler) {

        /*
            Function that refreshes the data on the screen based on the currently selected race
        */
        function updateScreen() {
            dataFetcher.getRaceData().then(function (data) {
                var race = getRaceFromData(data);
                $scope.race = race;

                /*
                    Build all charts on load of the page
                 */
                buildSpeedChart(race);
                buildRPMChart(race);
                buildTemperatureChart(race);

                /*
                    Methods that are invoked to build the charts when a user switches between tabs
                 */
                $scope.openRPM = (function () {
                    buildRPMChart(race);
                });

                $scope.openSpeed = (function () {
                    buildSpeedChart(race);
                });

                $scope.openTemperature = (function () {
                    buildTemperatureChart(race);
                });
            });
        }
        updateScreen();

        /*
            Scope will invoke the updateScreen method when a user query's for a new race
         */
        $scope.$on('newRaceQuery', function () {
            updateScreen();
        });


        /*
            Methods that build charts
         */
        function buildRPMChart(race) {
            var rpmData = [];

            for (var i = 0; i < race.data.length; i++) {
                rpmData.push({time: dateToTime(new Date(race.data[i].timestamp)), rpm: race.data[i].rpm});
            }

            $("#chartRPM").dxChart({
                dataSource: rpmData,
                series: [
                    { valueField: 'rpm' }
                ],
                commonSeriesSettings: {
                    argumentField: 'time',
                    type: 'line'
                },
                legend: {
                    visible: false
                },
                tooltip: {
                    enabled: true
                }
            });

        }

        function buildSpeedChart(race) {
            var speedData = [];

            for (var i = 0; i < race.data.length; i++) {
                speedData.push({time: dateToTime(new Date(race.data[i].timestamp)), speed: race.data[i].speed});
            }

            $("#chartSpeed").dxChart({
                dataSource: speedData,
                series: [
                    { valueField: 'speed' }
                ],
                commonSeriesSettings: {
                    argumentField: 'time',
                    type: 'line'
                },
                legend: {
                    visible: false
                },
                tooltip: {
                    enabled: true
                }
            });
        }

        function buildTemperatureChart(race) {
            var tempData = [];

            for (var i = 0; i < race.data.length; i++) {
                tempData.push({time: dateToTime(new Date(race.data[i].timestamp)), temperature: race.data[i].temperature});
            }

            $("#chartTemperature").dxChart({
                dataSource: tempData,
                series: [
                    { valueField: 'temperature' }
                ],
                commonSeriesSettings: {
                    argumentField: 'time',
                    type: 'line'
                },
                legend: {
                    visible: false
                },
                tooltip: {
                    enabled: true
                }
            });
        }

        /*
            Method to extract time from timestamp
         */
        function dateToTime(date) {
            return date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + ":" + date.getMilliseconds();
        }

        /*
            Method that retrieves the correct race from the data
         */
        function getRaceFromData(data) {
            for (var i = 0; i < data.length; i++) {
                if (queryHandler.getCurrentRace() === data[i].name) return data [i];
            }
            // return first race if none found
            return data[0];
        }
    }])
;