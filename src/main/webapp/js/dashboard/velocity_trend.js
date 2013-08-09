function get_chart() {
    return {
        title: {
            text: 'Velocity Trend',
            x: -20 //center
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
            name: 'Velocity',
            data: [29.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]
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
    url = '/resources/project/8dfb0d41-2e89-474d-8017-a1d3e78adc9d/velocities';
    $http({method: 'GET', url: url}).
        success(function(data, status, headers, config) {
            //chart.series[0].data = [10,23,14,11,5,21];
            chart.series[0].data = data;
            $scope.velocity_trend_chart = chart;

        });
}
