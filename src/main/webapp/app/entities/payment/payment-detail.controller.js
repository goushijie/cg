(function() {
    'use strict';

    angular
        .module('cgApp')
        .controller('PaymentDetailController', PaymentDetailController);

    PaymentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Payment', 'Household'];

    function PaymentDetailController($scope, $rootScope, $stateParams, entity, Payment, Household) {
        var vm = this;

        vm.payment = entity;

        var unsubscribe = $rootScope.$on('cgApp:paymentUpdate', function(event, result) {
            vm.payment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
