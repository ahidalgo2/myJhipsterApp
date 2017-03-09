(function() {
    'use strict';

    angular
        .module('myJhipsterApp')
        .controller('EditorialDialogController', EditorialDialogController);

    EditorialDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Editorial', 'Book'];

    function EditorialDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Editorial, Book) {
        var vm = this;

        vm.editorial = entity;
        vm.clear = clear;
        vm.save = save;
        vm.books = Book.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.editorial.id !== null) {
                Editorial.update(vm.editorial, onSaveSuccess, onSaveError);
            } else {
                Editorial.save(vm.editorial, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('myJhipsterApp:editorialUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
