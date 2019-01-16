export class Task {
    constructor(
            public taskID: string,
            public projectID: string,
            public taskName: string,
            public isParentTask: boolean,
            public priority: number,
            public parentTaskID: string,
            public startDate: string,
            public endDate: string,
            public userID: string,
            public status: string
        ){}
}