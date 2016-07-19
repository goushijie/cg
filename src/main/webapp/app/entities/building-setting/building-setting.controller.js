(function() {
    'use strict';

    angular
        .module('cgApp')
        .controller('BuildingSettingController', BuildingSettingController);

    BuildingSettingController.$inject = ['$scope', '$state', 'BuildingSetting'];

    function BuildingSettingController ($scope, $state, BuildingSetting) {
        var vm = this;
        
        vm.buildingSettings = [];

        loadAll();

        function loadAll() {
            BuildingSetting.query(function(result) {
                vm.buildingSettings = result;
            });
        }
    }
})();
