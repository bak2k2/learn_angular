metricsModule.filter('switch', function () {
    return function (input, map) {
        return map[input] || '';
    }});