angular.module('gpro').controller('TaskController',
		function($scope, $http, $log, taskServices, $uibModal, $document) {
			$scope.title='TASKS';
			$scope.data = [];
			$scope.instance = {};
			$scope.list = {};
			$scope.est = false;
			$scope.lst = false;
			$scope.instanceM = false;
			$scope.instanceL = false;
			$scope.isCollapsed = false;
			$scope.sort;

			//---------------------------MODAL CONTROL-----------------------------
			var $ctrl = this;
			$ctrl.animationsEnabled = true;

			$ctrl.open = function (size, parentSelector) {
			    var parentElem = parentSelector ? 
			      angular.element($document[0].querySelector('.modal-demo ' + parentSelector)) : undefined;
			    var modalInstance = $uibModal.open({
			      animation: $ctrl.animationsEnabled,
			      ariaLabelledBy: 'modal-title',
			      ariaDescribedBy: 'modal-body',
			      templateUrl: 'myModalContent.html',
			      controller: 'ModalInstanceCtrl',
			      controllerAs: '$ctrl',
			      size: size,
			      appendTo: parentElem,
			      resolve: {
			      }
			    });

			    modalInstance.result.then(function () {
			    	$scope.refresh();
			    	$log.info('Modal save successfully at: ' + new Date());
			    }, function () {
			    	$log.info('Modal dismissed at: ' + new Date());
			    });
			  };
			//---------------------------MODAL CONTROL-----------------------------
			
			  
			// Refresca los datos
			$scope.refresh=function(){
				$scope.instanceM = false;
				// voy al servicio getAll de tareas
				taskServices.list().then(
						function(resp){
							$scope.data=resp.data;
						}, function(err){
					
						});
			};
			
			// Añade una tarea
			$scope.addTask=function(){
				// Va al sercicio POST, utilizando add en task.service.js
				taskServices.add($scope.instance).then(
						function(resp){
							$scope.data.push(resp.data);
							$scope.instance={};
						}, function(err){
					
						});
			};
			
			// Borra una tarea
			$scope.deleteTask=function(id){
				if(!confirm("Are you sure you want to delete this task?"))
					return;
				// va al servicio DELETE a traves de task.service.js
				taskServices.remove(id).then(
						function(resp){
							if(resp.status==200){
								$scope.data.forEach(function(o, i){
									if(o.id==id){
										$scope.data.splice(i, 1);
										$scope.refresh;
										$scope.instance={};
									}
								});
							}
						}, function(err){
					
						});
			};
			
			// para cambiar la estimacion
			$scope.startEdit=function(t){
				$scope.instanceM=t;
				$scope.est=t.estimate;
			};
			
			// para cambiar la lista
			$scope.startChangeList=function(t){
				$scope.instanceL=t;
				$scope.lst=t.listName.name;
			};
			
			
			// edita los valores modificados
			$scope.editTask=function(t){
				var i = t;
				if(i.estimate>=0){
					// va al servicio UPDATE a traves de task.service.js
					taskServices.update(t, t.id).then(function(resp){
						$log.info("resolvio");
						$scope.instanceM=false;
						$scope.refresh();
					}, function(err){
						$log.info("error");
					});
				}else{
					confirm("Invalid Inputs");
				}
			};
			
			//editListTask(instanceM)
			$scope.changeListTask=function(t){
				var i = t;
				if(i.listName.name.length>=0){
					if(!confirm("Are you sure you want to change the list of this task?"))
						return;
					// va al servicio UPDATE a traves de task.service.js
					taskServices.update(t, t.id).then(function(resp){
						$scope.instanceL=false;
						$scope.refresh();
					}, function(err){
						alert("Invalid Input for List");
					});
				}else{
					alert("Invalid Inputs");
				}
			};

			
			// muestra btn guardar si se ingresaron valores validos
			$scope.showBtnSave=function(){
				var i = $scope.instance;
				return i.name && i.name.length>0 && i.priority;
			};
			
			// Cancelar 'editar'
			$scope.cancelEdit=function(){
				$scope.instanceM.estimate=$scope.est;
				$scope.instanceM=false;
			};
			
			// Cancelar 'change'
			$scope.cancelChange=function(){
				$scope.instanceL.listName.name=$scope.lst;
				$scope.instanceL=false;
			};
			
			$scope.sortBy=function(sort){
				taskServices.listTasksBy(sort).then(
		                function (resp){
		                	$scope.data = resp.data;
		                },
		                function (err) {
		                	alert("We can't order the table now.")
		                });
		    };
			
			
			$scope.refresh();

		});