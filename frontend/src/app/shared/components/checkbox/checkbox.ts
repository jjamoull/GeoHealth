import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'app-checkbox',
  imports: [],
  standalone: true,
  templateUrl: './checkbox.html',
  styleUrl: './checkbox.css',
})
export class Checkbox {

  @Input() label?:string;
  @Input() checked:boolean = false;
  @Input() id:string = crypto.randomUUID();
  @Input() name?:string;
  @Input() disabled:boolean = false;

  @Output() checkedChange = new EventEmitter<boolean>();

  onChange(event: Event) {
    const input = event.target as HTMLInputElement;
    this.checked = input.checked;
    this.checkedChange.emit(this.checked);
  }
}
