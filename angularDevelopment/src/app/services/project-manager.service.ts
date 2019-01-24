import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";


import { User } from "src/app/models/user.model";
import { Project } from "src/app/models/project.model";
import { Task } from "src/app/models/task.model";
import { ParentTask } from "src/app/models/parent-task.model";

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
                    projectInfo.startDate,
                    projectInfo.endDate,
                    projectInfo.priority,
                    projectInfo.user.userID,
                    0,
                    0
                    );
        })));
    }
    
    // get the projects with task details
    public getProjectsWithDetails(): Observable<Project[]>{
        return this.http.get<User[]>(this.API_PATH + 'projectsWithDetails')
        .pipe( map( response => response['data'].map(projectInfo => {
            return new Project(
                    projectInfo[0],
                    projectInfo[1],
                    projectInfo[2],
                    projectInfo[3],
                    projectInfo[4],
                    projectInfo[5],
                    projectInfo[6],
                    projectInfo[7]
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
    
    // suspend project
    public suspendProject(projectID: number): Observable<any> {
        return this.http.put(this.API_PATH + 'suspendProject', projectID, {headers : this.headers});
    }
    
    // get the parent tasks
    public getParentTasks(): Observable<ParentTask[]> {
        return this.http.get<ParentTask[]>(this.API_PATH + 'parentTasks')
        .pipe( map( response => response['data'].map(taskInfo => {
            return new ParentTask(
                    taskInfo.parentTaskID,
                    taskInfo.parentTaskName                   
                    );
        })));
    }
    
    // save the parent task
    public saveParentTask(parentTask: any): Observable<any> {
        return this.http.post(this.API_PATH + 'addParentTask', parentTask, {headers : this.headers}); 
    }
    
    // get the tasks
    public getTasks(projectID: any): Observable<Task[]>{
        return this.http.get<User[]>(this.API_PATH + 'tasks/'+projectID)
        .pipe( map( response => response['data'].map(taskInfo => {
            return new Task(
                    taskInfo.taskID,
                    taskInfo.project.projectID,
                    taskInfo.taskName,
                    false,
                    taskInfo.priority,
                    taskInfo.parentTask ? taskInfo.parentTask.parentTaskName : '',
                    taskInfo.startDate,
                    taskInfo.endDate,
                    taskInfo.user.userID,
                    taskInfo.status
                    );
        })));
    }
    
    // get the task
    public getTask(taskID: any): Observable<Task>{
        return this.http.get<User[]>(this.API_PATH + 'task/'+taskID)
        .pipe( map( response => 
                 new Task(
                     response['data'].taskID,
                     response['data'].project.projectID,
                     response['data'].taskName,
                    false,
                    response['data'].priority,
                    response['data'].parentTask ? response['data'].parentTask.parentTaskName : '',
                    response['data'].startDate,
                    response['data'].endDate,
                    response['data'].user.userID,
                    response['data'].status
                    )
        ));
    }
    
    // save or update task
    public saveUpdateTask(taskForm: any): Observable<any> {
        if(taskForm.taskID){
            return this.http.put(this.API_PATH + 'updateTask', taskForm, {headers : this.headers});
        } else {
            return this.http.post(this.API_PATH + 'addTask', taskForm, {headers : this.headers});
        }
    }
    
    // end task
    public endTask(taskID: number): Observable<any> {
        return this.http.put(this.API_PATH + 'endTask', taskID, {headers : this.headers}); 
    }
}