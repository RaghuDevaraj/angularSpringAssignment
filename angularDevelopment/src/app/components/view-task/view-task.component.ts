import { Component, ViewChild } from '@angular/core';

import { Task } from "src/app/models/task.model";
import { ProjectManagerService } from "src/app/services/project-manager.service";
import { ModalPopupComponent } from "src/app/shared/components/modal-popup/modal-popup.component";
import { Project } from "src/app/models/project.model";
import { FormGroup, FormControl } from "@angular/forms";
import { ConfirmationPopupComponent } from "src/app/shared/components/confirmation-popup/confirmation-popup.component";
import { ActivatedRoute, Router } from "@angular/router";

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
    // message
    message: string;
    successMessage: string;
    errorMessage: string;
    //popup variables
    popupmessage: string;
    paramsArray: string[] = [];
    // popup component
    @ViewChild(ConfirmationPopupComponent)
    private confirmPopup: ConfirmationPopupComponent;
    
    // dependecy injection
    constructor(private service: ProjectManagerService, private router: Router){
        // load the projects
        this.service.getProjects().subscribe(
                    (response) => {
                        this.projects = response;
                    }
                );      
        // build the form
        this.viewTaskForm = new FormGroup({
            projectName: new FormControl()
        });        
    }    
      
    // search project
    searchProject(): void {        
        // display the project details in modal poup
        this.popup.showConfirmationPopup().then( (result) => {
            const projectInfo = this.projects.find( (project) => project.id == parseInt(result));
            this.viewTaskForm.get("projectName").reset(projectInfo.projectName);
            this.getTasks(result);
        }, (reason) =>{

        });
    }
    
    // Get the tasks for the project
    getTasks(projectID: any): void {
        this.message = "";      
        this.service.getTasks(projectID).subscribe( (response) => {
            this.taskArray = response;
            if(this.taskArray.length == 0) {
                this.message = "No tasks found."
            }
        })
    }

    
    // end the task - set the status to complete
    endTask(index: number): void {
        const taskInfo: Task  = this.taskArray[index];
        this.paramsArray = [];
        this.popupmessage = 'end-task';
        this.paramsArray.push((taskInfo.taskName).toUpperCase());
      
        // display the confirmation popup b4 suspending the project.
        this.confirmPopup.showConfirmationPopup().then((result) => {
            this.successMessage = "";
            this.errorMessage = "";
            this.service.endTask(taskInfo.taskID).subscribe(
                (response) => {
                    if(response['message']) {
                        this.successMessage = response['message'];
                        this.getTasks(taskInfo.projectID);
                    } else {
                       this.errorMessage =  response['error'];
                    }
             })
        }, (reason) => {
            // user closed the popup by clicking cross or cancel button
        })
    }
    
    // edit task
    editTask(taskID: number): void {
        this.router.navigateByUrl("/addTask?editTask="+taskID);
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
