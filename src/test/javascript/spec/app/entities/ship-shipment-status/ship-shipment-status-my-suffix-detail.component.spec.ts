/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { KhushFinalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ShipShipmentStatusMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/ship-shipment-status/ship-shipment-status-my-suffix-detail.component';
import { ShipShipmentStatusMySuffixService } from '../../../../../../main/webapp/app/entities/ship-shipment-status/ship-shipment-status-my-suffix.service';
import { ShipShipmentStatusMySuffix } from '../../../../../../main/webapp/app/entities/ship-shipment-status/ship-shipment-status-my-suffix.model';

describe('Component Tests', () => {

    describe('ShipShipmentStatusMySuffix Management Detail Component', () => {
        let comp: ShipShipmentStatusMySuffixDetailComponent;
        let fixture: ComponentFixture<ShipShipmentStatusMySuffixDetailComponent>;
        let service: ShipShipmentStatusMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KhushFinalTestModule],
                declarations: [ShipShipmentStatusMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ShipShipmentStatusMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(ShipShipmentStatusMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShipShipmentStatusMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShipShipmentStatusMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ShipShipmentStatusMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.shipShipmentStatus).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
