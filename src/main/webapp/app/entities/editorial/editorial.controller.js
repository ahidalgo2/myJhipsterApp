(function() {
    'use strict';

    angular
        .module('myJhipsterApp')
        .controller('EditorialController', EditorialController);

    EditorialController.$inject = ['Editorial'];

    function EditorialController(Editorial) {

        var vm = this;

        vm.editorials = [];

        loadAll();

        function loadAll() {
            Editorial.query(function(result) {
                vm.editorials = result;
                vm.searchQuery = null;
            });
        }
    }
})();
