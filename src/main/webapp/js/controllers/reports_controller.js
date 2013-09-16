'use strict';

function ReportsHomeCtrl($scope, $http, switchFilter) {

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
                alert("mail sent");
            });
        //alert(iterationId + " " + projectId);
    }

}