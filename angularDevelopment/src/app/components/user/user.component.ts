import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { debounceTime, map } from 'rxjs/operators';

import { ProjectManagerService } from 'src/app/services/project-manager.service';
import { userMessages } from 'src/app/shared/messages/user.messages';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit{

    // instance variables
    userForm: FormGroup;
    userDetails: User[] = [];
    userDetailsCopy: User[] = [];
    isEdit: boolean;
    editIndex: number;

    // dependency injection
    constructor(private fb: FormBuilder, private service: ProjectManagerService){}

    // on load of the component
    ngOnInit(): void {
        this.buildForm();
    }

    // build the form controls and validations
    buildForm(): void {
        this.userForm = this.fb.group({
            firstName : ['',[Validators.required, Validators.minLength(5), Validators.maxLength(25),Validators.pattern('^[a-zA-Z]*$')]],
            lastName:['',[Validators.required,  Validators.minLength(5), Validators.maxLength(25),Validators.pattern('^[a-zA-Z]*$')]],
            employeeID:['',[Validators.required,  Validators.minLength(6), Validators.maxLength(8), Validators.pattern('^[a-zA-Z0-9]*$')]],
            searchKey: ['']
        });

        this.userForm.valueChanges.subscribe( formValues => {
           this.onValueChanged();
        });

        // listen to the search key value and filter the user details
        //  wait for 500 ms to react
        this.userForm.get("searchKey").valueChanges
                     .pipe(debounceTime(300))
                     .subscribe(key => {
                        if(key) {
                            this.userDetails = this.userDetails.filter(user => {return (user.firstName.indexOf(key) != -1 ||
                                user.lastName.toLowerCase().indexOf(key.toLowerCase()) != -1)})  
                        } else {
                            this.userDetails = this.userDetailsCopy;
                        }                        
                     });

    }

    // on value changed, check for control errors
    onValueChanged(): void {
        const messages = userMessages;
        if(!this.userForm) return;
        // iterate the form control error object
        Object.keys(this.formErrors).forEach( key => {
            const control = this.userForm.get(key);
            // clear the earlier error
            this.formErrors[key] = '';
            // check whether user has changed any value
            if((control.dirty || control.touched) && control.errors) {
                // show only single error
                const error = Object.keys(control.errors)[0];
                this.formErrors[key] = userMessages[key][error];                               
            }

            // when user cleared the value, change the state of control.
            if(!control.value) {
                control.markAsPristine();
                control.markAsUntouched();
            }
        });
    }

    // form errors
    formErrors = {
        'firstName': '',
        'lastName': '',
        'employeeID': ''
    }

    // method to add / update the user
    addUpdateUser(): void {
        console.log(this.userForm.value);
        if(typeof this.editIndex === "number"){
            this.userDetails[this.editIndex] = this.userForm.value;            
        } else {
            this.userDetails.push(this.userForm.value);            
        }
        this.userDetailsCopy = Object.assign([], this.userDetails);
        this.reset();
    } 

    // method to clear the user details
    reset(): void {
        this.userForm.controls['firstName'].reset('');
        this.userForm.controls['lastName'].reset('');
        this.userForm.controls['employeeID'].reset('');
        this.isEdit = false;
        this.editIndex = null;
    }

    // method to sort th user by first name, last name or employee id
    sortUser(by: string): void {
        if(by == "fn") {
            this.userDetails.sort((a,b) => a.firstName.localeCompare(b.firstName));
        } else if(by == "ln") {
            this.userDetails.sort((a,b) => a.lastName.localeCompare(b.lastName));
        } else if(by == "id") {
            this.userDetails.sort((a,b) => a.employeeID.localeCompare(b.employeeID));
        }
    } 
    
    // method to edit the user
    editUser(index: number): void {
        const userInfo: User  = this.userDetails[index];
        this.userForm.get("firstName").reset(userInfo.firstName);
        this.userForm.get("lastName").reset(userInfo.lastName);
        this.userForm.get("employeeID").reset(userInfo.employeeID);
        this.isEdit= true;
        this.editIndex= index;
    }

    // method to delete the user
    deleteUser(index: number): void {

    }
}
