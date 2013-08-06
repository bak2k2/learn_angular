'use strict';

metricsModule.factory('Project', function($resource){
    return $resource('resources/projects/:id', {}, {
        query: {method:'GET', isArray:false},
        save: {method:'POST', params:{id: '@id'}}
    });
});

metricsModule.factory('Projects', function($resource){
    return $resource('resources/projects', {}, {
        query: {method:'GET', isArray:true}
    });
});