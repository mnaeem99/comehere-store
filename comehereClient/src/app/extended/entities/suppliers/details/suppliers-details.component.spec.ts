import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { DetailsComponent, ListComponent, FieldsComp } from 'src/app/common/general-components';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { SuppliersExtendedService, SuppliersDetailsExtendedComponent, SuppliersListExtendedComponent } from '../';
import { ISuppliers } from 'src/app/entities/suppliers';
describe('SuppliersDetailsExtendedComponent', () => {
  let component: SuppliersDetailsExtendedComponent;
  let fixture: ComponentFixture<SuppliersDetailsExtendedComponent>;
  let el: HTMLElement;

  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [SuppliersDetailsExtendedComponent, DetailsComponent],
        imports: [TestingModule],
        providers: [SuppliersExtendedService],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(SuppliersDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          SuppliersDetailsExtendedComponent,
          SuppliersListExtendedComponent,
          DetailsComponent,
          ListComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'suppliers', component: SuppliersDetailsExtendedComponent },
            { path: 'suppliers/:id', component: SuppliersListExtendedComponent },
          ]),
        ],
        providers: [SuppliersExtendedService],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(SuppliersDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
