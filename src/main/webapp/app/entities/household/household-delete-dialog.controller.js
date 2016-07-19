(function() {
    'use strict';

    angular
        .module('cgApp')
        .controller('HouseholdDeleteController',HouseholdDeleteController);

    HouseholdDeleteController.$inject = ['$uibModalInstance', 'entity', 'Household'];

    function HouseholdDeleteController($uibModalInstance, entity, Household) {
        var vm = this;

        vm.household = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Household.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
