'use strict';

describe('Controller Tests', function() {

    describe('Payment Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPayment, MockHousehold;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPayment = jasmine.createSpy('MockPayment');
            MockHousehold = jasmine.createSpy('MockHousehold');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Payment': MockPayment,
                'Household': MockHousehold
            };
            createController = function() {
                $injector.get('$controller')("PaymentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cgApp:paymentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
