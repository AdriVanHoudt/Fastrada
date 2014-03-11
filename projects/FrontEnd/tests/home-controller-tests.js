'use strict';

describe('HomeCtrl', function () {
    var scope, $httpBackend;//we'll use this scope in our tests

    //mock Application to allow us to inject our own dependencies
    beforeEach(angular.mock.module('fastradaApp'));
    //mock the controller for the same reason and include $rootScope and $controller
    beforeEach(angular.mock.inject(function ($rootScope, $controller, $injector) {
        //create an empty scope
        scope = $rootScope.$new();
        $httpBackend = $injector.get('$httpBackend');
        $httpBackend.whenGET('json/data.json').respond();
        //declare the controller and inject our empty scope
        $controller('HomeCtrl', {$scope: scope});
    }));



    // No logic to test
    it('should have variable text = "hello"', function(){
        expect(scope.test).toBe('hello');
    });



});