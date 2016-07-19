'use strict';

describe('Controller Tests', function() {

    describe('Owner Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockOwner, MockHousehold;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockOwner = jasmine.createSpy('MockOwner');
            MockHousehold = jasmine.createSpy('MockHousehold');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Owner': MockOwner,
                'Household': MockHousehold
            };
            createController = function() {
                $injector.get('$controller')("OwnerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cgApp:ownerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
