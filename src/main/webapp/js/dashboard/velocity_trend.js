function get_chart() {
    return {
        title: {
            text: 'Velocity Trend',
            x: -20 //center
        },
        xAxis: {
            categories: []
        },
        plotOptions: {
            series: {
                cursor: 'pointer',
                point: {
                    events: {
                        click: function() {
                            alert ('Category: '+ this.category +', value: '+ this.y);
                        }
                    }
                }
            }
        },

        series: [{
            name: 'Average Velocity',
            data: []
        }]
    };
}

var app = angular.module('charts', []);

app.directive('highchart', function () {
    return {
        restrict: 'E',
        template: '<div></div>',
        replace: true,

        link: function (scope, element, attrs) {
            scope.$watch(function() { return attrs.chart; }, function() {
                if(!attrs.chart) return;
                var chart = scope.velocity_trend_chart;
                element.highcharts(chart);
            });
        }
    }
});

function VelocityTrendCtrl($scope, $http) {
    chart = get_chart();
    //url = '/resources/project/90699d25-23a0-4908-b21d-d20182768d84/velocities';
    url = '/resources/project/averagevelocities';
    $http({method: 'GET', url: url}).
        success(function(data, status, headers, config) {
            //chart.series[0].data = [10,23,14,11,5,21];
            chart.series[0].data = data.averageVelocities;
            chart.xAxis.categories = data.iterationNames;
            $scope.velocity_trend_chart = chart;

        });
}
