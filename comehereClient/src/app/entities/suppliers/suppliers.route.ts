import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
// import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
// import { AuthGuard } from 'src/app/core/guards/auth.guard';

// import { SuppliersDetailsComponent, SuppliersListComponent, SuppliersNewComponent } from './';

const routes: Routes = [
  // { path: '', component: SuppliersListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: ':id', component: SuppliersDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: 'new', component: SuppliersNewComponent, canActivate: [ AuthGuard ] },
];

export const suppliersRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);
