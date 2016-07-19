'use strict';

describe('Controller Tests', function() {

    describe('News Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockNews, MockHousehold;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockNews = jasmine.createSpy('MockNews');
            MockHousehold = jasmine.createSpy('MockHousehold');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'News': MockNews,
                'Household': MockHousehold
            };
            createController = function() {
                $injector.get('$controller')("NewsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cgApp:newsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
