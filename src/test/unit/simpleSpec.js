'use strict';

describe("Hello world", function() {
    beforeEach(module('testApp'));
    it("says hello", function() {
        expect("Hello world!").toEqual("Hello world!");
    });

    it("says hello", function() {
        expect("Hello world!").toEqual("Hello world!");
    });

});

describe('Controller: AdminHomeCtrl', function () {

    beforeEach(function(){
//        angular.mock.module('ngResource', []);
//        angular.mock.module('restangular', []);
//        module('metrics', ['ngResource', 'restangular']);
        module('metrics');
    });

    var AdminHomeCtrl, scope;

    beforeEach(inject(function ($controller, $rootScope) {
        scope = $rootScope.$new();
        AdminHomeCtrl = $controller('AdminHomeCtrl', {
            $scope: scope
        });
    }));

    it('should attach a list of menu items to the scope', function () {
        expect(scope.menus.length).toBe(2);
    });
});