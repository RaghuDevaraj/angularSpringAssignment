import { Component, ViewChild, Input, OnChanges, SimpleChanges, OnInit } from "@angular/core";
import { NgbModal, NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";

@Component({
    selector: 'app-modal-popup',
    templateUrl: 'modal-popup.component.html',
    styleUrls: ['modal-popup.component.css']
})
export class ModalPopupComponent implements OnInit, OnChanges {

    // to get the modal
    @ViewChild('modalPopup')
    private modalPopup: any;
    @Input('key')
    key: string;
    @Input('arrayInfo')
    arrayInfo  : any[] = [];
    title: string;
    headers = headers();

    //form creation
    modalForm: FormGroup;

    // dependency injection
    constructor(private modalService: NgbModal, public activeModal: NgbActiveModal, private fb: FormBuilder){}

    // on laod of the component
    ngOnInit(): void {
        this.modalForm = this.fb.group({
            optionSelected: ['', [Validators.required]]
        });
    }
    // whenever the message-key or parameter is changed, the title and message should be updated.
    ngOnChanges(changes: SimpleChanges): void {
        if(changes['key'] || changes['arrayInfo']) {
            this.title = this.headers[this.key];
        }
    }

    // show confirmnation popup and returns the user response as Promise.
    showConfirmationPopup() : Promise<string> {
        return this.modalService.open(this.modalPopup,{backdrop:'static', keyboard: false, centered: true}).result;
    }

    //  on form submission
    userChoice(): void {
        this.activeModal.close(this.modalForm.value);
    }
}
 
// headers to the popup
export const headers = () => {
    return {
        'user' : 'Select a User',
        'project' : 'Select a Project',
        'task' : 'Select a Task'
    }
} 