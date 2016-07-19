(function() {
    'use strict';

    angular
        .module('cgApp')
        .controller('NewsDialogController', NewsDialogController);

    NewsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'News', 'Household'];

    function NewsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, News, Household) {
        var vm = this;

        vm.news = entity;
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
            if (vm.news.id !== null) {
                News.update(vm.news, onSaveSuccess, onSaveError);
            } else {
                News.save(vm.news, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cgApp:newsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
