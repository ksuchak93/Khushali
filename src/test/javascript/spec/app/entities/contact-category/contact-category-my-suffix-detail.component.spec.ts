/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { KhushFinalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ContactCategoryMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/contact-category/contact-category-my-suffix-detail.component';
import { ContactCategoryMySuffixService } from '../../../../../../main/webapp/app/entities/contact-category/contact-category-my-suffix.service';
import { ContactCategoryMySuffix } from '../../../../../../main/webapp/app/entities/contact-category/contact-category-my-suffix.model';

describe('Component Tests', () => {

    describe('ContactCategoryMySuffix Management Detail Component', () => {
        let comp: ContactCategoryMySuffixDetailComponent;
        let fixture: ComponentFixture<ContactCategoryMySuffixDetailComponent>;
        let service: ContactCategoryMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KhushFinalTestModule],
                declarations: [ContactCategoryMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ContactCategoryMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(ContactCategoryMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ContactCategoryMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContactCategoryMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ContactCategoryMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.contactCategory).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
