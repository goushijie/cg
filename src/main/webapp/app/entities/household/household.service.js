(function() {
    'use strict';
    angular
        .module('cgApp')
        .factory('Household', Household);

    Household.$inject = ['$resource'];

    function Household ($resource) {
        var resourceUrl =  'api/households/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
