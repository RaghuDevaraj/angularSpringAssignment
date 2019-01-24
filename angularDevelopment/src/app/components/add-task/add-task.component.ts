import { Component, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";

import { User } from "src/app/models/user.model";
import { Project } from "src/app/models/project.model";
import { ParentTask } from "src/app/models/parent-task.model";
import { Task } from "src/app/models/task.model";
import { ModalPopupComponent } from "src/app/shared/components/modal-popup/modal-popup.component";
import { ProjectManagerService } from "src/app/services/project-manager.service";
import { DateValidator } from "src/app/shared/validators/DateValidator";
import { taskMessages } from "src/app/shared/messages/task.messages";

@Component({
  selector: 'app-add-task',
  templateUrl: './add-task.component.html',
  styleUrls: ['./add-task.component.css']
})
export class AddTaskComponent {

    
    // instance variables
    addTaskForm: FormGroup;
    users: User[] = [];
    projects: Project[] = [];
    parentTasks: ParentTask[] = [];
    editIndex: number;
    editTask: Task;
    isParentTask: boolean;
    selectedProject: Project;
    selectedParentTask: ParentTask;
    selectedUser: User;
    // popup variables
    popuptitle: string;
    popuparray: any;
    // popup component
    @ViewChild(ModalPopupComponent)
    private popup: ModalPopupComponent;
    //messages
    successMessage: string;
    errorMessage: string;

    // form errors
    formErrors = {
      'taskName': '',
      'startDate': '',
      'endDate': ''      
    }

    // dependency injection
    constructor(private fb: FormBuilder, private service: ProjectManagerService,private route: ActivatedRoute){       
        this.editIndex = this.route.snapshot.queryParams['editTask']; 
        setTimeout(() => {	
            if(this.editIndex) {
                this.service.getTask(this.editIndex).subscribe((response) => {
                    this.editTask = response;
                    // set form values
                    this.selectedProject = this.getObjectForID("project", this.editTask.projectID);
                    this.selectedParentTask = this.getObjectForID("parentTask", this.editTask.parentTaskID);
                    this.selectedUser = this.getObjectForID("user", this.editTask.userID);
                    
                    this.addTaskForm.get('project').reset(this.selectedProject.projectName);
                    this.addTaskForm.get('taskName').reset(this.editTask.taskName);
                    this.addTaskForm.get('priority').reset(this.editTask.priority);
                    this.addTaskForm.get('parentTask').reset(this.selectedParentTask ? this.selectedParentTask.taskName : '');
                    this.addTaskForm.get('startDate').reset(this.getValidDate(this.editTask.startDate));
                    this.addTaskForm.get('endDate').reset(this.getValidDate(this.editTask.endDate));
                    this.addTaskForm.get('user').reset(this.selectedUser.firstName + " " + this.selectedUser.lastName);
                    
                })
            }
        }, 50);        
        
    }

    // on load of the component
    ngOnInit(): void {
        this.buildForm();
        this.getUsers();
        this.getProjects();
        this.getParentTasks();
        this.subscribeParentCheckInfo();
    }

    // build the form controls and validations
    buildForm(): void {
        this.addTaskForm = this.fb.group({
            project: ['', [Validators.required]],
            taskName: ['',[Validators.required, Validators.minLength(5), Validators.maxLength(25),Validators.pattern('^[a-zA-Z0-9 \-]*$')]],
            parentTaskCheck: [false],
            priority:[ 0 ],
            parentTask:[''],
            startDate: [this.getDateInfo('today'), [Validators.required, DateValidator.date, DateValidator.minDate(new Date())]],
            endDate: [this.getDateInfo('tomorrow'), [Validators.required, DateValidator.date]],
            user:['', [Validators.required]]
        }, { validator: Validators.compose([
              DateValidator.maximumDate('startDate', 'endDate'),
              DateValidator.minimumDate('startDate', 'endDate')
        ])});

        this.addTaskForm.valueChanges.subscribe( formValues => {
           this.onValueChanged();
        });  
    }

    // on value changed, check for control errors
    onValueChanged(): void {
        if(!this.addTaskForm) return;
        // iterate the form control error object
        Object.keys(this.formErrors).forEach( key => {
            const control = this.addTaskForm.get(key);
            // clear the earlier error
            this.formErrors[key] = '';
            // check whether user has changed any value
            console.log(key,control.errors);
            if(control && (control.dirty || control.touched) && control.invalid) {
              const messages = taskMessages[key];            
                for(const error in control.errors) {
                    if(this.formErrors[key].length === 0) {
                      this.formErrors[key] = messages[error];
                    }                
                }                                               
            }

            // when user cleared the value, change the state of control.
            if(!control.value) {
                control.markAsPristine();
            }
        });
    }  
    
    // method to get user details
    getUsers() {
        this.service.getUsers().subscribe(
                (response) => {
                    this.users = response;
            });
    }
    
    // method to get project details
    getProjects() {
        this.service.getProjects().subscribe(
                (response) => {
                    this.projects = response;
            });
    }
    
    // method to get the parent task details
    getParentTasks() {
        this.service.getParentTasks().subscribe(
                (response) => {
                    this.parentTasks = response;
            });
    }

    // subscribe to date check checkbox
    subscribeParentCheckInfo(): void {        
        // initialize the value changes stream
        const parentTaskSelectionChanges$ = this.addTaskForm.get("parentTaskCheck").valueChanges;
        parentTaskSelectionChanges$.subscribe( (value) => {
          if(!value) {
              this.isParentTask = false;
              // project field
              this.addTaskForm.get("project").setValidators([Validators.required]);
              this.addTaskForm.get("project").updateValueAndValidity();
              // priority field
              this.addTaskForm.get("priority").enable();
              // start date field
              this.addTaskForm.get("startDate").enable();
              this.addTaskForm.get("startDate").setValidators([
                  Validators.required, DateValidator.date, DateValidator.minDate(new Date()) 
              ]);
              // end date function
              this.addTaskForm.get("endDate").enable();
              this.addTaskForm.get("endDate").setValidators([Validators.required, DateValidator.date]);
              // user field
              this.addTaskForm.get("user").setValidators([Validators.required]);
              this.addTaskForm.get("user").updateValueAndValidity();
              this.addTaskForm.updateValueAndValidity();
          } else {  
              this.isParentTask = true;
              // project field
              this.addTaskForm.get("project").setValidators(null);
              this.addTaskForm.get("project").updateValueAndValidity();
              // priority field
              this.addTaskForm.get("priority").disable();
              // start date field
              this.addTaskForm.get("startDate").disable();
              this.addTaskForm.get("startDate").setValidators(null);
              // end date function
              this.addTaskForm.get("endDate").disable();
              this.addTaskForm.get("endDate").setValidators(null);
              // user field
              this.addTaskForm.get("user").setValidators(null);
              this.addTaskForm.get("user").updateValueAndValidity();
              this.addTaskForm.updateValueAndValidity();
          }
        })

    }
    
    // to get the today's and tomorrow's date
    getDateInfo(when: string): string{
        // date strings
        const today = new Date();
        const tomorrow = new Date();
        tomorrow.setDate(today.getDate() + 1);
        if(when == "today") {
            return today.getFullYear() + "-" + this.prependZero(today.getMonth() + 1) + "-" + this.prependZero(today.getDate());
        } else {
            return tomorrow.getFullYear() + "-" + this.prependZero(tomorrow.getMonth() + 1) + "-" + this.prependZero(tomorrow.getDate());
        }        
    }
    
    // find the Object for the id of project / user / parent task
    getObjectForID(valueFor: string, id: any): any { 
        if(valueFor == "user") {
            return this.users.find((user) => user.id == id);
        } else if(valueFor == "project") {
            return this.projects.find((project) => project.id == id);
        } else if(valueFor == "parentTask") {
            return this.parentTasks.find((task) => task.taskID == id);
        }
    }

    // prepend zero to month / day
    prependZero(value: number) : any {
      return value < 10 ? "0" + value : value;
    }
    
    // search project
    searchProject(): void {        
        this.popuptitle="project";
        this.popuparray= this.projects;
        // display the project details in modal poup
        this.popup.showConfirmationPopup().then( (result) => {
            this.selectedProject = this.getObjectForID("project", result);
            this.addTaskForm.get("project").reset(this.selectedProject.projectName);
        }, (reason) =>{

        });
    }
    
    // search Parent Task
    searchParentTask(): void {        
        this.popuptitle="parentTask";
        this.popuparray=this.parentTasks;
     // display the project details in modal poup
        this.popup.showConfirmationPopup().then( (result) => {
            this.selectedParentTask = this.getObjectForID("parentTask", result);
            this.addTaskForm.get("parentTask").reset(this.selectedParentTask.taskName);
        }, (reason) =>{

        });
        
        
    }

    // search user
    searchUser(): void {      
      this.popuptitle="user";
      this.popuparray=this.users;
      // display the user details in modal poup
      this.popup.showConfirmationPopup().then( (result) => {
          this.selectedUser = this.getObjectForID("user", result);
          this.addTaskForm.get("user").reset(this.selectedUser.firstName + ' ' + this.selectedUser.lastName);
      }, (reason) =>{

      });
    }

    // method to add / update the task
    addUpdateTask(): void {
        let task = this.addTaskForm.value;
        if(this.isParentTask) {
            task.parentTaskName = task.taskName;
            this.service.saveParentTask(task).subscribe(
                    (response) => {
                        if(response['message']) {
                            this.successMessage = response['message'];
                            this.getProjects();
                        } else {
                           this.errorMessage =  response['error'];
                        }
             });
        } else {
            if(this.editIndex){
                task.taskID = this.editIndex;
            } else {
                task.status = 'O';
            } 
            task.project = this.selectedProject.id;
            task.parentTask = this.selectedParentTask ? this.selectedParentTask.taskID : null;
            task.user = this.selectedUser.id;
            this.service.saveUpdateTask(task).subscribe(
                    (response) => {
                        if(response['message']) {
                            this.successMessage = response['message'];
                            this.getProjects();
                        } else {
                           this.errorMessage =  response['error'];
                        }
             });
        }        
        this.reset();
    } 

    // method to clear the user details
    reset(): void {
        this.addTaskForm.controls['project'].reset('');
        this.addTaskForm.controls['taskName'].reset('');
        this.addTaskForm.controls['parentTaskCheck'].reset(false);
        this.addTaskForm.controls['priority'].reset(0);
        this.addTaskForm.controls['parentTask'].reset('');
        this.addTaskForm.controls['startDate'].reset(this.getDateInfo("today"));
        this.addTaskForm.controls['endDate'].reset(this.getDateInfo("tomorrow"));
        this.addTaskForm.controls['user'].reset('');
    }   
    
 // get valid date for HTML date field
    getValidDate(date: any) {
        let formattedDate = ''
        if(date) {
            formattedDate = date.split("T")[0];
        }
        return formattedDate;
    }

}
