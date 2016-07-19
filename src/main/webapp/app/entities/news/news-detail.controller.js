(function() {
    'use strict';

    angular
        .module('cgApp')
        .controller('NewsDetailController', NewsDetailController);

    NewsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'News', 'Household'];

    function NewsDetailController($scope, $rootScope, $stateParams, entity, News, Household) {
        var vm = this;

        vm.news = entity;

        var unsubscribe = $rootScope.$on('cgApp:newsUpdate', function(event, result) {
            vm.news = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
