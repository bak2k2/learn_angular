'use strict'

function ProjectHomeCtrl($scope, ProjectGateway, ProjectsGateway, IterationsGateway, ProjectIterationGateway) {
    $scope.editMode = false;
    $scope.selectedIterationId = {};

    $scope.iterations = {};

    IterationsGateway.query(function(iterations){
        $scope.iterations = iterations;
    });

    ProjectsGateway.query(function(projects){
        $scope.projects = projects;
        if (projects.length > 0){
            $scope.select(projects[0].id);
        }
    });

    $scope.selectIteration = function(){
        ProjectIterationGateway.get({projectId: $scope.project.id, iterationId: $scope.selectedIterationId},
            function(projIterationDetails){
                $scope.projectIterationDetails = projIterationDetails;
            });
    }

    $scope.select = function(id){
        var resp = ProjectGateway.get({id: id}, function(project){
            setProject(project);
            setEditMode(false);
        });
    }

    $scope.edit = function(){
        setEditMode(true);
    }

    $scope.cancel = function(){
        setEditMode(false);
    }

    $scope.save = function(){
        ProjectGateway.save({id: $scope.editable_project.id}, $scope.editable_project, onSave);
    }

    $scope.applyIterationChanges = function(){
        ProjectIterationGateway.save({projectId: $scope.project.id, iterationId: $scope.selectedIterationId}, $scope.projectIterationDetails, function(projIterDetails){
            console.log("saved successfully");
            $scope.projectIterationDetails = projIterDetails;
        });
    }

    function setProject(project){
        $scope.project = project;
        $scope.selectedIterationId = {};
    }

    function onSave(project){
        setProject(project);
        setEditMode(false);
        $scope.$broadcast('metricsApp.projectSaved');
    }

    function setEditMode(isEditMode){
        $scope.editMode = isEditMode;
        $scope.editable_project = cloneData($scope.project);
    }

    $scope.$on('metricsApp.projectSaved', function(){
        $scope.projects = ProjectsGateway.query();
    });

}