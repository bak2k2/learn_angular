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


function IterationsCtrl($scope, Restangular, MyErrorService) {
    var iterations = Restangular.all('iterations');
    iterations.getList().then(onQuery);
    var isNewIteration = false;

    $scope.select = function(id){
        Restangular.one('iterations', id).get().then(function(iteration){ $scope.iteration = iteration; });
    }

    $scope.save = function(){
        if (iterationDetailsAreValid())
            saveOrCreate();
        else
            MyErrorService.broadCastMessage(msgTypes().failure, "Please enter a iteration name.");
    }

    $scope.new = function(){
        $scope.iteration = {};
        isNewIteration = true;
    }

    $scope.delete = function(id){
        var confirmDelete = confirm("Are you sure you want to delete this iteration?");
        if (confirmDelete)
            Restangular.one('iterations', id).remove().then(onDelete);
    }

    function iterationDetailsAreValid(){
        return typeof $scope.iteration != "undefined" &&
            typeof $scope.iteration.iterationNumber != "undefined" &&
            !isEmpty($scope.iteration.iterationNumber);
    }

    function saveOrCreate() {
        if (isNewIteration)
            iterations.post($scope.iteration).then(onSave);
        else
            $scope.iteration.put().then(onSave)
    }

    function onDelete(){
        MyErrorService.broadCastMessage(msgTypes().success, "Iteration deleted successfully.");
        $scope.iteration = {};
        isNewIteration = true;
        iterations.getList().then(onQuery);
    }

    function onSave(){
        MyErrorService.broadCastMessage(msgTypes().success, "Iteration saved successfully.");
        isNewIteration = false;
        iterations.getList().then(onQuery);
    }

    function onQuery(iterations){
        $scope.iterations = iterations.sort(iterationComparator);
        if (iterations.length == 0){
            $scope.iteration = {};
            isNewIteration = true;
        }
    }
}

