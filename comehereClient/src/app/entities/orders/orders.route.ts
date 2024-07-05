import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
// import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
// import { AuthGuard } from 'src/app/core/guards/auth.guard';

// import { OrdersDetailsComponent, OrdersListComponent, OrdersNewComponent } from './';

const routes: Routes = [
  // { path: '', component: OrdersListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: ':id', component: OrdersDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: 'new', component: OrdersNewComponent, canActivate: [ AuthGuard ] },
];

export const ordersRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);
