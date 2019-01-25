import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { debounceTime, map } from 'rxjs/operators';

import { ProjectManagerService } from 'src/app/services/project-manager.service';
import { userMessages } from 'src/app/shared/messages/user.messages';
import { User } from 'src/app/models/user.model';
import { ConfirmationPopupComponent } from 'src/app/shared/components/confirmation-popup/confirmation-popup.component';

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
    // popup variables
    popupmessage: string;
    paramsArray: string[] = [];
    // popup component
    @ViewChild(ConfirmationPopupComponent)
    private popup: ConfirmationPopupComponent;
    // messages
    successMessage: string;
    errorMessage: string;

    // dependency injection
    constructor(private fb: FormBuilder, private service: ProjectManagerService){}

    // on load of the component
    ngOnInit(): void {
        this.buildForm();
        this.getUser();
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
        this.userForm.get("searchKey").valueChanges
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
    
    // method to get user details
    getUser() {
        this.service.getUsers().subscribe(
                (response) => {
                    this.userDetails = response;
                    this.userDetailsCopy = Object.assign([], this.userDetails);
            });
    }

    // method to add / update the user
    addUpdateUser(): void { 
        let user = this.userForm.value;
        if(typeof this.editIndex === "number") {
            user.userID = this.editIndex;
        }
        this.service.saveUpdateUser(user).subscribe(
                (response) => {
                   if(response['message']) {
                       this.successMessage = response['message'];
                       this.getUser();
                   } else {
                      this.errorMessage =  response['error'];
                   }
        });          
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
        this.userForm.markAsDirty();
        this.isEdit= true;
        this.editIndex = userInfo.id;
    }

    // method to delete the user
    deleteUser(index: number): void {
        const userInfo: User  = this.userDetails[index];
        this.paramsArray = [];
        this.popupmessage = 'user-delete';
        this.paramsArray.push((userInfo.firstName + ' ' + userInfo.lastName).toUpperCase());

        // display the confirmation popup b4 deleting the user info.
        this.popup.showConfirmationPopup().then((result) => {
            this.service.deleteUser(userInfo.id).subscribe(
                (response) => {
                    if(response['message']) {
                        this.successMessage = response['message'];
                        this.getUser();
                    } else {
                       this.errorMessage =  response['error'];
                    }
             })
        }, (reason) => {
            // user closed the popup by clicking cross or cancel button
        })
    }
}
