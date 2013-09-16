'use strict';

function ReportsHomeCtrl($scope, $http, switchFilter, MyErrorService) {

    var url = '/resources/iterations/reports';

    $http({method: 'GET', url: url}).
        success(function(data, status, headers, config) {
            $scope.iterationReports = data;
            $scope.iterationReport = $scope.iterationReports[0];
        });

    $scope.sendMail = function(projectId, iterationId){
        var mailReportUrl = 'resources/mailreport/' + projectId + '/' + iterationId;

        $http({method: 'POST', url: mailReportUrl}).
            success(function(data,status,headers,config){
                MyErrorService.broadCastMessage(msgTypes().success, "Email sent successfully.");
            });
    }

}