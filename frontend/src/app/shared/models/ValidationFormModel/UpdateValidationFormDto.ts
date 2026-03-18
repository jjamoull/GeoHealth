export interface UpdateValidationFormDto{
  id: number,
  agreementLevel: number|null,
  perceivedRisk: string|null,
  certaintyLevel: string|null,
  comment: string|null,
  isPublic: boolean
}
