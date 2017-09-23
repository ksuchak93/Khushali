/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { KhushFinalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ShipProductMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/ship-product/ship-product-my-suffix-detail.component';
import { ShipProductMySuffixService } from '../../../../../../main/webapp/app/entities/ship-product/ship-product-my-suffix.service';
import { ShipProductMySuffix } from '../../../../../../main/webapp/app/entities/ship-product/ship-product-my-suffix.model';

describe('Component Tests', () => {

    describe('ShipProductMySuffix Management Detail Component', () => {
        let comp: ShipProductMySuffixDetailComponent;
        let fixture: ComponentFixture<ShipProductMySuffixDetailComponent>;
        let service: ShipProductMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KhushFinalTestModule],
                declarations: [ShipProductMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ShipProductMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(ShipProductMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShipProductMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShipProductMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ShipProductMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.shipProduct).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
