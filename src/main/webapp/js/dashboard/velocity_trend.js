function get_chart() {
    return {
        xAxis: {
            categories: ['Jan', 'Jan', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
        },
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

function VelocityTrendCtrl($scope) {
    $scope.velocity_trend_chart = get_chart();
}
