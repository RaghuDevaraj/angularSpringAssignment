import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";


import { User } from "src/app/models/user.model";
import { Project } from "src/app/models/project.model";

@Injectable()
export class ProjectManagerService {
    
    // generic group search URL to web api
    private API_PATH = "http://localhost:8080/projectManager/";
    
    // set headers
    private headers = new HttpHeaders({
        'Content-Type': 'application/json'
        });

    constructor(private http: HttpClient){}
    
    // get users
    public getUsers(): Observable<User[]> {
         return this.http.get<User[]>(this.API_PATH + 'users')
             .pipe( map( response => response['data'].map(userInfo => {
                 return new User(
                         userInfo["userID"],
                         userInfo["firstName"],
                         userInfo["lastName"],
                         userInfo["employeeID"]
                         );
             })));            
    }
    
    // save or update user
    public saveUpdateUser(userForm: any): Observable<any> {
        if(userForm.userID){
            return this.http.put(this.API_PATH + 'updateUser', userForm, {headers : this.headers});
        } else {
            return this.http.post(this.API_PATH + 'addUser', userForm, {headers : this.headers});
        }
    }
    
    // delete the user
    public deleteUser(userID : any): Observable<any> {
        return this.http.delete(this.API_PATH + 'deleteUser/' + userID);
    }
    
    // get the projects
    public getProjects(): Observable<Project[]>{
        return this.http.get<User[]>(this.API_PATH + 'projects')
        .pipe( map( response => response['data'].map(projectInfo => {
            return new Project(
                    projectInfo.projectID,
                    projectInfo.projectName,
                    projectInfo.lastName,
                    projectInfo.employeeID,
                    projectInfo.startDate,
                    projectInfo.endDate,
                    projectInfo.priority,
                    projectInfo.user.userID
                    );
        })));
    }
    
    // save or update project
    public saveUpdateProject(projectForm: any): Observable<any> {
        if(projectForm.projectID){
            return this.http.put(this.API_PATH + 'updateProject', projectForm, {headers : this.headers});
        } else {
            return this.http.post(this.API_PATH + 'addProject', projectForm, {headers : this.headers});
        }
    }
    
    // save or update task
    public saveUpdateTask(taskForm: any): Observable<any> {
        if(taskForm.taskID){
            return this.http.put(this.API_PATH + 'updateTask', taskForm, {headers : this.headers});
        } else {
            return this.http.post(this.API_PATH + 'addTask', taskForm, {headers : this.headers});
        }
    }
}