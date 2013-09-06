function get_empcont_chart() {
    return {
        chart: {
            type: 'area'
        },
        title: {
            text: 'Employee - Contractor Trend',
            x: -20 //center
        },
        xAxis: {
            categories: []
        },
        yAxis: {
            title: {
                text: 'Employees / Contractors'
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
            name: 'Employees',
            marker: {
                enabled: false
            },
            data: []
        }, {
            fillColor: Highcharts.getOptions().colors[1],
            color: Highcharts.getOptions().colors[1],
            name: 'Contractors',
            marker: {
                enabled: false
            },
            data: []
        }]
    };
}

metricsModule.directive('empconthighchart', function () {
    return {
        restrict: 'E',
        template: '<div></div>',
        replace: true,

        link: function (scope, element, attrs) {
            scope.$watch(function() { return attrs.empcontchart; }, function() {
                if(!attrs.empcontchart) return;
                var empcontchart = scope.empcont_trend_chart;
                element.highcharts(empcontchart);
            });
        }
    }
});

function EmpContTrendCtrl($scope, $http) {
    empcontchart = get_empcont_chart();
    url = '/resources/project/totalemployeecontractors';
    $http({method: 'GET', url: url}).
        success(function(data, status, headers, config) {
            empcontchart.series[0].data = data.totalNoEmployees;
            empcontchart.series[1].data = data.totalNoContractors;
            empcontchart.xAxis.categories = data.iterationNames;
            $scope.empcont_trend_chart = empcontchart;
        });
}
