import { AbstractControl, FormGroup } from "@angular/forms";

export class DateValidator {
    
    // to check the date is valid.
    static date(control: AbstractControl): {[key : string] : boolean} {
        const date = new Date(control.value);
        return date.toString() != 'Invalid Date' ?  null : {'date' : true};
    }

    //  to check the date is minimum than the supplied date.
    static minDate = (minDate: Date) => {
        return (control: AbstractControl) => {
            const minimumDate = minDate.getFullYear() + "-" + prependZero(minDate.getMonth() + 1) + "-" + prependZero(minDate.getDate());
            if( new Date(control.value).getTime() < new Date(minimumDate).getTime()) {
                return {'minDate': true};
            }
            return null;
        }
    }

    //  to check the date is maximum than the supplied date.
    static maximumDate = (date1: string, date2: string) => {
        return (group: FormGroup) => {
            let errors = group.controls[date1].errors;
            if( new Date(group.controls[date1].value).getTime() >= new Date(group.controls[date2].value).getTime()) {
                errors ? errors : (errors = {});            
                errors['maxDate'] = true;
                group.controls[date1].setErrors(errors);
                return {'maxDate': true};
            } else {
                errors && errors.maxDate ? delete errors.maxDate : '';
                errors && Object.keys(errors).length == 0 ? errors = null : '';
                group.controls[date1].setErrors(errors);
                return null;
            }     
        }
    }    

    //  to check the date is maximum than the supplied date.
    static minimumDate = (date1: string, date2: string) => {
        return (group: FormGroup) => {
            let errors = group.controls[date2].errors;
            if( new Date(group.controls[date2].value).getTime() <= new Date(group.controls[date1].value).getTime()) {
                errors ? errors : (errors = {});           
                errors['minimumDate'] = true;
                group.controls[date2].setErrors(errors);
                return {'minimumDate': true};
            } else {
                errors && errors.minimumDate ? delete errors.minimumDate : '';
                errors && Object.keys(errors).length == 0 ? errors = null : '';
                group.controls[date2].setErrors(errors);
                return null;
            }
        }
    }
}

// prepend zero to month / day
function  prependZero(value: number) : any {
    return value < 10 ? "0" + value : value;
  }