(function() {
    'use strict';

    angular
        .module('myJhipsterApp')
        .controller('EditorialDetailController', EditorialDetailController);

    EditorialDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Editorial', 'Book'];

    function EditorialDetailController($scope, $rootScope, $stateParams, previousState, entity, Editorial, Book) {
        var vm = this;

        vm.editorial = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('myJhipsterApp:editorialUpdate', function(event, result) {
            vm.editorial = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
