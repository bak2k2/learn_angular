function get_empcont_chart() {
    return {
        title: {
            text: 'Employee Contractor Trend',
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
            name: 'Employees',
            data: []
        }, {
            name: 'Contractors',
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
