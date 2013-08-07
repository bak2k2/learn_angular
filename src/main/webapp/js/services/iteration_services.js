'use strict';

'use strict';

metricsModule.factory('IterationGateway', function($resource){
    return $resource('resources/iteration/:id', {}, {
        get: {method:'GET', isArray:false},
        save: {method:'POST', params:{id: '@id'}}
    });
});

metricsModule.factory('IterationsGateway', function($resource){
    return $resource('resources/iterations', {}, {
        query: {method:'GET', isArray:true}
    });
});