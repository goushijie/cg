(function() {
    'use strict';

    angular
        .module('cgApp')
        .controller('BuildingSettingDialogController', BuildingSettingDialogController);

    BuildingSettingDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BuildingSetting'];

    function BuildingSettingDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BuildingSetting) {
        var vm = this;

        vm.buildingSetting = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.buildingSetting.id !== null) {
                BuildingSetting.update(vm.buildingSetting, onSaveSuccess, onSaveError);
            } else {
                BuildingSetting.save(vm.buildingSetting, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cgApp:buildingSettingUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
