(function() {
    'use strict';

    angular
        .module('myJhipsterApp')
        .controller('EditorialDeleteController',EditorialDeleteController);

    EditorialDeleteController.$inject = ['$uibModalInstance', 'entity', 'Editorial'];

    function EditorialDeleteController($uibModalInstance, entity, Editorial) {
        var vm = this;

        vm.editorial = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Editorial.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
