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
    // popup variables
    popuptitle: string;
    popuparray: any;
    // popup component
    @ViewChild(ModalPopupComponent)
    private popup: ModalPopupComponent;

    // form errors
    formErrors = {
      'taskName': '',
      'startDate': '',
      'endDate': ''
    }

    // dependency injection
    constructor(private fb: FormBuilder, private service: ProjectManagerService,private route: ActivatedRoute){
        
        this.projects.push(new Project("1","Anthem","","","","",4,"",));
        this.projects.push(new Project("2","Aetna","","","","",1,""));
        this.projects.push(new Project("3","Toyota","","","","",2,""));
        this.projects.push(new Project("4","Cigna","","","","",3,""));
        
        this.parentTasks.push(new ParentTask("1","Add component"));
        this.parentTasks.push(new ParentTask("2","Delete component"));
        this.parentTasks.push(new ParentTask("3","Reset component"));
        
        this.users.push(new User("1","Raghu","Devaraj","326452"));
        this.users.push(new User("2","Sugriev","Prathap","321677"));
        this.users.push(new User("3","Krishnaveni","Raghu","265432"));
        
        this.editIndex = this.route.snapshot.queryParams['editTask']; 
        this.editTask = new Task(
                    "5",
                    "3",
                    "Pattern test",         
                    false,
                    25,
                    "2",
                    "2019-12-19",
                    "2019-12-30",
                    "2",
                    ""
                );
    }

    // on load of the component
    ngOnInit(): void {
        this.buildForm();
        this.subscribeParentCheckInfo();
    }

    // build the form controls and validations
    buildForm(): void {
        this.addTaskForm = this.fb.group({
            projectName: [this.editIndex ? this.getObjectForID("project", this.editTask.projectID).projectName : '', [Validators.required]],
            taskName: [this.editIndex ? this.editTask.taskName : '',[Validators.required, Validators.minLength(5), Validators.maxLength(25),Validators.pattern('^[a-zA-Z0-9 \-]*$')]],
            parentTaskCheck: [false],
            priority:[this.editIndex ? this.editTask.priority : 0, [Validators.required] ],
            parentTask:[this.editIndex ? this.getObjectForID("parentTask", this.editTask.parentTaskID).taskName  : ''],
            startDate: [this.editIndex ? this.editTask.startDate : this.getDateInfo('today'), [Validators.required, DateValidator.date, DateValidator.minDate(new Date())]],
            endDate: [this.editIndex ? this.editTask.endDate : this.getDateInfo('tomorrow'), [Validators.required, DateValidator.date]],
            user:[this.editIndex ? this.getObjectForID("user", this.editTask.userID).firstName + " " + this.getObjectForID("user", this.editTask.userID).lastName  : '', [Validators.required]]
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

    // subscribe to date check checkbox
    subscribeParentCheckInfo(): void {        
        // initialize the value changes stream
        const parentTaskSelectionChanges$ = this.addTaskForm.get("parentTaskCheck").valueChanges;
        parentTaskSelectionChanges$.subscribe( (value) => {
          if(!value) {
              this.isParentTask = false;
              // project field
              this.addTaskForm.get("projectName").setValidators([Validators.required]);
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
              this.addTaskForm.updateValueAndValidity();
          } else {  
              this.isParentTask = true;
              // project field
              this.addTaskForm.get("projectName").setValidators(null);
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
    getObjectForID(valueFor: string, id: string): any { 
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
            let project = this.getObjectForID("project", result);
            this.addTaskForm.get("projectName").reset(project.projectName);
        }, (reason) =>{

        });
    }
    
    // search Parent Task
    searchParentTask(): void {        
        this.popuptitle="parentTask";
        this.popuparray=this.parentTasks;
     // display the project details in modal poup
        this.popup.showConfirmationPopup().then( (result) => {
            let parentTask = this.getObjectForID("parentTask", result);
            this.addTaskForm.get("parentTask").reset(parentTask.taskName);
        }, (reason) =>{

        });
        
        
    }

    // search user
    searchUser(): void {      
      this.popuptitle="user";
      this.popuparray=this.users;
      // display the user details in modal poup
      this.popup.showConfirmationPopup().then( (result) => {
          let user = this.getObjectForID("user", result);
          this.addTaskForm.get("user").reset(user.firstName + ' ' + user.lastName);
      }, (reason) =>{

      });
    }

    // method to add / update the task
    addUpdateProject(): void {
        if(typeof this.editIndex === "number"){
        } else {
        }
        this.reset();
    } 

    // method to clear the user details
    reset(): void {
        this.addTaskForm.controls['projectName'].reset('');
        this.addTaskForm.controls['taskName'].reset('');
        this.addTaskForm.controls['parentTaskCheck'].reset(false);
        this.addTaskForm.controls['priority'].reset(0);
        this.addTaskForm.controls['parentTask'].reset('');
        this.addTaskForm.controls['startDate'].reset(this.getDateInfo("today"));
        this.addTaskForm.controls['endDate'].reset(this.getDateInfo("tomorrow"));
        this.addTaskForm.controls['user'].reset('');
    }   

}
