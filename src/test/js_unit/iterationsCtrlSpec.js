describe('Controller: IterationsCtrl', function () {

    var IterationsCtrl, scope, Restangular, httpBackend, controller;

    beforeEach(function(){
        module('metrics');
    });

    beforeEach(inject(function($controller, $rootScope, _$httpBackend_, _Restangular_) {
        controller = $controller;
        httpBackend = _$httpBackend_;
        Restangular = _Restangular_;
        scope = $rootScope;
    }));

    it('iterations should be sorted desc lexicographically on the iteration number by default', function () {
        var iterations = [iterationBuilder().withIterationNumber("65").build(),
            iterationBuilder().withIterationNumber("67").build()];
        httpBackend.expectGET("/resources/iterations").respond(JSON.stringify(iterations));
        IterationsCtrl = controller('IterationsCtrl', { $scope: scope, $httpBackend: httpBackend, Restangular: Restangular});
        httpBackend.flush();
        expect(scope.iterations[0].iterationNumber).toEqual("67");
        expect(scope.iterations[1].iterationNumber).toEqual("65");
    });
});