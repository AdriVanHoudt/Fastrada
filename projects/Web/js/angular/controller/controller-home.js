'use strict';

/* Controllers */

angular.module('fastradaApp.controllers').
    controller('HomeCtrl', ['$scope', 'dataFetcher', '$timeout', function ($scope, dataFetcher, $timeout) {
        $scope.counter = 1;
        $scope.nextChart = (function () {
            if ($scope.counter < 3) {
                $scope.counter++;
            } else $scope.counter = 1;
        });

        $scope.prevChart = (function () {
            if ($scope.counter > 1 && $scope.counter <= 3) {
                $scope.counter--;
            } else $scope.counter = 3;
        });


        dataFetcher.getRaceData().then(function (data) {
            // alert(data.race2[0].speed);
            buildSpeedChart(data);
            buildRPMChart(data);

            $scope.data = data;
        });

        function buildRPMChart(data) {
            var rpmData = [];

            for (var i = 0; i < data.race1.length; i++) {
                rpmData.push({time: data.race1[i].timestamp, rpm: data.race1[i].rpm});
            }

            $("#chartRPM").dxChart({
                dataSource: rpmData,
                series: [
                    { valueField: 'rpm' }
                ],
                commonSeriesSettings: {
                    argumentField: 'time',
                    type: 'line'
                }
            });

        }

        function buildSpeedChart(data) {
            var speedData = [];

            for (var i = 0; i < data.race1.length; i++) {
                speedData.push({time: data.race1[i].timestamp, speed: data.race1[i].speed});
            }

            $("#chartSpeed").dxChart({
                dataSource: speedData,
                series: [
                    { valueField: 'speed' }
                ],
                commonSeriesSettings: {
                    argumentField: 'time',
                    type: 'line'
                }
            });
        }


        $('#chartOptions').dxChart({
            dataSource: [
                { company: 'test', 2004: 100, 2005: 362, 2006: 400},
                { company: 'test2', 2004: 200, 2005: 562, 2006: 600},
                { company: 'test3', 2004: 600, 2005: 862, 2006: 800}
            ],
            series: [
                { valueField: '2004', name: '2004', type: 'area'},
                { valueField: '2005', name: '2005', type: 'area'},
                { valueField: '2006', name: '2006', type: 'spline', color: '#03A624'}
            ],
            commonSeriesSettings: {
                argumentField: 'company',
                area: { color: 'cornflowerblue' }
            }
        });
    }])
;