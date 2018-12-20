angular.module('gpro').config(function($routeProvider, $locationProvider, $httpProvider, $logProvider) {
			console.log('Configurando...');
			$logProvider.debugEnabled(true);
			
			$httpProvider.defaults.withCredentials = true;

			$httpProvider.interceptors.push('APIInterceptor');
		
			
			$locationProvider.hashPrefix('!');
			
			$routeProvider.when('/main', {
				templateUrl : 'views/main.html',
				controller : 'MainController'
			}).when('/list', {
				templateUrl : 'views/list.html',
				controller : 'ListController'
			}).when('/task', {
				templateUrl : 'views/task.html',
				controller : 'TaskController'
			}).otherwise({
				redirectTo : '/main'
			});
		});
