import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { debounceTime } from 'rxjs/internal/operators/debounceTime';

import { Project } from 'src/app/models/project.model';
import { ProjectManagerService } from 'src/app/services/project-manager.service';
import { projectMessages } from 'src/app/shared/messages/project.messages';

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
  }

  // build the form controls and validations
  buildForm(): void {
      this.projectForm = this.fb.group({
          projectName: ['',[Validators.required, Validators.minLength(5), Validators.maxLength(25),Validators.pattern('^[a-zA-Z0-9\-]*$')]],
          dateCheck: [''],
          startDate: [''],
          endDate: [''],
          priority:[0],
          manager:['',[Validators.required]],
          searchKey: ['']
      });

      this.projectForm.valueChanges.subscribe( formValues => {
         this.onValueChanged();
      });

      // listen to the search key value and filter the project details
      //  wait for 500 ms to react
      this.projectForm.get("searchKey").valueChanges
                   .pipe(debounceTime(300))
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
      const messages = projectMessages;
      if(!this.projectForm) return;
      // iterate the form control error object
      Object.keys(this.formErrors).forEach( key => {
          const control = this.projectForm.get(key);
          // clear the earlier error
          this.formErrors[key] = '';
          // check whether user has changed any value
          if((control.dirty || control.touched) && control.errors) {
              // show only single error
              const error = Object.keys(control.errors)[0];
              this.formErrors[key] = projectMessages[key][error];                               
          }

          // when user cleared the value, change the state of control.
          if(!control.value) {
              control.markAsPristine();
              control.markAsUntouched();
          }
      });
  }  

  // method to add / update the project
  addUpdateProject(): void {
      console.log(this.projectForm.value);
      if(typeof this.editIndex === "number"){
          this.projectDetails[this.editIndex] = this.projectForm.value;            
      } else {
          this.projectDetails.push(this.projectForm.value);            
      }
      this.projectDetailsCopy = Object.assign([], this.projectDetails);
      this.reset();
  } 

  // method to clear the user details
  reset(): void {
      this.projectForm.controls['projectName'].reset('');
      this.projectForm.controls['priority'].reset(0);
      this.projectForm.controls['manager'].reset('');
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
        this.projectDetails.sort((a,b) => a.completedTasks.localeCompare(b.completedTasks));
    }
  } 
  
  // method to edit the project
  editProject(index: number): void {
      const projectInfo: Project  = this.projectDetails[index];
      this.projectForm.get("projectName").reset(projectInfo.projectName);
      this.projectForm.get("priority").reset(projectInfo.priority);
      this.projectForm.get("manager").reset(projectInfo.manager);
      this.isEdit= true;
      this.editIndex= index;
  }

  // method to suspend the project
  suspendProject(index: number): void {

  }
  
}
