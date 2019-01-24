import { Component, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { debounceTime } from 'rxjs/internal/operators/debounceTime';

import { Project } from 'src/app/models/project.model';
import { ProjectManagerService } from 'src/app/services/project-manager.service';
import { projectMessages } from 'src/app/shared/messages/project.messages';
import { User } from 'src/app/models/user.model';
import { ModalPopupComponent } from 'src/app/shared/components/modal-popup/modal-popup.component';
import { DateValidator } from 'src/app/shared/validators/DateValidator';
import { ConfirmationPopupComponent } from "src/app/shared/components/confirmation-popup/confirmation-popup.component";

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent {
  
  // instance variables
  projectForm: FormGroup;
  projectDetails: Project[] = [];
  projectDetailsCopy: Project[] = [];
  isEdit: boolean;
  editIndex: number;
  users: User[] = [];
  selectedUser: User;
  //popup variables
  popupmessage: string;
  paramsArray: string[] = [];
  // popup component
  @ViewChild(ConfirmationPopupComponent)
  private confirmPopup: ConfirmationPopupComponent;
  // popup variables
  popuptitle: string;
  // popup component
  @ViewChild(ModalPopupComponent)
  private popup: ModalPopupComponent;
  //messages
  successMessage: string;
  errorMessage: string;

  // form errors
  formErrors = {
    'projectName': '',
    'startDate': '',
    'endDate': ''
  }

  // dependency injection
  constructor(private fb: FormBuilder, private service: ProjectManagerService){}

  // on load of the component
  ngOnInit(): void {
      this.buildForm();
      this.getUsers();
      this.getprojectsWithDetails();
      this.subscribeDateCheckInfo();
  }

  // build the form controls and validations
  buildForm(): void {
      this.projectForm = this.fb.group({
          projectName: ['',[Validators.required, Validators.minLength(5), Validators.maxLength(25),Validators.pattern('^[a-zA-Z0-9 \-]*$')]],
          dateCheck: [''],
          startDate: [{value:'', disabled: true}],
          endDate: [{value:'', disabled: true}],
          priority:[0],
          manager:['',[Validators.required]],
          searchKey: ['']
      }, { validator: Validators.compose([
            DateValidator.maximumDate('startDate', 'endDate'),
            DateValidator.minimumDate('startDate', 'endDate')
      ])});

      this.projectForm.valueChanges.subscribe( formValues => {
         this.onValueChanged();
      });

      // listen to the search key value and filter the project details
      this.projectForm.get("searchKey").valueChanges
                      .subscribe(key => {
                          if(key) {
                              this.projectDetails = this.projectDetails.filter(project => project.projectName.indexOf(key) != -1);  
                          } else {
                              this.projectDetails = this.projectDetailsCopy;
                          }                        
                   });

  }

  // on value changed, check for control errors
  onValueChanged(): void {
      if(!this.projectForm) return;
      // iterate the form control error object
      Object.keys(this.formErrors).forEach( key => {
          const control = this.projectForm.get(key);
          // clear the earlier error
          this.formErrors[key] = '';
          // check whether user has changed any value
          console.log(key,control.errors);
          if(control && (control.dirty || control.touched) && control.errors) {
            const messages = projectMessages[key];            
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
  
  // method to get project with task details
  getprojectsWithDetails() {
      this.service.getProjectsWithDetails().subscribe(
              (response) => {
                  this.projectDetails = response;
                  this.projectDetailsCopy = Object.assign([], this.projectDetails);
          });
  }

  // subscribe to date check checkbox
  subscribeDateCheckInfo(): void {
      // date strings
      const today = new Date();
      const tomorrow = new Date();
      tomorrow.setDate(today.getDate() + 1);
      const startDate = today.getFullYear() + "-" + this.prependZero(today.getMonth() + 1) + "-" + this.prependZero(today.getDate());
      const endDate = tomorrow.getFullYear() + "-" + this.prependZero(tomorrow.getMonth() + 1) + "-" + this.prependZero(tomorrow.getDate());
      // initalize the value changes stream
      const dateSelectionChanges$ = this.projectForm.get("dateCheck").valueChanges;
      dateSelectionChanges$.subscribe( (value) => {
        if(value) {
            // start date function
            this.projectForm.get("startDate").enable();
            this.isEdit ? '' : this.projectForm.get("startDate").reset(startDate);
            this.projectForm.get("startDate").setValidators([
                Validators.required, DateValidator.date, DateValidator.minDate(new Date()) 
            ]);
            this.projectForm.get("startDate").updateValueAndValidity();

            // end date function
            this.projectForm.get("endDate").enable();
            this.isEdit ? '' : this.projectForm.get("endDate").reset(endDate);
            this.projectForm.get("endDate").setValidators([Validators.required, DateValidator.date]);
            this.projectForm.get("endDate").updateValueAndValidity();
        } else {
            // start date function
            this.projectForm.get("startDate").disable();
            this.projectForm.get("startDate").reset("");
            this.projectForm.get("startDate").setValidators(null);
            this.projectForm.get("startDate").updateValueAndValidity();

            // end date function
            this.projectForm.get("endDate").disable();
            this.projectForm.get("endDate").reset("");
            this.projectForm.get("endDate").setValidators(null);
            this.projectForm.get("endDate").updateValueAndValidity();
        }
      })

  }

  // prepend zero to month / day
  prependZero(value: number) : any {
    return value < 10 ? "0" + value : value;
  }

  // search user
  searchUser(): void {    
    this.popuptitle="user";
    // display the user details in modal poup
    this.popup.showConfirmationPopup().then( (result) => {
        this.selectedUser = this.users.find( user => user.id == parseInt(result));
        this.projectForm.get("manager").reset(this.selectedUser.firstName + ' ' + this.selectedUser.lastName);
    }, (reason) =>{

    });
  }

  // method to add / update the project
  addUpdateProject(): void {
      let project = this.projectForm.value;
      if(typeof this.editIndex === "number"){
          project.projectID = this.editIndex;            
      } 
      project.user = this.selectedUser.id;
      this.service.saveUpdateProject(project).subscribe(
              (response) => {
                 if(response['message']) {
                     this.successMessage = response['message'];
                     this.getprojectsWithDetails();
                 } else {
                    this.errorMessage =  response['error'];
                 }
      });            
      this.reset();
  } 

  // method to clear the user details
  reset(): void {
      this.projectForm.controls['projectName'].reset('');
      this.projectForm.controls['priority'].reset(0);
      this.projectForm.controls['manager'].reset('');
      this.projectForm.controls['dateCheck'].reset('');
      this.projectForm.controls['startDate'].reset('');
      this.projectForm.controls['endDate'].reset('');
      this.isEdit = false;
      this.editIndex = null;
  }

  // method to sort th project by first name, last name or employee id
  sortProject(by: string): void {
      if(by == "sd") {
          this.projectDetails.sort((a,b) => a.startDate.localeCompare(b.startDate));
      } else if(by == "ed") {
          this.projectDetails.sort((a,b) => a.endDate.localeCompare(b.endDate));
      } else if(by == "p") {
          this.projectDetails.sort((a,b) => a.priority - b.priority);
      }  else if(by == "c") {
        this.projectDetails.sort((a,b) => b.completedTasks - a.completedTasks);
    }
  } 
  
  // method to edit the project
  editProject(index: number): void {
      this.isEdit= true;
      const projectInfo: Project  = this.projectDetails[index];

      // find the manager name      
      this.selectedUser = this.users.find( user => user.id == parseInt(projectInfo.manager));
      
      this.projectForm.get("projectName").reset(projectInfo.projectName);
      this.projectForm.get("priority").reset(projectInfo.priority);
      this.projectForm.get("manager").reset(this.selectedUser.firstName + ' ' + this.selectedUser.lastName);
      this.projectForm.get('dateCheck').reset(projectInfo.startDate ? true : false);
      this.projectForm.get('startDate').reset(this.getValidDate(projectInfo.startDate));
      this.projectForm.get('endDate').reset(this.getValidDate(projectInfo.endDate));      
      this.editIndex= projectInfo.id;
  }
  
  // get valid date for HTML date field
  getValidDate(date: any) {
      let formattedDate = ''
      if(date) {
          formattedDate = date.split("T")[0];
      }
      return formattedDate;
  }

  // method to suspend the project
  suspendProject(index: number): void {
      const projectInfo: Project  = this.projectDetails[index];
      this.paramsArray = [];
      this.popupmessage = 'project-suspend';
      this.paramsArray.push((projectInfo.projectName).toUpperCase());
    
      // display the confirmation popup b4 suspending the project.
      this.confirmPopup.showConfirmationPopup().then((result) => {
          this.service.suspendProject(projectInfo.id).subscribe(
              (response) => {
                  if(response['message']) {
                      this.successMessage = response['message'];
                      this.getprojectsWithDetails();
                  } else {
                     this.errorMessage =  response['error'];
                  }
           })
      }, (reason) => {
          // user closed the popup by clicking cross or cancel button
      })
  }
  
}
