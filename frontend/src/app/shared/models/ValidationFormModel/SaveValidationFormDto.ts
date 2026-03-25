export interface SaveValidationFormDto{
  division: string,
  agreementLevel: number|null,
  perceivedRisk:  string|null,
  certaintyLevel: number|null,
  comment: string|null,
  finalMapId: number,
  isPublic: boolean
}
