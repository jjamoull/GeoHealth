package com.webgis.admin.evaluationform;

import com.webgis.MessageDto;
import com.webgis.admin.dto.evaluationform.AdminResponseEvaluationFormDto;
import com.webgis.evaluationform.EvaluationForm;
import com.webgis.evaluationform.EvaluationFormService;
import com.webgis.map.finalmap.FinalMap;
import com.webgis.map.finalmap.FinalMapService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/evaluationForm")
public class AdminEvaluationFromController {

    private final EvaluationFormService evaluationFormService;
    private final FinalMapService finalMapService;

    public AdminEvaluationFromController(EvaluationFormService evaluationFormService,
                                    FinalMapService finalMapService) {
        this.evaluationFormService = evaluationFormService;
        this.finalMapService=finalMapService;
    }

    /**
     * Get all existing form for a specific map
     *
     * @param finalMapId the id of the map you are interested in
     *
     * @return the froms information for a map in a list, not found if the map doesn't exist
     */
    @GetMapping("/allFormForFinalMap/{finalMapId}")
    public ResponseEntity<Object> getAllFormForFinalMap(
            @PathVariable long finalMapId
    ){
        final Optional<FinalMap> optionalFinalMap= finalMapService.findById(finalMapId);
        if(optionalFinalMap.isEmpty()){
            return ResponseEntity.status(404).body(new MessageDto("The selected map does not exist"));
        }
        final FinalMap finalMap= optionalFinalMap.get();

        final List<EvaluationForm> EvaluationForms = evaluationFormService.getAllFormForFinalMap(finalMap);
        final List<AdminResponseEvaluationFormDto> responseEvaluationForms = new ArrayList<>();
        for(EvaluationForm evaluationForm : EvaluationForms){
            if (evaluationForm.getUser().isDeleted()) {
                evaluationForm.getUser().setUsername("Deleted user");
                evaluationForm.getUser().setFirstName(" ");
                evaluationForm.getUser().setLastName(" ");
            }
            responseEvaluationForms.add(new AdminResponseEvaluationFormDto(evaluationForm));
        }
        return ResponseEntity.status(200).body(responseEvaluationForms);
    }

}
