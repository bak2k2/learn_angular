'use strict'

function ProjectHomeCtrl($scope, ProjectGateway, ProjectsGateway, IterationsGateway, ProjectIterationGateway) {
    $scope.editMode = false;
    $scope.selectedIterationId = {};

    $scope.iterations = {};

    IterationsGateway.query(function(iterations){
        $scope.iterations = iterations.sort(iterationComparator);
    });

    ProjectsGateway.query(function(projects){
        $scope.projects = projects.sort(projectComparator);
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
        if (typeof $scope.selectedIterationId == "string")
            ProjectIterationGateway.save({projectId: $scope.project.id, iterationId: $scope.selectedIterationId}, $scope.projectIterationDetails, function(projIterDetails){
                $scope.projectIterationDetails = projIterDetails;
            });
        else
            alert("Please select an iteration to apply changes");
    }

    function setProject(project){
        $scope.project = project;
        $scope.selectedIterationId = {};
        $scope.projectIterationDetails = {};
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
        var projects = ProjectsGateway.query();
        $scope.projects = projects.sort(projectComparator);
    });
}