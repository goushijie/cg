(function() {
    'use strict';
    angular
        .module('cgApp')
        .factory('BuildingSetting', BuildingSetting);

    BuildingSetting.$inject = ['$resource'];

    function BuildingSetting ($resource) {
        var resourceUrl =  'api/building-settings/:id';

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
