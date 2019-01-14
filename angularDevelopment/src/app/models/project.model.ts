export class Project{
    constructor(
        public id: string,
        public projectName: string,
        public numberOfTasks: string,
        public completedTasks: string,
        public startDate: string,
        public endDate: string,
        public priority: number,
        public manager: string
    ){}
}