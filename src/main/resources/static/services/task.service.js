angular.module('gpro').factory('taskServices', function($http, URL_TASK, $log){	
	return {
		list: function(){
			return $http.get(URL_TASK);
		},
		listTasksBy: function(sort){
            return $http.get(URL_TASK + "?ob=" + sort);
        },
		add: function(t){
			return $http.post(URL_TASK, t);
		},
		update: function(t, id){
			return $http.put(URL_TASK + "/" + id, t);
		},
		remove: function(id){
			return $http.delete(URL_TASK + "?id=" + id);
		}
	};
});