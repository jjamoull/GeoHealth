export interface AdminResponseEvaluationFormDto {
  id:number,
  division: string,
  agreementLevel: number|null,
  perceivedRisk: string|null,
  certaintyLevel: number|null,
  comment: string | null,
  username: string,
  firstName: string,
  lastName: string,
  isPublic: boolean
}
