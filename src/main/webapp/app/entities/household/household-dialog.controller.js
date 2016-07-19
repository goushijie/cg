(function() {
    'use strict';

    angular
        .module('cgApp')
        .controller('HouseholdDialogController', HouseholdDialogController);

    HouseholdDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Household', 'Payment', 'Owner', 'News'];

    function HouseholdDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Household, Payment, Owner, News) {
        var vm = this;

        vm.household = entity;
        vm.clear = clear;
        vm.save = save;
        vm.payments = Payment.query();
        vm.owners = Owner.query();
        vm.news = News.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.household.id !== null) {
                Household.update(vm.household, onSaveSuccess, onSaveError);
            } else {
                Household.save(vm.household, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cgApp:householdUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
