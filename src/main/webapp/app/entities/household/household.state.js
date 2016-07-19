(function() {
    'use strict';

    angular
        .module('cgApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('household', {
            parent: 'entity',
            url: '/household',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cgApp.household.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/household/households.html',
                    controller: 'HouseholdController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('household');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('household-detail', {
            parent: 'entity',
            url: '/household/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cgApp.household.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/household/household-detail.html',
                    controller: 'HouseholdDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('household');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Household', function($stateParams, Household) {
                    return Household.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('household.new', {
            parent: 'household',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/household/household-dialog.html',
                    controller: 'HouseholdDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                householdId: null,
                                householdAddress: null,
                                householdArea: null,
                                householdPropertyfee: null,
                                householdGarbagefee: null,
                                lightAndWater: null,
                                presentValue: null,
                                homeOwnersName: null,
                                homeOwnersPhone: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('household', null, { reload: true });
                }, function() {
                    $state.go('household');
                });
            }]
        })
        .state('household.edit', {
            parent: 'household',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/household/household-dialog.html',
                    controller: 'HouseholdDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Household', function(Household) {
                            return Household.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('household', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('household.delete', {
            parent: 'household',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/household/household-delete-dialog.html',
                    controller: 'HouseholdDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Household', function(Household) {
                            return Household.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('household', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
