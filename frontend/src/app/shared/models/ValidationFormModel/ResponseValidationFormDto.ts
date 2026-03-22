export interface ResponseValidationFormDto{
  id:number,
  department: string,
  agreementLevel: number|null,
  perceivedRisk: string|null,
  certaintyLevel: number|null,
  comment: string | null,
  username: string,
  isPublic: boolean
}
