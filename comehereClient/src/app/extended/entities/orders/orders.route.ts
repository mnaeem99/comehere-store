import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
import { AuthGuard } from 'src/app/core/guards/auth.guard';
import { OrdersDetailsExtendedComponent, OrdersListExtendedComponent, OrdersNewExtendedComponent } from './';

const routes: Routes = [
  { path: '', component: OrdersListExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [AuthGuard] },
  {
    path: ':id',
    component: OrdersDetailsExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  { path: 'new', component: OrdersNewExtendedComponent, canActivate: [AuthGuard] },
];

export const ordersRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);
