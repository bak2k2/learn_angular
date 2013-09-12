'use strict';

describe('Controller: AdminHomeCtrl', function () {

    beforeEach(function(){
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