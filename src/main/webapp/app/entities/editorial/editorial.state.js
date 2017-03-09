(function() {
    'use strict';

    angular
        .module('myJhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('editorial', {
            parent: 'entity',
            url: '/editorial',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Editorials'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/editorial/editorials.html',
                    controller: 'EditorialController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('editorial-detail', {
            parent: 'editorial',
            url: '/editorial/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Editorial'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/editorial/editorial-detail.html',
                    controller: 'EditorialDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Editorial', function($stateParams, Editorial) {
                    return Editorial.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'editorial',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('editorial-detail.edit', {
            parent: 'editorial-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/editorial/editorial-dialog.html',
                    controller: 'EditorialDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Editorial', function(Editorial) {
                            return Editorial.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('editorial.new', {
            parent: 'editorial',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/editorial/editorial-dialog.html',
                    controller: 'EditorialDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('editorial', null, { reload: 'editorial' });
                }, function() {
                    $state.go('editorial');
                });
            }]
        })
        .state('editorial.edit', {
            parent: 'editorial',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/editorial/editorial-dialog.html',
                    controller: 'EditorialDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Editorial', function(Editorial) {
                            return Editorial.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('editorial', null, { reload: 'editorial' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('editorial.delete', {
            parent: 'editorial',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/editorial/editorial-delete-dialog.html',
                    controller: 'EditorialDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Editorial', function(Editorial) {
                            return Editorial.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('editorial', null, { reload: 'editorial' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
