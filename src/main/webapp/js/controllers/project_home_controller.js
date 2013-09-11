'use strict'

function ProjectHomeCtrl($scope, Restangular, MyErrorService) {
    $scope.editMode = false;
    $scope.selectedIterationId;
    $scope.originalIteration = {};
    $scope.iterations = {};

    Restangular.all('iterations').getList().then(function(iterations){
        $scope.iterations = iterations.sort(iterationComparator);
    });

    Restangular.all('projects').getList().then(function(projects){
        $scope.projects = projects.sort(projectComparator);
        if (projects.length > 0){
            $scope.select(projects[0].id);
        }
    });

    $scope.selectIteration = function(){
        Restangular.one('project', $scope.project.id).one('iteration', $scope.selectedIterationId).get().then(
            function(projIterationDetails){
                $scope.projectIterationDetails = projIterationDetails;
            });
    }

    $scope.select = function(id){
        Restangular.one('projects', id).get().then(function(project){
            setProject(project);
            $scope.editMode = false;
        });
    }

    $scope.edit = function(){
        $scope.editMode = true;
    }

    $scope.cancel = function(){
        $scope.editMode = false;
        $scope.project.lastIteration = $scope.originalIteration;
    }

    $scope.save = function(){
        stripLastIterationOfRestangularAttributes();
        $scope.project.put().then(onSave);
    }

    $scope.applyIterationChanges = function(){
        if (typeof $scope.selectedIterationId == "string")
            $scope.projectIterationDetails.post().then(onIterationDetailsSave);
        else
            MyErrorService.broadCastMessage(msgTypes().failure, "Please select an iteration.");
    }

    function setProject(project){
        var its = _.find($scope.iterations, {id: project.lastIteration.id})
        if (its != "undefined")
            project.lastIteration = its;
        $scope.project = project;
        $scope.originalIteration = project.lastIteration;
        $scope.selectedIterationId = {};
        $scope.projectIterationDetails = {};
    }

    function onIterationDetailsSave(projIterDetails){
        MyErrorService.broadCastMessage(msgTypes().success, "Iteration details saved successfully.");
        $scope.projectIterationDetails = projIterDetails;
    }

    function onSave(){
        MyErrorService.broadCastMessage(msgTypes().success, "Project saved successfully.");
        $scope.editMode = false;
        setProject($scope.project);
    }

    function stripLastIterationOfRestangularAttributes(){
        $scope.project.lastIteration = cloneData($scope.project.lastIteration);
        delete $scope.project.lastIteration['route'];
        delete $scope.project.lastIteration['parentResource'];
        delete $scope.project.lastIteration['restangularCollection'];
    }
}