/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { KhushFinalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ContactMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/contact/contact-my-suffix-detail.component';
import { ContactMySuffixService } from '../../../../../../main/webapp/app/entities/contact/contact-my-suffix.service';
import { ContactMySuffix } from '../../../../../../main/webapp/app/entities/contact/contact-my-suffix.model';

describe('Component Tests', () => {

    describe('ContactMySuffix Management Detail Component', () => {
        let comp: ContactMySuffixDetailComponent;
        let fixture: ComponentFixture<ContactMySuffixDetailComponent>;
        let service: ContactMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KhushFinalTestModule],
                declarations: [ContactMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ContactMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(ContactMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ContactMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContactMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ContactMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.contact).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
