import { Component, ViewChild, Input, OnChanges, SimpleChanges } from "@angular/core";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";

@Component({
    selector: 'app-confirmation-popup',
    templateUrl: 'confirmation-popup.component.html',
    styleUrls: ['confirmation-popup.component.css']
})
export class ConfirmationPopupComponent implements OnChanges {

    // to get the modal
    @ViewChild('confirmationPopup')
    private confirmationPopup: any;
    @Input('messageKey')
    messageKey: string;
    @Input('params')
    params ? : string[] = [];
    title: string;
    message: string;
    headers = headers();
    messages = messages(['','','']);   
    
    // dependency injection
    constructor(private modalService: NgbModal){}

    // whenever the message-key or parameter is changed, the title and message should be updated.
    ngOnChanges(changes: SimpleChanges): void {
        if(changes['messageKey'] || changes['params']) {
            this.messages = messages(this.params ? this.params : ['','','']);
            this.title = this.headers[this.messageKey];
            this.message = this.messages[this.messageKey];
        }
    }

    // show confirmnation popup and returns the user response as Promise.
    showConfirmationPopup() : Promise<string> {
        return this.modalService.open(this.confirmationPopup,{backdrop:'static', keyboard: false, centered: true}).result;
    }

}


// headers to the popup
export const headers = () => {
    return {
        'user-delete' : 'User Deletion',
        'project-suspend' : 'Project Suspension',
        'end-task' : 'End Task',
    }
}    

// messages to the popup
export const messages = (params) => {
    return {
        'user-delete' : `You are about to delete the user - ${params[0]}. Do you want to proceed?`,
        'project-suspend': `You are about to suspend the project - ${params[0]}. Do you want to proceed?`,
        'end-task' : `You are about to end the task - ${params[0]}. Do you want to proceed?`
    }
}