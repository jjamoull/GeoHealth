import { signal } from '@angular/core';
import { DivisionRiskDto } from '../../shared/models/MeasureModel/DivisionRiskDto';
import { EvaluatorAgreementMeasureService } from '../../core/service/MeasureService/EvaluatorAgreementMeasureService/evaluatorAgreementMeasureService';
import { MeanMeasureService } from '../../core/service/MeasureService/MeanMeasureService/meanMeasureService';
import { ModelEvaluationMeasureService } from '../../core/service/MeasureService/ModelEvaluationMeasureService/modelEvaluationMeasureService';

export class MapMetrics {

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

  computeAllMetrics(mapId: number, division: string, divisionRisk: string, divisionRiskDto: DivisionRiskDto) {
    this.computeEvaluatorAgreement(mapId, division);
    this.computeMeanMeasures(mapId, division);
    this.computeModelEvaluation(mapId,division,divisionRisk, divisionRiskDto);
  }

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

