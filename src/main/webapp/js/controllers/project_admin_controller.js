function ProjectAdminCtrl($scope, ProjectGateway, ProjectsGateway, MyErrorService) {
    ProjectsGateway.query(onQuery);

    $scope.select = function(id){
        var resp = ProjectGateway.get({id: id}, function(project){
            $scope.project = project;
        });
    }

    $scope.save = function(){
        if (typeof $scope.project != "undefined" && typeof $scope.project.projectName != "undefined" &&
                !isEmpty($scope.project.projectName))
            ProjectGateway.save({id: $scope.project.id}, $scope.project, onSave);
        else{
            MyErrorService.broadCastMessage(msgTypes().failure, "Please enter a project name.");
        }
    }

    $scope.new = function(){
        $scope.project = {};
    }

    $scope.delete = function(id){
        var confirmDelete = confirm("Are you sure you want to delete this project?");
        if (confirmDelete)
            ProjectGateway.delete({id: id}, onDelete);
    }

    function onDelete(project){
        MyErrorService.broadCastMessage(msgTypes().success, "Project deleted successfully.");
        $scope.project = {};
        ProjectsGateway.query(onQuery);
    }

    function onSave(project){
        MyErrorService.broadCastMessage(msgTypes().success, "Project saved successfully.");
        $scope.project = project;
        ProjectsGateway.query(onQuery);
    }

    function onQuery(projects){
        $scope.projects = projects.sort(projectComparator);
        if (projects.length == 0){
            $scope.project = {};
        }
    }
}

