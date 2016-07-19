(function() {
    'use strict';

    angular
        .module('cgApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('news', {
            parent: 'entity',
            url: '/news',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cgApp.news.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/news/news.html',
                    controller: 'NewsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('news');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('news-detail', {
            parent: 'entity',
            url: '/news/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cgApp.news.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/news/news-detail.html',
                    controller: 'NewsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('news');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'News', function($stateParams, News) {
                    return News.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('news.new', {
            parent: 'news',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/news/news-dialog.html',
                    controller: 'NewsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                newsId: null,
                                newsName: null,
                                newsType: null,
                                newsContent: null,
                                receiver: null,
                                sendTime: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('news', null, { reload: true });
                }, function() {
                    $state.go('news');
                });
            }]
        })
        .state('news.edit', {
            parent: 'news',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/news/news-dialog.html',
                    controller: 'NewsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['News', function(News) {
                            return News.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('news', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('news.delete', {
            parent: 'news',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/news/news-delete-dialog.html',
                    controller: 'NewsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['News', function(News) {
                            return News.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('news', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
