'use strict';

metricsModule.factory('ProjectIterationGateway', function($resource){
    return $resource('resources/project/:projectId/iteration/:iterationId', {}, {
        get: {method:'GET', isArray:false},
        save: {method:'POST', params:{projectId: '@projectId', iterationId: '@iterationId'}},
    });
});
