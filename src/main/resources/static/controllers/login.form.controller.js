angular.module('gpro')
.controller('LoginFormController', ['$rootScope', '$scope', '$location','$window','$log', '$uibModalInstance', 'coreService','user',  LoginFormController]);
function LoginFormController($rootScope, $scope, $location, $window, $log, $uibModalInstance, coreService,user) {
	$scope.title="Log In";
	$scope.user=user;	
	$scope.login = function (us) {
		$log.log("Method Log In");
		coreService.login(us).then(
			function(resp){
				$log.log("after Core Service");
				if(resp.status===200) {
					$log.log("Receive 200");
					$rootScope.loginOpen=false;
					$uibModalInstance.dismiss();
					$rootScope.user.fullName=resp.data.name;
					$rootScope.user.name=resp.data.username;
					$rootScope.user.roles = resp.data.roles;
					$rootScope.auth=true;
					if($rootScope.cbauth)
						$rootScope.cbauth();
				}else{
					$log.log("Receive !200");
					$rootScope.auth=false;					
					$rootScope.user.roles=[];
					$rootScope.openLoginForm();
				}
			},
			function(respErr){
				$log.log(respErr);
			}
		);
	  };
}
