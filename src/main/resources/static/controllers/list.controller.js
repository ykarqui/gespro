angular.module('gpro').controller('ListController', 
		function($scope, $http, $log, listServices, taskServices, $uibModal, $document){
			$scope.title = "LISTS";
			$scope.est=false;
			$scope.data=[];
			$scope.tasks=[];
			$scope.instance={};
			$scope.instanceM=false;
			$scope.isCollapsed = true;
			$scope.date = new Date();
		    $scope.listas=[];	
			$scope.tareas={};
			
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
			
			//Refresca los datos
			$scope.refresh=function(){
				//voy al servicio getAll de listas
				listServices.list().then(
						function(resp){
							$scope.data=resp.data;
						}, function(err){
					
						});
			};
			
			//Añade una lista
			$scope.addList=function(){
				//Va al sercicio POST, utilizando add en list.service.js
				listServices.add($scope.instance).then(
						function(resp){
							$scope.data.push(resp.data);
							$scope.instance={};
						}, function(err){
					
						});
			};
			
			//Borra una lista
			$scope.deleteList=function(id){
				if(!confirm("Are you sure you want to delete the list and remove its tasks?"))
					return;
				//va al servicio DELETE a traves de list.service.js
				listServices.remove(id).then(
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
			
			$scope.startEdit=function(l){
				$scope.instance=l;
			};
			
			$scope.startTaskEdit=function(t,l){
				$scope.instanceM=t;
				$scope.instanceM.listName={};
				$scope.instanceM.listName.id=l.id;
				$scope.instanceM.listName.name=l.name;
				$scope.instanceM.listName.sprintName=l.sprintName;
				$scope.est=t.estimate;
			};
			
			//edita los valores modificados de la lista
			$scope.editList=function(){
				var i = $scope.instanceM;
				if(i.name && i.name.length>0 && i.sprintName && i.sprintName.length>0){
					//va al servicio UPDATE a traves de list.service.js
					listServices.update($scope.instanceM).then(function(resp){
						$scope.instanceM=false;
						$scope.refresh();
					}, function(err){
						
					});
				}else{
					confirm("Invalid Inputs");
				}
			};
			
			// edita los valores modificados de la tarea
			$scope.editTask=function(t){
				if(t.estimate>=0){
					// va al servicio UPDATE a traves de task.service.js
					taskServices.update(t, t.id).then(function(resp){
						$scope.instanceM=false;
						$scope.refresh();
					}, function(err){
						$log.error("Failed because " + err);
					});
				}else{
					confirm("Invalid Inputs");
				}
			};
			
			//muestra btn guardar si se ingresaron valores validos
			$scope.showBtnSave=function(){
				var i = $scope.instance;
				return i.name && i.name.length>0 && i.sprintName && i.sprintName.length>0;
			};
			
			//Cancelar 'editar'
			$scope.cancel=function(){
				$scope.instanceM=false;
			};
			
			$scope.deleteTask=function(t){
				if(!confirm("Are you sure you want to delete this task?"))
					return;
				// va al servicio DELETE a traves de task.service.js
				taskServices.remove(t.id).then(
						function(resp){
							if(resp.status==200){
								$scope.refresh();
							}
						}, function(err){
					
						});
			};
			
			// Cancelar 'editTask'
			$scope.cancelEdit=function(filter){
				$scope.instanceM.estimate=$scope.est;
				$scope.instanceM=false;
			};
			
			$scope.sortBy=function(l, sort){
				listServices.getTasksBy(l.name, sort).then(
		                function (resp){
		                	for(var i in $scope.data){
		                		if($scope.data[i].id == resp.data.id){
				                	$scope.data[i] = resp.data;
		                		}
		                	}
		                },
		                function (err) {
		                	alert("We can't order the table now.")
		                });
		    };
			
			
			$scope.refresh();
});