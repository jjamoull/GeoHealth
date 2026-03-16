export interface ResponseValidationFormDto{
  id:number,
  department: String,
  agreementLevel: number|null,
  perceivedRisk: String|null,
  certaintyLevel: number|null,
  comment: String|null,
  username: String,
  isPublic: boolean
}
