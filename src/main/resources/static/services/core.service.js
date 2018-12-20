angular.module('gpro').factory('coreService',
		['$http', 'URL_BASE', '$log', function($http, URL_BASE, $log) {
			return {
				logout: function() {
					return $http.get(URL_BASE+"logout");
				},
				authInfo: function() {
					 return $http.get(URL_BASE+"authinfo");
				},
				login: function(user) {
					$log.info("user: "+user.name);
					$log.info("pass: "+user.password);
					var req = {
						method: 'POST',
						url: URL_BASE+'dologin',
						headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
						data: 'remember-me=true&username='+user.name+'&password='+user.password
					};
					return $http(req);
				}
		} 
	}
]);