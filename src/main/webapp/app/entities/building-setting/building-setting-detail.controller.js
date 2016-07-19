(function() {
    'use strict';

    angular
        .module('cgApp')
        .controller('BuildingSettingDetailController', BuildingSettingDetailController);

    BuildingSettingDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'BuildingSetting'];

    function BuildingSettingDetailController($scope, $rootScope, $stateParams, entity, BuildingSetting) {
        var vm = this;

        vm.buildingSetting = entity;

        var unsubscribe = $rootScope.$on('cgApp:buildingSettingUpdate', function(event, result) {
            vm.buildingSetting = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
