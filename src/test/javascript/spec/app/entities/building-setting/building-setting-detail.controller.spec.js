'use strict';

describe('Controller Tests', function() {

    describe('BuildingSetting Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockBuildingSetting;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockBuildingSetting = jasmine.createSpy('MockBuildingSetting');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'BuildingSetting': MockBuildingSetting
            };
            createController = function() {
                $injector.get('$controller')("BuildingSettingDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cgApp:buildingSettingUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
