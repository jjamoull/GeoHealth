import { signal } from '@angular/core';
import { DivisionRiskDto } from '../../shared/models/MeasureModel/DivisionRiskDto';
import { EvaluatorAgreementMeasureService } from '../../core/service/MeasureService/EvaluatorAgreementMeasureService/evaluatorAgreementMeasureService';
import { MeanMeasureService } from '../../core/service/MeasureService/MeanMeasureService/meanMeasureService';
import { ModelEvaluationMeasureService } from '../../core/service/MeasureService/ModelEvaluationMeasureService/modelEvaluationMeasureService';

export class MapMetrics {
  
  // All the metrics computed
  divisionalConsensusScore = signal<number | null>(null);
  nationalConsensusScore = signal<number | null>(null);
  krippendorffAlpha = signal<number | null>(null);

  meanDivisionalAgreementScore = signal<number | null>(null);
  meanCertaintyForMapForDivision = signal<number | null>(null);
  dominantPerceivedRiskLevel = signal<string | null>(null);

  weightedDivisionalLevelAgreementScore = signal<number | null>(null);
  nationalModelFieldAgreementScore = signal<number | null>(null);


  constructor(
    private evaluatorAgreementMeasureService: EvaluatorAgreementMeasureService,
    private meanMeasureService: MeanMeasureService,
    private modelEvaluationMeasureService: ModelEvaluationMeasureService
  ) {}

  /**
   * Set the value for all the metrics of the class
   *
   * @param mapId the map for which you want the metrics
   * @param division the division on which the user is clicking
   * @param divisionRisk the risk level for the division on which the user is clicking
   * @param divisionRiskDto a map containing the risk level for all divisions
   */
  computeAllMetrics(mapId: number, division: string, divisionRisk: string, divisionRiskDto: DivisionRiskDto) {
    this.computeEvaluatorAgreement(mapId, division);
    this.computeMeanMeasures(mapId, division);
    this.computeModelEvaluation(mapId,division,divisionRisk, divisionRiskDto);
  }

  /**
   * Compute all the metrics linked to agreement between evaluators
   *
   * @param mapId the map for which you want the metrics
   * @param division the division on which the user is clicking
   */
  private computeEvaluatorAgreement(mapId: number, division: string) {

    this.evaluatorAgreementMeasureService
      .getDivisionalConsensusScore(mapId, division)
      .subscribe({
        next: (score) => this.divisionalConsensusScore.set(score),
        error: (err) => console.error('Divisional consensus error', err)
      });

    this.evaluatorAgreementMeasureService
      .getNationalConsensusScore(mapId)
      .subscribe({
        next: (score) => this.nationalConsensusScore.set(score),
        error: (err) => console.error('National consensus error', err)
      });

    this.evaluatorAgreementMeasureService
      .getKrippendorff(mapId)
      .subscribe({
        next: (alpha) => this.krippendorffAlpha.set(alpha),
        error: (err) => console.error('Krippendorff error', err)
      });
  }

  /**
   * Compute all the metrics linked to making the mean of evaluation forms fields values
   *
   * @param mapId the map for which you want the metrics
   * @param division the division on which the user is clicking
   */
  private computeMeanMeasures(mapId: number, division: string) {

    this.meanMeasureService
      .getMeanDivisionalAgreementScore(mapId,division)
      .subscribe({
        next: (score) => this.meanDivisionalAgreementScore.set(score),
        error: (err) => console.error('Mean divisional agreement error', err)
      });

    this.meanMeasureService
      .getMeanCertaintyForMapForDivision(mapId,division)
      .subscribe({
        next: (score) => this.meanCertaintyForMapForDivision.set(score),
        error: (err) => console.error('Mean Certainty error', err)
      });

    this.meanMeasureService
      .getDominantPerceivedRiskLevelForMapForDivision(mapId,division)
      .subscribe({
        next: (riskLevel) => this.dominantPerceivedRiskLevel.set(riskLevel),
        error: (err) => console.error('Dominant perceived riskLevel error', err)
      });
  }

  /**
   * Compute the metrics linked to the assessment of the model by the evaluators
   *
   * @param mapId the map for which you want the metrics
   * @param division the division on which the user is clicking
   * @param divisionRisk the risk level for the division on which the user is clicking
   * @param divisionRiskDto a map containing the risk level for all divisions
   */
  private computeModelEvaluation(mapId: number, division: string, divisionRisk:string,divisionRiskDto: DivisionRiskDto) {

    this.modelEvaluationMeasureService
      .getWeightedDivisionalLevelAgreementScore(mapId, division,divisionRisk)
      .subscribe({
        next: (score) => this.weightedDivisionalLevelAgreementScore.set(score),
        error: (err) => console.log('Weighted Divisional Level Agreement Score error', err)
      });

    this.modelEvaluationMeasureService
      .getNationalModelFieldAgreementScore(mapId,divisionRiskDto)
      .subscribe({
        next:(score) => this.nationalModelFieldAgreementScore.set(score),
        error: (err) =>console.log('National Model Field Agreement Score error', err)
      });

  }

  /**
   * Reset all the computed value to null
   */
  resetAllMetrics() {
    this.divisionalConsensusScore.set(null);
    this.nationalConsensusScore.set(null);
    this.krippendorffAlpha.set(null);

    this.meanDivisionalAgreementScore.set(null);
    this.meanCertaintyForMapForDivision.set(null);
    this.dominantPerceivedRiskLevel.set(null);

    this.weightedDivisionalLevelAgreementScore.set(null);
    this.nationalModelFieldAgreementScore.set(null);
  }
}

