import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
import { AuthGuard } from 'src/app/core/guards/auth.guard';
import { SuppliersDetailsExtendedComponent, SuppliersListExtendedComponent, SuppliersNewExtendedComponent } from './';

const routes: Routes = [
  {
    path: '',
    component: SuppliersListExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  {
    path: ':id',
    component: SuppliersDetailsExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  { path: 'new', component: SuppliersNewExtendedComponent, canActivate: [AuthGuard] },
];

export const suppliersRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);
