import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserComponent } from 'src/app/components/user/user.component';
import { ProjectComponent } from 'src/app/components/project/project.component';
import { AddTaskComponent } from 'src/app/components/add-task/add-task.component';
import { ViewTaskComponent } from 'src/app/components/view-task/view-task.component';
import { ProjectManagerService } from 'src/app/services/project-manager.service';
import { HeaderComponent } from 'src/app/shared/components/header/header.component';
import { FooterComponent } from 'src/app/shared/components/footer/footer.component';

@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    ProjectComponent,
    AddTaskComponent,
    ViewTaskComponent,
    HeaderComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule
  ],
  providers: [ProjectManagerService],
  bootstrap: [AppComponent]
})
export class AppModule { }
