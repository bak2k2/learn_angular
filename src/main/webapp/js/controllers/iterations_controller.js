'use strict'

function IterationsCtrl($scope, IterationGateway, IterationsGateway) {

    IterationsGateway.query(function(iterations){
        $scope.iterations = iterations;
        if (iterations.length > 0){
            $scope.select(iterations[0].id);
        }
        else {
             $scope.iteration = {};
        }
    });

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
    }

}