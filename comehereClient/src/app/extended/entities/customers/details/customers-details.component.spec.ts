import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { DetailsComponent, ListComponent, FieldsComp } from 'src/app/common/general-components';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { CustomersExtendedService, CustomersDetailsExtendedComponent, CustomersListExtendedComponent } from '../';
import { ICustomers } from 'src/app/entities/customers';
describe('CustomersDetailsExtendedComponent', () => {
  let component: CustomersDetailsExtendedComponent;
  let fixture: ComponentFixture<CustomersDetailsExtendedComponent>;
  let el: HTMLElement;

  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [CustomersDetailsExtendedComponent, DetailsComponent],
        imports: [TestingModule],
        providers: [CustomersExtendedService],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(CustomersDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          CustomersDetailsExtendedComponent,
          CustomersListExtendedComponent,
          DetailsComponent,
          ListComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'customers', component: CustomersDetailsExtendedComponent },
            { path: 'customers/:id', component: CustomersListExtendedComponent },
          ]),
        ],
        providers: [CustomersExtendedService],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(CustomersDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
