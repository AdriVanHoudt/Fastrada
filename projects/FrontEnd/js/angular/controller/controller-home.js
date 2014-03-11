'use strict';

/* Controllers */

angular.module('fastradaApp.controllers').
    controller('HomeCtrl', ['$scope', 'dataFetcher', 'queryHandler', function ($scope, dataFetcher, queryHandler) {
        /*
         Function that refreshes the data on the screen based on the currently selected race
         */
        $scope.test = "hello";
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

        function buildSpeedChart(race) {
            var speedData = [];

            for (var i = 0; i < race.sensors[0].packets.length; i++) {
                speedData.push({ time: (new Date(race.sensors[0].packets[i].timestamp)), speed: race.sensors[0].packets[i].value});
            }

            var series = [
                {
                    argumentField: 'time',
                    valueField: 'speed'
                }
            ];

            $("#chartSpeed").dxChart({
                dataSource: speedData,
                series: series,
                commonSeriesSettings: {
                    argumentField: 'time',
                    type: 'line'
                },
                argumentAxis: {
                    label: { format: 'longTime'}
                },
                legend: {
                    visible: false
                },
                tooltip: {
                    enabled: true
                },
                adjustOnZoom: true
            });

            $("#chartSpeedRangeSelector").dxRangeSelector({
                size: {
                    height: 120
                },
                margin: {
                    left: 10
                },
                scale: {
                    divisionValue: 1,
                    minRange: 1
                },
                selectedRange: {
                    startValue: race.sensors[0].packets[0].timestamp,
                    endValue:  race.sensors[0].packets[race.sensors[0].packets.length - 1].timestamp
                },
                dataSource: speedData,
                chart: {
                    series: series
                },
                behavior: {
                    callSelectedRangeChanged: "onMoving"
                },
                selectedRangeChanged: function (e) {
                    var zoomChart = $("#chartSpeed").dxChart('instance');
                    zoomChart.zoomArgument(e.startValue, e.endValue);
                }
            });
        }

        function buildRPMChart(race) {
            var rpmData = [];

            for (var i = 0; i < race.sensors[1].packets.length; i++) {
                rpmData.push({time: (new Date(race.sensors[1].packets[i].timestamp)), rpm: race.sensors[1].packets[i].value});
            }

            var series = [
                {
                    argumentField: 'time',
                    valueField: 'rpm'
                }
            ];

            $("#chartRPM").dxChart({
                dataSource: rpmData,
                series: series,
                commonSeriesSettings: {
                    argumentField: 'time',
                    type: 'line'
                },
                legend: {
                    visible: false
                },
                argumentAxis: {
                    label: { format: 'longTime'}
                },
                tooltip: {
                    enabled: true
                },
                adjustOnZoom: true
            });

            $("#chartRPMRangeSelector").dxRangeSelector({
                size: {
                    height: 120
                },
                margin: {
                    left: 10
                },
                scale: {
                    divisionValue: 1,
                    minRange: 1
                },
                selectedRange: {
                    startValue: race.sensors[1].packets[0].timestamp,
                    endValue: race.sensors[1].packets[race.sensors[0].packets.length - 1].timestamp
                },
                dataSource: rpmData,
                chart: {
                    series: series
                },
                behavior: {
                    callSelectedRangeChanged: "onMoving"
                },
                selectedRangeChanged: function (e) {
                    var zoomChart = $("#chartRPM").dxChart('instance');
                    zoomChart.zoomArgument(e.startValue, e.endValue);
                }
            });
        }


        function buildTemperatureChart(race) {
            var tempData = [];

            for (var i = 0; i < race.sensors[2].packets.length; i++) {
                tempData.push({time: (new Date(race.sensors[2].packets[i].timestamp)), temperature: race.sensors[2].packets[i].value});
            }

            var series = [
                {
                    argumentField: 'time',
                    valueField: 'temperature'
                }
            ];

            $("#chartTemperature").dxChart({
                dataSource: tempData,
                series: series,
                commonSeriesSettings: {
                    argumentField: 'time',
                    type: 'line'
                },
                legend: {
                    visible: false
                },
                tooltip: {
                    enabled: true
                },
                argumentAxis: {
                    label: { format: 'longTime'}
                },
                adjustOnZoom: true
            });

            $("#chartTemperatureRangeSelector").dxRangeSelector({
                size: {
                    height: 120
                },
                margin: {
                    left: 10
                },
                scale: {
                    divisionValue: 1,
                    minRange: 1
                },
                selectedRange: {
                    startValue: race.sensors[2].packets[0].timestamp,
                    endValue: race.sensors[2].packets[race.sensors[2].packets.length - 1].timestamp
                },
                dataSource: tempData,
                chart: {
                    series: series
                },
                behavior: {
                    callSelectedRangeChanged: "onMoving"
                },
                selectedRangeChanged: function (e) {
                    var zoomChart = $("#chartTemperature").dxChart('instance');
                    zoomChart.zoomArgument(e.startValue, e.endValue);
                }
            });
        }



        /*
         Method that retrieves the correct race from the data
         */
        function getRaceFromData(data) {
            for (var i = 0; i < data.length; i++) {
                if (queryHandler.getCurrentRace().toLowerCase() === data[i].name.toLowerCase()) {
                    return data [i];
                }
            }
            // return first race if none found
            return data[0];
        }
    }])
;