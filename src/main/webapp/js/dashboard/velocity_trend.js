function get_velocity_chart() {
    return {
        title: {
            text: 'Velocity - Transition - Trend',
            x: -20 //center
        },
        xAxis: {
            categories: []
        },
        yAxis: {
            title: {
                text: ''
            }
        },
        credits: {
            enabled: false
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
            fillColor: Highcharts.getOptions().colors[2],
            color: Highcharts.getOptions().colors[2],
            data: []
        },
            {
                name: 'Average Transition',
                fillColor: Highcharts.getOptions().colors[1],
                color: Highcharts.getOptions().colors[1],
                data: []
            }]
    };
}

metricsModule.directive('velocityhighchart', function () {
    return {
        restrict: 'E',
        template: '<div></div>',
        replace: true,

        link: function (scope, element, attrs) {
            scope.$watch(function() { return attrs.velocitychart; }, function() {
                if(!attrs.velocitychart) return;
                var velocitychart = scope.velocity_trend_chart;
                element.highcharts(velocitychart);
            });
        }
    }
});

function VelocityTrendCtrl($scope, $http) {
    velocitychart = get_velocity_chart();
    url = '/resources/project/averagevelocities';
    $http({method: 'GET', url: url}).
        success(function(data, status, headers, config) {
            velocitychart.series[0].data = data.averageVelocities;
            velocitychart.series[1].data = data.averageTransitions;
            velocitychart.xAxis.categories = data.iterationNames;
            $scope.velocity_trend_chart = velocitychart;
        });
}
