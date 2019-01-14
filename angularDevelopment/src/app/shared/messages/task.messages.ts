export const taskMessages = {
    "taskName": {
        "required": "Task Name is required.",
        "minlength": "Task Name should be minimum 5 characters.",
        "maxlength": "Task Name should be maximum 50 characters.",
        "pattern": "Task Name should be alphaNumeric characters with/without special characters -,*"
    },
    "startDate":{
        "required": "Start Date is required.",
        "date": "Enter a valid Start Date.",
        "minDate": "Start Date cannot be lesser than current date.",
        "maxDate": "Start Date should be lesser than end date"
    },
    "endDate":{
        "required": "End Date is required.",
        "date": "Enter a valid End Date.",
        "minimumDate": "End Date should be greater than start date."
    }
}