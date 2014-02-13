'use strict';

/* Controllers */

angular.module('fastradaApp.controllers').
    controller('HomeCtrl', ['$scope', function ($scope) {
        $scope.chartOptions = {
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
                argumentField:'company',
                area: { color: 'cornflowerblue' }
            }
        };
    }]);