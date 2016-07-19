'use strict';

describe('Controller Tests', function() {

    describe('Household Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockHousehold, MockPayment, MockOwner, MockNews;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockHousehold = jasmine.createSpy('MockHousehold');
            MockPayment = jasmine.createSpy('MockPayment');
            MockOwner = jasmine.createSpy('MockOwner');
            MockNews = jasmine.createSpy('MockNews');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Household': MockHousehold,
                'Payment': MockPayment,
                'Owner': MockOwner,
                'News': MockNews
            };
            createController = function() {
                $injector.get('$controller')("HouseholdDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cgApp:householdUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
