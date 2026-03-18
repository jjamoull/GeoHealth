export interface SaveValidationFormDto{
  department: string,
  agreementLevel: number|null,
  perceivedRisk:  string|null,
  certaintyLevel: number|null,
  comment: string|null,
  isPublic: boolean
}
