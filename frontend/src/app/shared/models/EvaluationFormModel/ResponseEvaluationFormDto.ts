export interface ResponseEvaluationFormDto {
  id:number,
  division: string,
  agreementLevel: number|null,
  perceivedRisk: string|null,
  certaintyLevel: number|null,
  comment: string | null,
  username: string,
  isPublic: boolean
}
