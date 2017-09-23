/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { KhushFinalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ShipmentMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/shipment/shipment-my-suffix-detail.component';
import { ShipmentMySuffixService } from '../../../../../../main/webapp/app/entities/shipment/shipment-my-suffix.service';
import { ShipmentMySuffix } from '../../../../../../main/webapp/app/entities/shipment/shipment-my-suffix.model';

describe('Component Tests', () => {

    describe('ShipmentMySuffix Management Detail Component', () => {
        let comp: ShipmentMySuffixDetailComponent;
        let fixture: ComponentFixture<ShipmentMySuffixDetailComponent>;
        let service: ShipmentMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KhushFinalTestModule],
                declarations: [ShipmentMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ShipmentMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(ShipmentMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShipmentMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShipmentMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ShipmentMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.shipment).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
