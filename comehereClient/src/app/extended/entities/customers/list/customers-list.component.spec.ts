import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChangeDetectorRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { EntryComponents, TestingModule } from 'src/testing/utils';
import {
  CustomersExtendedService,
  CustomersDetailsExtendedComponent,
  CustomersListExtendedComponent,
  CustomersNewExtendedComponent,
} from '../';
import { ICustomers } from 'src/app/entities/customers';
import { ListFiltersComponent, ServiceUtils } from 'src/app/common/shared';
import { ListComponent, DetailsComponent, NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('CustomersListExtendedComponent', () => {
  let fixture: ComponentFixture<CustomersListExtendedComponent>;
  let component: CustomersListExtendedComponent;
  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [CustomersListExtendedComponent, ListComponent],
        imports: [TestingModule],
        providers: [CustomersExtendedService, ChangeDetectorRef],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(CustomersListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          CustomersListExtendedComponent,
          CustomersNewExtendedComponent,
          NewComponent,
          CustomersDetailsExtendedComponent,
          ListComponent,
          DetailsComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'customers', component: CustomersListExtendedComponent },
            { path: 'customers/:id', component: CustomersDetailsExtendedComponent },
          ]),
        ],
        providers: [CustomersExtendedService, ChangeDetectorRef],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(CustomersListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
