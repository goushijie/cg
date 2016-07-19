(function() {
    'use strict';

    angular
        .module('cgApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('building-setting', {
            parent: 'entity',
            url: '/building-setting',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cgApp.buildingSetting.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/building-setting/building-settings.html',
                    controller: 'BuildingSettingController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('buildingSetting');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('building-setting-detail', {
            parent: 'entity',
            url: '/building-setting/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cgApp.buildingSetting.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/building-setting/building-setting-detail.html',
                    controller: 'BuildingSettingDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('buildingSetting');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'BuildingSetting', function($stateParams, BuildingSetting) {
                    return BuildingSetting.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('building-setting.new', {
            parent: 'building-setting',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/building-setting/building-setting-dialog.html',
                    controller: 'BuildingSettingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                buildingId: null,
                                buildingName: null,
                                buildingAddress: null,
                                buildingNumber: null,
                                unitNumber: null,
                                householdNumber: null,
                                countTier: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('building-setting', null, { reload: true });
                }, function() {
                    $state.go('building-setting');
                });
            }]
        })
        .state('building-setting.edit', {
            parent: 'building-setting',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/building-setting/building-setting-dialog.html',
                    controller: 'BuildingSettingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BuildingSetting', function(BuildingSetting) {
                            return BuildingSetting.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('building-setting', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('building-setting.delete', {
            parent: 'building-setting',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/building-setting/building-setting-delete-dialog.html',
                    controller: 'BuildingSettingDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BuildingSetting', function(BuildingSetting) {
                            return BuildingSetting.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('building-setting', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
