describe('Controller: IterationsCtrl', function () {

    var IterationsCtrl, scope, Restangular, httpBackend;

    beforeEach(function(){
        module('metrics');
    });

    beforeEach(inject(function($controller, $rootScope, _$httpBackend_, _Restangular_) {
        httpBackend = _$httpBackend_;
        Restangular = _Restangular_;
        scope = $rootScope;

        httpBackend.expectGET("/resources/iterations").respond('[]');

        IterationsCtrl = $controller('IterationsCtrl', {
            $scope: scope,
            $httpBackend: httpBackend,
            Restangular: Restangular
        });


    }));

    it('iteration should be empty', function () {
        httpBackend.flush();
        expect(scope.iteration).toEqual({});
//        expect(scope.iteration).toEqual({ "id": "a"});
    });
});