(function() {
    'use strict';

    angular
        .module('cgApp')
        .controller('HouseholdDetailController', HouseholdDetailController);

    HouseholdDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Household', 'Payment', 'Owner', 'News'];

    function HouseholdDetailController($scope, $rootScope, $stateParams, entity, Household, Payment, Owner, News) {
        var vm = this;

        vm.household = entity;

        var unsubscribe = $rootScope.$on('cgApp:householdUpdate', function(event, result) {
            vm.household = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
