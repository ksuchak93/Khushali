/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { KhushFinalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CompanyCategoryMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/company-category/company-category-my-suffix-detail.component';
import { CompanyCategoryMySuffixService } from '../../../../../../main/webapp/app/entities/company-category/company-category-my-suffix.service';
import { CompanyCategoryMySuffix } from '../../../../../../main/webapp/app/entities/company-category/company-category-my-suffix.model';

describe('Component Tests', () => {

    describe('CompanyCategoryMySuffix Management Detail Component', () => {
        let comp: CompanyCategoryMySuffixDetailComponent;
        let fixture: ComponentFixture<CompanyCategoryMySuffixDetailComponent>;
        let service: CompanyCategoryMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KhushFinalTestModule],
                declarations: [CompanyCategoryMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CompanyCategoryMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(CompanyCategoryMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyCategoryMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyCategoryMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CompanyCategoryMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.companyCategory).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
