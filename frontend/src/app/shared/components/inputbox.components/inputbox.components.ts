import {Component, forwardRef, Input} from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';


@Component({
  standalone: true,
  selector: 'app-inputbox',
  imports: [],
  templateUrl: './inputbox.components.html',
  styleUrl: './inputbox.components.css',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => InputboxComponents),
      multi: true
    }
  ]
})
export class InputboxComponents implements ControlValueAccessor {

  @Input() type:'text' | 'password' | 'email' | 'number'='text';
  @Input() placeholder:string='';
  @Input() disabled:boolean = false;
  @Input() label:string='';
  value = '';

  onChange = (value: any) => {};
  onTouched = () => {};

  writeValue(value: any): void {
    this.value = value ?? '';
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }


}
