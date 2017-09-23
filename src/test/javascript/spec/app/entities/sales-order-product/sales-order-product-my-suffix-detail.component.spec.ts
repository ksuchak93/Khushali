/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { KhushFinalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SalesOrderProductMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/sales-order-product/sales-order-product-my-suffix-detail.component';
import { SalesOrderProductMySuffixService } from '../../../../../../main/webapp/app/entities/sales-order-product/sales-order-product-my-suffix.service';
import { SalesOrderProductMySuffix } from '../../../../../../main/webapp/app/entities/sales-order-product/sales-order-product-my-suffix.model';

describe('Component Tests', () => {

    describe('SalesOrderProductMySuffix Management Detail Component', () => {
        let comp: SalesOrderProductMySuffixDetailComponent;
        let fixture: ComponentFixture<SalesOrderProductMySuffixDetailComponent>;
        let service: SalesOrderProductMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KhushFinalTestModule],
                declarations: [SalesOrderProductMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SalesOrderProductMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(SalesOrderProductMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SalesOrderProductMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SalesOrderProductMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SalesOrderProductMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.salesOrderProduct).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
