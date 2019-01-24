export class Project{
    constructor(
        public id: number,
        public projectName: string,        
        public startDate: string,
        public endDate: string,
        public priority: number,
        public manager: string,
        public numberOfTasks: number,
        public completedTasks: number
    ){}
}