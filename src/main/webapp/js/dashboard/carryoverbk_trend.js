function get_carryoverbk_chart() {
    return {
        title: {
            text: 'Carryover - Blocker Trend',
            x: -20 //center
        },
        xAxis: {
            categories: []
        },
        yAxis: {
            title: {
                text: 'Carryover / Blockers'
            }
        },
        credits: {
            enabled: false
        },
        plotOptions: {
            areaspline: {
                fillOpacity: 0.5
            },
            area: {
                stacking: 'normal',
                lineColor: '#666666',
                lineWidth: 1,
                marker: {
                    lineWidth: 1,
                    lineColor: '#666666'
                }
            }
        },

        series: [{
            fillColor: Highcharts.getOptions().colors[2],
            color: Highcharts.getOptions().colors[2],
            name: 'Carryover',
            marker: {
                enabled: false
            },
            data: []
        }, {
            fillColor: Highcharts.getOptions().colors[1],
            color: Highcharts.getOptions().colors[1],
            name: 'Blocker',
            marker: {
                enabled: false
            },
            data: []
        }]
    };
}

metricsModule.directive('carryoverbkhighchart', function () {
    return {
        restrict: 'E',
        template: '<div></div>',
        replace: true,

        link: function (scope, element, attrs) {
            scope.$watch(function() { return attrs.carryoverbkchart; }, function() {
                if(!attrs.carryoverbkchart) return;
                var carryoverbkchart = scope.carryoverbk_trend_chart;
                element.highcharts(carryoverbkchart);
            });
        }
    }
});

function CarryoverBlockerTrendCtrl($scope, $http) {
    carryoverbkchart = get_carryoverbk_chart();
    url = '/resources/project/carryoverblockers';
    $http({method: 'GET', url: url}).
        success(function(data, status, headers, config) {
            carryoverbkchart.series[0].data = data.carryOvers;
            carryoverbkchart.series[1].data = data.blockers;
            carryoverbkchart.xAxis.categories = data.iterationNames;
            $scope.carryoverbk_trend_chart = carryoverbkchart;
        });
}
