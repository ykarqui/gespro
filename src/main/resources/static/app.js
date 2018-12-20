//En angular, el llamado angular.module() con...
//...dos parametros: setter
var app = angular.module('gpro', [ 'ngRoute', 'ngAnimate', 'ngSanitize',
		'ui.bootstrap', 'ngSanitize', 'ngTouch', 'ui.bootstrap', 'dndLists',
		'angularUtils.directives.dirPagination', 'angucomplete-alt',
		'ngLoadingSpinner', 'ui.select', 'adaptv.adaptStrap', 'ngDragDrop',
		'ui-notification', 'chart.js', 'ngStomp', 'uiSwitch', 'ngStomp', 'smart-table' ]);

app.constant('URL_BASE', '/');
app.constant('URL_API_BASE', '/api/v1/');
app.constant('URL_LIST', '/api/v1/list');
app.constant('URL_TASK', '/api/v1/task');
app.constant('URL_WS', '/api/v1/ws');

// ...un parametro: getter
app.run([ '$location', '$log', '$rootScope', 'coreService', '$uibModal',
		function($location, $log, $rootScope, coreService, $uibModal) {
			$log.log('INICIANDO');
			
			$rootScope.relocate = function(loc) {
				$location.path(loc);
			};
			
			$rootScope.cleanLoginData = function() {
				$rootScope.auth = false;
				$rootScope.user = {
					fullName: "",
					name : "",
					password : "",
					roles : []
				};
			};
			
			$rootScope.cleanLoginData();
			
			$rootScope.openLoginForm = function(size) {
				if (!$rootScope.loginOpen) {
					$rootScope.cleanLoginData();
					$rootScope.loginOpen = true;
					$uibModal.open({
						animation : true,
						backdrop : 'static',
						keyboard : false,
						templateUrl : 'views/loginForm.html',
						controller : 'LoginFormController',
						size : size,
						resolve : {
							user : function() {
								return $rootScope.user
							}
						}
					});
				}
			};
			//Callback luego de autenticación
			$rootScope.cbauth=false;
			$rootScope.authInfo=function(cb) {
				if(cb) $rootScope.cbauth=cb;
				coreService.authInfo().then(function(resp){
					if(resp.status===200) {
						$rootScope.user.fullName=resp.data.name;
						$rootScope.user.name=resp.data.username;
						$rootScope.user.roles = resp.data.roles;
						$rootScope.auth=true;
					}else{
						$rootScope.auth=false;					
						$rootScope.user.roles=[];
					}
				});
			}

			$rootScope.logout = function(callAuthInfo) {						
				coreService.logout().then(function(r){
					$rootScope.cleanLoginData();
					if(callAuthInfo) {
						$rootScope.authInfo();
					}
				},function(){});
			};
}]);

app.filter('changeDate', function() {
	return function(inp) {
		var input = new Date(inp).toISOString();
		return input;
	};
});
