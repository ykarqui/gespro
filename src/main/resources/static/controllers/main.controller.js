angular.module('gpro').controller('MainController', 
		function($rootScope, $scope){
			$scope.titulo = "Menú";
			
			$rootScope.authInfo(true);
		});