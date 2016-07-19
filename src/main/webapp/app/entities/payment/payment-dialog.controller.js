(function() {
    'use strict';

    angular
        .module('cgApp')
        .controller('PaymentDialogController', PaymentDialogController);

    PaymentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Payment', 'Household'];

    function PaymentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Payment, Household) {
        var vm = this;

        vm.payment = entity;
        vm.clear = clear;
        vm.save = save;
        vm.households = Household.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.payment.id !== null) {
                Payment.update(vm.payment, onSaveSuccess, onSaveError);
            } else {
                Payment.save(vm.payment, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cgApp:paymentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
