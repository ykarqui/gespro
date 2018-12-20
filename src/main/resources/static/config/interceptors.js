angular.module('gpro')
.service('APIInterceptor', function($rootScope) {
    var service = this;

    service.responseError = function(response) {
       if(response.status==401) {
    	   $rootScope.openLoginForm();
       } else {
    	   $rootScope.authInfo();
       }
       return response;
    };
})