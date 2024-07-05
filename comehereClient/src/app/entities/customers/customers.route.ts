import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
// import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
// import { AuthGuard } from 'src/app/core/guards/auth.guard';

// import { CustomersDetailsComponent, CustomersListComponent, CustomersNewComponent } from './';

const routes: Routes = [
  // { path: '', component: CustomersListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: ':id', component: CustomersDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: 'new', component: CustomersNewComponent, canActivate: [ AuthGuard ] },
];

export const customersRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);
