import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { UserComponent } from 'src/app/components/user/user.component';
import { ProjectComponent } from 'src/app/components/project/project.component';

const routes: Routes = [
  {path: 'user', component: UserComponent},
  {path: 'project', component: ProjectComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
