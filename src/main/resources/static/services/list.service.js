angular.module('gpro').factory('listServices', function($http, URL_LIST){	
	return {
		getTasksBy: function(name, sort){
            return $http.get(URL_LIST + '/' + name + "?ob=" + sort);
        },
		list: function(){
			return $http.get(URL_LIST);
		},
		add: function(l){
			return $http.post(URL_LIST, l);
		},
		update: function(l){
			return $http.put(URL_LIST, l);
		},
		remove: function(id){
			return $http.delete(URL_LIST + "?id=" + id);
		}
	};
});