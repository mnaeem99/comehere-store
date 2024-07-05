import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
import { AuthGuard } from 'src/app/core/guards/auth.guard';
import { CustomersDetailsExtendedComponent, CustomersListExtendedComponent, CustomersNewExtendedComponent } from './';

const routes: Routes = [
  {
    path: '',
    component: CustomersListExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  {
    path: ':id',
    component: CustomersDetailsExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  { path: 'new', component: CustomersNewExtendedComponent, canActivate: [AuthGuard] },
];

export const customersRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);
