(function() {
    'use strict';

    angular
        .module('cgApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('owner', {
            parent: 'entity',
            url: '/owner',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cgApp.owner.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/owner/owners.html',
                    controller: 'OwnerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('owner');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('owner-detail', {
            parent: 'entity',
            url: '/owner/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cgApp.owner.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/owner/owner-detail.html',
                    controller: 'OwnerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('owner');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Owner', function($stateParams, Owner) {
                    return Owner.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('owner.new', {
            parent: 'owner',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/owner/owner-dialog.html',
                    controller: 'OwnerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                userId: null,
                                userName: null,
                                userPwd: null,
                                userMail: null,
                                userPhone: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('owner', null, { reload: true });
                }, function() {
                    $state.go('owner');
                });
            }]
        })
        .state('owner.edit', {
            parent: 'owner',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/owner/owner-dialog.html',
                    controller: 'OwnerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Owner', function(Owner) {
                            return Owner.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('owner', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('owner.delete', {
            parent: 'owner',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/owner/owner-delete-dialog.html',
                    controller: 'OwnerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Owner', function(Owner) {
                            return Owner.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('owner', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
