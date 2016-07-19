(function() {
    'use strict';

    angular
        .module('cgApp')
        .controller('PaymentDeleteController',PaymentDeleteController);

    PaymentDeleteController.$inject = ['$uibModalInstance', 'entity', 'Payment'];

    function PaymentDeleteController($uibModalInstance, entity, Payment) {
        var vm = this;

        vm.payment = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Payment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
