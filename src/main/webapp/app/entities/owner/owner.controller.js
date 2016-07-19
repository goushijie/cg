(function() {
    'use strict';

    angular
        .module('cgApp')
        .controller('OwnerController', OwnerController);

    OwnerController.$inject = ['$scope', '$state', 'Owner'];

    function OwnerController ($scope, $state, Owner) {
        var vm = this;
        
        vm.owners = [];

        loadAll();

        function loadAll() {
            Owner.query(function(result) {
                vm.owners = result;
            });
        }
    }
})();
