angular.module('gpro').controller('ModalInstanceCtrl', function (taskServices, $uibModalInstance) {
	  var $ctrl = this;
	  $ctrl.data = [];
	  $ctrl.instance = {};

	  $ctrl.ok = function () {
		  taskServices.add($ctrl.instance).then(
					function(resp){
						$ctrl.data.push(resp.data);
						$ctrl.instance={};
						
						$uibModalInstance.close();
					}, function(err){
				
					});
	  };

	  $ctrl.cancel = function () {
	    $uibModalInstance.dismiss('cancel');
	  };
	});