'use strict'

function DashboardCtrl($scope){
    $scope.initData = function () {
        $scope.data = [
            ['Chrome', 47.0],
            ['Firefox', 33.0],
            ['IE', 20.0]
        ];
    }

    $scope.initData();
}

angular.module('metrics.directives', []).
    directive('drawPieChart', function(){
        return function (scope, element, attrs) {
            var container = $(element).attr("id");

            // watch the expression, and update the UI on change.
            scope.$watch('data', function () {
                drawPlot();
            }, true);

            var drawPlot = function () {
                var chart;
                chart = new Highcharts.Chart({
                    chart: {
                        renderTo: container
                    },
                    credits: {
                        enabled: false
                    },
                    title: {
                        text: ''
                    },
                    tooltip: {
                        pointFormat: 'sample: <b>sample</b>',
                        percentageDecimals: 1
                    },
                    plotOptions: {
                        pie: {
                            dataLabels: {
                                enabled: false
                            }
                        }
                    },
                    series: [{
                        type: 'pie',
                        name: 'Browser share',
                        data: scope.data
                    }]
                });
            }
        }
});
