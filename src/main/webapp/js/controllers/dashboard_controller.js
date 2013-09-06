'use strict'

function DashboardCtrl($scope, $http){
    var url = '/resources/project/happinessmetrics';

    $scope.getTimes=function(n){
        return new Array(n);
    };

    $scope.max = 5;

    $http({method: 'GET', url: url}).
        success(function(data, status, headers, config) {
            $scope.commitment = data.commitment;
            $scope.engagement = data.engagement;
            $scope.perceivedValue = data.perceivedValue;
            $scope.respectTrust = data.respectTrust;
        });

}
