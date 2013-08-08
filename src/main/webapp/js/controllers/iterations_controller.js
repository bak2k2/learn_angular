'use strict'

function IterationsCtrl($scope, IterationGateway, IterationsGateway) {

    IterationsGateway.query(onQuery);

    $scope.select = function(id){
        var resp = IterationGateway.get({id: id}, function(iteration){
            $scope.iteration = iteration;
        });
    }

    $scope.save = function(){
        IterationGateway.save({id: $scope.iteration.id}, $scope.iteration, onSave);
    }

    $scope.new = function(){
        $scope.iteration = {};
    }

    function onSave(iteration){
        $scope.iteration = iteration;
        IterationsGateway.query(onQuery);
    }

    function onQuery(iterations){
        $scope.iterations = iterations;
        if (iterations.length == 0){
            $scope.iteration = {};
        }
    }
}