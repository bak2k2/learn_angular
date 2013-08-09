function get_cycletime_chart() {
    return {
        title: {
            text: 'Cycle Time Trend',
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
            name: 'Average Cycle Time',
            data: []
        }]
    };
}

app.directive('cyclehighchart', function () {
    return {
        restrict: 'E',
        template: '<div></div>',
        replace: true,

        link: function (scope, element, attrs) {
            scope.$watch(function() { return attrs.chartc; }, function() {
                if(!attrs.chartc) return;
                var chartc = scope.cycletime_trend_chart;
                element.highcharts(chartc);
            });
        }
    }
});

function CycleTimeTrendCtrl($scope, $http) {
    chartc = get_cycletime_chart();
    //url = '/resources/project/90699d25-23a0-4908-b21d-d20182768d84/velocities';
    url = '/resources/project/averagecycletimes';
    $http({method: 'GET', url: url}).
        success(function(data, status, headers, config) {
            chartc.series[0].data = data.averageCycleTimes;
            chartc.xAxis.categories = data.iterationNames;
            $scope.cycletime_trend_chart = chartc;
        });
}
