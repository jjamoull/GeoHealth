import {Component, Inject, Input, OnInit} from '@angular/core';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {FinalMapService} from '../../core/service/MapService/FinalMapService/finalMapService';
import {RiskFactorMapService} from '../../core/service/MapService/RiskMapService/riskFactorMapService';
import {FinalMap} from './final-map/final-map';
import {RiskFactor} from './risk-factor/risk-factor';

@Component({
  selector: 'app-pop-up',
  imports: [
    ReactiveFormsModule,
    FinalMap,
    RiskFactor
  ],
  templateUrl: './pop-up.component.html',
  styleUrl: './pop-up.component.css',
})
export class PopUpComponent implements OnInit {
  constructor(@Inject(MAT_DIALOG_DATA) public data: { typeOfPopUp: string }) {}
  //************ Constants ************
  addMap:string ="addMap";
  addRiskFactor : string = "addRiskFactor"
  //***********************************

  //******** Global variables *********
  typeOfPopUp: string = "null";
  //***********************************
  ngOnInit():void {
    this.typeOfPopUp = this.data.typeOfPopUp;
  }



  /**
   * Check the type of popup component that will be called and display it
   * */
  typeOfPopopIsValid(name:string){
    if (this.typeOfPopUp == name){
      return true;
    }else {
      return false;
    }
  }





}
