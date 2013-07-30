'use strict';

angular.module('projectServices', ['ngResource']).
    factory('Project', function($resource){
        return $resource('resources/projects', {}, {
            query: {method:'GET', isArray:true}
        });
    });