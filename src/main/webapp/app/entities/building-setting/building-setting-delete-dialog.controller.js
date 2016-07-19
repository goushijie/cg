(function() {
    'use strict';

    angular
        .module('cgApp')
        .controller('BuildingSettingDeleteController',BuildingSettingDeleteController);

    BuildingSettingDeleteController.$inject = ['$uibModalInstance', 'entity', 'BuildingSetting'];

    function BuildingSettingDeleteController($uibModalInstance, entity, BuildingSetting) {
        var vm = this;

        vm.buildingSetting = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BuildingSetting.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
