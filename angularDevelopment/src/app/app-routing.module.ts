import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { UserComponent } from 'src/app/components/user/user.component';
import { ProjectComponent } from 'src/app/components/project/project.component';
import { AddTaskComponent } from "src/app/components/add-task/add-task.component";
import { ViewTaskComponent } from "src/app/components/view-task/view-task.component";

const routes: Routes = [
  {path: 'user', component: UserComponent},
  {path: 'project', component: ProjectComponent},
  {path: 'addTask', component: AddTaskComponent},
  {path:'viewTask', component: ViewTaskComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
