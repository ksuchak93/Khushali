/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { KhushFinalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SalesOrderMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/sales-order/sales-order-my-suffix-detail.component';
import { SalesOrderMySuffixService } from '../../../../../../main/webapp/app/entities/sales-order/sales-order-my-suffix.service';
import { SalesOrderMySuffix } from '../../../../../../main/webapp/app/entities/sales-order/sales-order-my-suffix.model';

describe('Component Tests', () => {

    describe('SalesOrderMySuffix Management Detail Component', () => {
        let comp: SalesOrderMySuffixDetailComponent;
        let fixture: ComponentFixture<SalesOrderMySuffixDetailComponent>;
        let service: SalesOrderMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KhushFinalTestModule],
                declarations: [SalesOrderMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SalesOrderMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(SalesOrderMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SalesOrderMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SalesOrderMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SalesOrderMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.salesOrder).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
