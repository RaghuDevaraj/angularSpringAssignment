export class Project{
    constructor(
        public id: string,
        public projectName: string,
        public numberOfTasks: number,
        public completedTasks: number,
        public startDate: string,
        public endDate: string,
        public priority: number,
        public manager: string
    ){}
}