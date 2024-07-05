import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChangeDetectorRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { EntryComponents, TestingModule } from 'src/testing/utils';
import {
  SuppliersExtendedService,
  SuppliersDetailsExtendedComponent,
  SuppliersListExtendedComponent,
  SuppliersNewExtendedComponent,
} from '../';
import { ISuppliers } from 'src/app/entities/suppliers';
import { ListFiltersComponent, ServiceUtils } from 'src/app/common/shared';
import { ListComponent, DetailsComponent, NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('SuppliersListExtendedComponent', () => {
  let fixture: ComponentFixture<SuppliersListExtendedComponent>;
  let component: SuppliersListExtendedComponent;
  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [SuppliersListExtendedComponent, ListComponent],
        imports: [TestingModule],
        providers: [SuppliersExtendedService, ChangeDetectorRef],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(SuppliersListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          SuppliersListExtendedComponent,
          SuppliersNewExtendedComponent,
          NewComponent,
          SuppliersDetailsExtendedComponent,
          ListComponent,
          DetailsComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'suppliers', component: SuppliersListExtendedComponent },
            { path: 'suppliers/:id', component: SuppliersDetailsExtendedComponent },
          ]),
        ],
        providers: [SuppliersExtendedService, ChangeDetectorRef],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(SuppliersListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
