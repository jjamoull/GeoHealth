export interface UpdateValidationFormDto{
  id: number,
  agreementLevel: number|null,
  perceivedRisk: String|null,
  certaintyLevel: String|null,
  comment: String|null,
  isPublic: boolean
}
