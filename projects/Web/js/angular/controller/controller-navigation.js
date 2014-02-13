'use strict';

/* Controllers */

angular.module('fastradaApp.controllers').
    controller('NavigationCtrl', ['$scope', function ($scope) {
        $scope.searchboxHove = function () {
            $scope.searchIsHover = false;
            $scope.searchBoxHover = function () {
                $scope.searchIsHover = false;
                return {
                    'border-bottom': '1px rgba(151,163,75,1) solid'
                };
            }
            $scope.searchIconHover = function () {
                $scope.searchIsHover = false;
                return {
                    color: rgba(151, 163, 75, 1)
                };
            }

        }
    }]);