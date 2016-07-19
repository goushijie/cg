(function() {
    'use strict';

    angular
        .module('cgApp')
        .controller('HouseholdController', HouseholdController);

    HouseholdController.$inject = ['$scope', '$state', 'Household'];

    function HouseholdController ($scope, $state, Household) {
        var vm = this;
        
        vm.households = [];

        loadAll();

        function loadAll() {
            Household.query(function(result) {
                vm.households = result;
            });
        }
    }
})();
