import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AdminRasterMapService } from '../../../../../../core/service/AdminService/AdminMapService/AdminRiskFactorMapService';
import { InputboxComponents } from '../../../../../../shared/components/inputbox.components/inputbox.components';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-risk-factor-form',
  standalone: true,
  imports: [ReactiveFormsModule, InputboxComponents],
  templateUrl: './risk-factor-form.html',
  styleUrl: './risk-factor-form.css'
})
export class RiskFactorFormComponent implements OnInit {

  constructor(
    private dialog: MatDialogRef<RiskFactorFormComponent>,
    private adminRasterMapService: AdminRasterMapService
  ) {}

  formGroup!: FormGroup;
  selectedTifFile: File | null = null;
  isUploading = false;

  ngOnInit(): void {
    this.formGroup = new FormGroup({
      title: new FormControl('', [Validators.required, Validators.minLength(1)]),
      description: new FormControl('', [Validators.required, Validators.minLength(1)]),
      tifFile: new FormControl(null, [Validators.required])
    });
  }

  onTifFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedTifFile = input.files[0];
      this.formGroup.get('tifFile')?.setValue(this.selectedTifFile.name);
    }
  }

  isFieldValid(name: string): boolean {
    const formControl = this.formGroup.get(name);
    return !!(formControl?.invalid && formControl?.dirty);
  }

  onSubmit(): void {
    if (!this.selectedTifFile) return;

    this.isUploading = true;
    const formData = new FormData();
    formData.append('title', this.formGroup.value.title);
    formData.append('description', this.formGroup.value.description);
    formData.append('typeOfRaster', 'risk_factor');
    formData.append('tifFile', this.selectedTifFile);

    this.adminRasterMapService.uploadRasterMap(formData).subscribe({
      next: () => {
        this.isUploading = false;
        this.dialog.close();
      },
      error: (err) => {
        console.error(err);
        this.isUploading = false;
      }
    });
  }
}
