import { Component } from "@angular/core";

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls:['./header.component.css']
})
export class HeaderComponent {
    
    // instance variable
    showMenu: boolean;

    // mthod to toggle the menu bar
    toggleMenu(): void {
        this.showMenu = !this.showMenu;
    }
}