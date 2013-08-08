'use strict';

metricsModule.factory('ProjectGateway', function($resource){
    return $resource('resources/projects/:id', {}, {
        get: {method:'GET', isArray:false},
        save: {method:'POST', params:{id: '@id'}},
        delete: {method:'DELETE', params:{id: '@id'}}
    });
});

metricsModule.factory('ProjectsGateway', function($resource){
    return $resource('resources/projects', {}, {
        query: {method:'GET', isArray:true}
    });
});