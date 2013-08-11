'use strict';

metricsModule.factory("MyErrorService", function($rootScope){
    var sharedService = {};
    sharedService.message = "";

    sharedService.broadCastMessage = function(msgType, msg){
        this.message = msg;
        $rootScope.$broadcast(msgType);
    }

    return sharedService;
});