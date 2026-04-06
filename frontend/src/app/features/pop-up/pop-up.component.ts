import {Component, Inject, Input, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {ReactiveFormsModule} from '@angular/forms';
import {FinalMap} from './final-map/final-map';
import {RiskFactor} from './risk-factor/risk-factor';
import {FinalRasterMap} from './final-raster-map/final-raster-map';

@Component({
  selector: 'app-pop-up',
  imports: [
    ReactiveFormsModule,
    FinalMap,
    RiskFactor,
    FinalRasterMap
  ],
  templateUrl: './pop-up.component.html',
  styleUrl: './pop-up.component.css',
})
export class PopUpComponent implements OnInit {
  constructor(@Inject(MAT_DIALOG_DATA) public data: { typeOfPopUp: string }) {}
  //************ Constants ************
  addMap:string ="addMap";
  addRiskFactor : string = "addRiskFactor";
  addFinalRasterMap :string = "addFinalRasterMap";
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
