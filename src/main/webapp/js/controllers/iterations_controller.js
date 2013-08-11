'use strict'

metricsModule.directive('uiDate', function() {
    return {
        require: '?ngModel',
        link: function($scope, element, attrs, controller) {
            var originalRender, updateModel, usersOnSelectHandler;
            if ($scope.uiDate == null) $scope.uiDate = {};
            if (controller != null) {
                updateModel = function(value, picker) {
                    return $scope.$apply(function() {
                        var dt = new Date(element.datepicker("getDate"));
                        var formattedDate = dt.format("mm/dd/yyyy");
                        return controller.$setViewValue(formattedDate);
                    });
                };
                if ($scope.uiDate.onSelect != null) {
                    usersOnSelectHandler = $scope.uiDate.onSelect;
                    $scope.uiDate.onSelect = function(value, picker) {
                        updateModel(value);
                        return usersOnSelectHandler(value, picker);
                    };
                } else {
                    $scope.uiDate.onSelect = updateModel;
                }
            }
            return element.datepicker($scope.uiDate);
        }
    };
});

function IterationsCtrl($scope, IterationGateway, IterationsGateway, MyErrorService) {

    IterationsGateway.query(onQuery);

    $scope.select = function(id){
        var resp = IterationGateway.get({id: id}, function(iteration){
            $scope.iteration = iteration;
        });
    }

    $scope.save = function(){
        if (typeof $scope.iteration != "undefined" && typeof $scope.iteration.iterationNumber != "undefined" &&
                !isEmpty($scope.iteration.iterationNumber))
            IterationGateway.save({id: $scope.iteration.id}, $scope.iteration, onSave);
        else
            MyErrorService.broadCastMessage(msgTypes().failure, "Please enter an iteration #.");
    }

    $scope.new = function(){
        $scope.iteration = {};
    }

    $scope.delete = function(id){
        var confirmDelete = confirm("Are you sure you want to delete this iteration?");
        if (confirmDelete)
            IterationGateway.delete({id: id}, onDelete);
    }

    function onDelete(iteration){
        MyErrorService.broadCastMessage(msgTypes().success, "Iteration deleted successfully.");
        $scope.iteration = {};
        IterationsGateway.query(onQuery);
    }

    function onSave(iteration){
        MyErrorService.broadCastMessage(msgTypes().success, "Iteration saved successfully.");
        $scope.iteration = iteration;
        IterationsGateway.query(onQuery);
    }

    function onQuery(iterations){
        $scope.iterations = iterations.sort(iterationComparator);
        if (iterations.length == 0){
            $scope.iteration = {};
        }
    }
}