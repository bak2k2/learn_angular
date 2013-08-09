function get_empcont_chart() {
    return {
        chart: {
            type: 'area'
        },
        title: {
            text: 'Employee Contractor Trend',
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
        plotOptions: {
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
            fillColor: '#2288C0',
            color: '#2288C0',
            name: 'Employees',
            marker: {
                enabled: false
            },
            data: []
        }, {
            fillColor: '#10425D',
            color: '#10425D',
            name: 'Contractors',
            marker: {
                enabled: false
            },
            data: []
        }]
    };
}

app.directive('empconthighchart', function () {
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
    url = '/resources/project/averageemployeecontractors';
    $http({method: 'GET', url: url}).
        success(function(data, status, headers, config) {
            empcontchart.series[0].data = data.averageNoEmployees;
            empcontchart.series[1].data = data.averageNoContractors;
            empcontchart.xAxis.categories = data.iterationNames;
            $scope.empcont_trend_chart = empcontchart;
        });
}
