function ProjectAdminCtrl($scope, ProjectGateway, ProjectsGateway) {
    ProjectsGateway.query(onQuery);

    $scope.select = function(id){
        var resp = ProjectGateway.get({id: id}, function(project){
            $scope.project = project;
        });
    }

    $scope.save = function(){
        ProjectGateway.save({id: $scope.project.id}, $scope.project, onSave);
    }

    $scope.new = function(){
        $scope.project = {};
    }

    $scope.delete = function(id){
        ProjectGateway.delete({id: id}, onDelete);
    }

    function onDelete(project){
        $scope.project = {};
        ProjectsGateway.query(onQuery);
    }

    function onSave(project){
        $scope.project = project;
        ProjectsGateway.query(onQuery);
    }

    function onQuery(projects){
        $scope.projects = projects;
        if (projects.length == 0){
            $scope.project = {};
        }
    }
}

