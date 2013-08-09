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
            scope.$watch(function() { return attrs.cyclechart; }, function() {
                if(!attrs.cyclechart) return;
                var cyclechart = scope.cycletime_trend_chart;
                element.highcharts(cyclechart);
            });
        }
    }
});

function CycleTimeTrendCtrl($scope, $http) {
    cyclechart = get_cycletime_chart();
    url = '/resources/project/averagecycletimes';
    $http({method: 'GET', url: url}).
        success(function(data, status, headers, config) {
            cyclechart.series[0].data = data.averageCycleTimes;
            cyclechart.xAxis.categories = data.iterationNames;
            $scope.cycletime_trend_chart = cyclechart;
        });
}
