function ProjectAdminCtrl($scope, Restangular, MyErrorService) {
    var projects = Restangular.all('projects');
    projects.getList().then(onQuery);
    var isNewProject = false;

    $scope.select = function(id){
        Restangular.one('projects', id).get().then(function(project){ $scope.project = project; },
            function(response){ MyErrorService.broadCastMessage(msgTypes().failure, "Unable to fetch the project.");});
    }

    $scope.save = function(){
        if (projectDetailsAreValid())
            saveOrCreate();
        else
            MyErrorService.broadCastMessage(msgTypes().failure, "Please enter a project name.");
    }

    $scope.new = function(){
        $scope.project = {};
        isNewProject = true;
    }

    $scope.delete = function(id){
        var confirmDelete = confirm("Are you sure you want to delete this project?");
        if (confirmDelete)
            Restangular.one('projects', id).remove().then(onDelete,
                function(response){ MyErrorService.broadCastMessage(msgTypes().failure, "Unable to delete the project.");});
    }

    function projectDetailsAreValid(){
        return typeof $scope.project != "undefined" &&
               typeof $scope.project.projectName != "undefined" &&
               !isEmpty($scope.project.projectName);
    }

    function saveOrCreate() {
        if (isNewProject)
            projects.post($scope.project).then(onSave, function(response){ MyErrorService.broadCastMessage(msgTypes().failure, "Unable to save the project.");});
        else
            $scope.project.put().then(onSave, function(response){ MyErrorService.broadCastMessage(msgTypes().failure, "Unable to save the project.");})
    }

    function onDelete(){
        MyErrorService.broadCastMessage(msgTypes().success, "Project deleted successfully.");
        $scope.project = {};
        isNewProject = true;
        projects.getList().then(onQuery);
    }

    function onSave(){
        MyErrorService.broadCastMessage(msgTypes().success, "Project saved successfully.");
        isNewProject = false;
        projects.getList().then(onQuery);
    }

    function onQuery(projects){
        $scope.projects = projects.sort(projectComparator);
        if (projects.length == 0){
            $scope.project = {};
            isNewProject = true;
        }
    }
}

