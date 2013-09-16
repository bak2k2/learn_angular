'use strict';

function ReportsHomeCtrl($scope, $http, switchFilter) {

    var url = '/resources/iterations/reports';

    $http({method: 'GET', url: url}).
        success(function(data, status, headers, config) {
            $scope.iterationReports = data;
            $scope.iterationReport = $scope.iterationReports[0];
        });

    $scope.sendMail = function(iterationId, projectId){
        alert(iterationId + " " + projectId);
    }

}