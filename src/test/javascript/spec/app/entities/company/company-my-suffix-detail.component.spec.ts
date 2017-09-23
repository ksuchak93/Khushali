/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { KhushFinalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CompanyMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/company/company-my-suffix-detail.component';
import { CompanyMySuffixService } from '../../../../../../main/webapp/app/entities/company/company-my-suffix.service';
import { CompanyMySuffix } from '../../../../../../main/webapp/app/entities/company/company-my-suffix.model';

describe('Component Tests', () => {

    describe('CompanyMySuffix Management Detail Component', () => {
        let comp: CompanyMySuffixDetailComponent;
        let fixture: ComponentFixture<CompanyMySuffixDetailComponent>;
        let service: CompanyMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KhushFinalTestModule],
                declarations: [CompanyMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CompanyMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(CompanyMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CompanyMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.company).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
