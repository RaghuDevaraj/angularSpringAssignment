import { Component, OnInit } from "@angular/core";

@Component({
    selector: 'app-footer',
    templateUrl: './footer.component.html'
})
export class FooterComponent implements OnInit{

    // instance variable
    year: number;

    // on load of the component
    ngOnInit(): void {
        let date = new Date();
        this.year = date.getFullYear();
    }
    
}