export interface SaveValidationFormDto{
  department: String,
  agreementLevel: number|null,
  perceivedRisk:  String|null,
  certaintyLevel: number|null,
  comment: String|null,
  isPublic: boolean
}
