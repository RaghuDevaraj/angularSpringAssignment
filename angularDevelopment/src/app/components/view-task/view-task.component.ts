import { Component, ViewChild } from '@angular/core';

import { Task } from "src/app/models/task.model";
import { ProjectManagerService } from "src/app/services/project-manager.service";
import { ModalPopupComponent } from "src/app/shared/components/modal-popup/modal-popup.component";
import { Project } from "src/app/models/project.model";
import { FormGroup, FormControl } from "@angular/forms";

@Component({
  selector: 'app-view-task',
  templateUrl: './view-task.component.html',
  styleUrls: ['./view-task.component.css']
})
export class ViewTaskComponent {
    
    //instance variables
    viewTaskForm: FormGroup;
    taskArray: Task[] = [];
    projects: Project[] = [];
    //popup variables
    popuptitle: string = 'project';
    //popup component
    @ViewChild(ModalPopupComponent)
    private popup: ModalPopupComponent;
    
    // dependecy injection
    constructor(private service: ProjectManagerService){
        this.projects.push(new Project("1","Anthem","","","","",4,"",));
        this.projects.push(new Project("2","Aetna","","","","",1,""));
        this.projects.push(new Project("3","Toyota","","","","",2,""));
        this.projects.push(new Project("4","Cigna","","","","",3,""));
        
        this.viewTaskForm = new FormGroup({
            projectName: new FormControl()
        });
    }
    
 // search project
    searchProject(): void {        
        // display the project details in modal poup
        this.popup.showConfirmationPopup().then( (result) => {
            const projectInfo = this.projects.find( (project) => project.id == result);
            this.viewTaskForm.get("projectName").reset(projectInfo.projectName);
            this.taskArray.push(new Task("1","2","Component task",false,10,"1","10/03/2018","10/05/2018","1","In Progress"));
        }, (reason) =>{

        });
    }

  
    // method to sort th project by first name, last name or employee id
    sortProject(by: string): void {
        if(by == "sd") {
            this.taskArray.sort((a,b) => a.startDate.localeCompare(b.startDate));
        } else if(by == "ed") {
            this.taskArray.sort((a,b) => a.endDate.localeCompare(b.endDate));
        } else if(by == "p") {
            this.taskArray.sort((a,b) => a.priority - b.priority);
        }  else if(by == "c") {
          this.taskArray.sort((a,b) => a.status.localeCompare(b.status));
      }
    } 
}
