'use strict';

/* Controllers */

angular.module('fastradaApp.controllers').
    controller('HomeCtrl', ['$scope', 'dataFetcher', '$timeout', function ($scope, dataFetcher, $timeout) {

        dataFetcher.getRaceData().then(function (data) {
            // alert(data.race2[0].speed);
            buildSpeedChart(data);

            $scope.data = data;

            $scope.openRPM = (function() {
                buildRPMChart(data);
            });

            $scope.openSpeed = (function() {
                buildSpeedChart(data);
            });

            $scope.openTemperature = (function() {
                buildTemperatureChart(data);
            });
        });


        function buildRPMChart(data) {
            var rpmData = [];

            for (var i = 0; i < data.race1.length; i++) {
                rpmData.push({time: dateToTime(new Date(data.race1[i].timestamp)), rpm: data.race1[i].rpm});
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
                }
            });

        }

        function buildSpeedChart(data) {
            var speedData = [];

            for (var i = 0; i < data.race1.length; i++) {
                speedData.push({time: dateToTime(new Date(data.race1[i].timestamp)), speed: data.race1[i].speed});
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
                }
            });
        }


        function buildTemperatureChart(data) {
            var tempData = [];

            for (var i = 0; i < data.race1.length; i++) {
                tempData.push({time: dateToTime(new Date(data.race1[i].timestamp)), temperature: data.race1[i].temperature});
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
                }
            });
        }

        function dateToTime(date) {
            return date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + ":" + date.getMilliseconds();
        }
    }])
;