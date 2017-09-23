/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { KhushFinalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProductDemoMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/product-demo/product-demo-my-suffix-detail.component';
import { ProductDemoMySuffixService } from '../../../../../../main/webapp/app/entities/product-demo/product-demo-my-suffix.service';
import { ProductDemoMySuffix } from '../../../../../../main/webapp/app/entities/product-demo/product-demo-my-suffix.model';

describe('Component Tests', () => {

    describe('ProductDemoMySuffix Management Detail Component', () => {
        let comp: ProductDemoMySuffixDetailComponent;
        let fixture: ComponentFixture<ProductDemoMySuffixDetailComponent>;
        let service: ProductDemoMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KhushFinalTestModule],
                declarations: [ProductDemoMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProductDemoMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(ProductDemoMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProductDemoMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductDemoMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ProductDemoMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.productDemo).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
