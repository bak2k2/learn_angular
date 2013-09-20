function get_onoffnear_chart() {
    return {
        series: [{
            name: 'onshore',
            data: []
            },
            {
                name: 'nearshore - chile',
                data: []
            },
            {
                name: 'nearshore - mexico',
                data: []
            },
            {
                name: 'nearshore - brazil',
                data: []
            },
            {
                name: 'offshore - india',
                data: []
            },
            {
                name: 'offshore - uk',
                data: []
            }
        ],

        chart: {
            polar: true,
            type: 'column'
        },

        title: {
            text: 'On - Off - Near shore composition'
        },

        subtitle: {
            text: 'Project level'
        },

        pane: {
            size: '85%'
        },

        legend: {
            reversed: true,
            align: 'right',
            verticalAlign: 'top',
            y: 100,
            layout: 'vertical'
        },

        xAxis: {
            tickmarkPlacement: 'off',
            categories: []
        },

        yAxis: {
            min: 0,
            endOnTick: false,
            showLastLabel: true,
            title: {
                text: ''
            },
            labels: {
                formatter: function () {
                    return this.value;
                }
            }
        },
        credits: {
            enabled: false
        },
        tooltip: {
            valueSuffix: '',
            followPointer: true
        },

        plotOptions: {
            series: {
                stacking: 'normal',
                shadow: false,
                groupPadding: 0,
                pointPlacement: 'on'
            }
        }
    };
}

metricsModule.directive('onoffnearhighchart', function () {
    return {
        restrict: 'E',
        template: '<div></div>',
        replace: true,

        link: function (scope, element, attrs) {
            scope.$watch(function() { return attrs.onoffnearchart; }, function() {
                if(!attrs.onoffnearchart) return;
                var onoffnearchart = scope.onoffnear_trend_chart;
                element.highcharts(onoffnearchart);
            });
        }
    }
});

function OnOffNearTrendCtrl($scope, $http) {
    onoffnearchart = get_onoffnear_chart();
    url = '/resources/project/onoffnearshoredetails';
    $http({method: 'GET', url: url}).
        success(function(data, status, headers, config) {
            onoffnearchart.series[0].data = data.onShoreCount;
            onoffnearchart.series[1].data = data.nearShoreChileCount;
            onoffnearchart.series[2].data = data.nearShoreMexicoCount;
            onoffnearchart.series[3].data = data.nearShoreBrazilCount;
            onoffnearchart.series[4].data = data.offShoreIndiaCount;
            onoffnearchart.series[5].data = data.offShoreUkCount;
            onoffnearchart.xAxis.categories = data.projectNames;
            $scope.onoffnear_trend_chart = onoffnearchart;
        });
}
