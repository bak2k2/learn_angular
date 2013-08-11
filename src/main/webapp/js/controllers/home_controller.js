'use strict'

function HomeCtrl($scope, MyErrorService){
    $scope.$on(msgTypes().failure, function(){
        $("#error_control").show();
        $("#error_message").text(MyErrorService.message);
        setTimeout(function(){
            $("#error_control").fadeOut("slow", function () {
                $("#error_control").hide();
            });

        }, 2000);
    });

    $scope.$on(msgTypes().success, function(){
        $("#success_control").show();
        $("#success_message").text(MyErrorService.message);
        setTimeout(function(){
            $("#success_control").fadeOut("slow", function () {
                $("#success_control").hide();
            });

        }, 2000);
    });

}