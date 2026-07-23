package com.sgic.pratice_2.contoller;

import com.sgic.pratice_2.Utils.EndpointBundle;
import com.sgic.pratice_2.Utils.ResponseWrapper;
import com.sgic.pratice_2.Utils.ValidationMessages;
import com.sgic.pratice_2.dto.SchoolDto;
import com.sgic.pratice_2.enums.RestApiResponseStatusCodes;
import com.sgic.pratice_2.service.SchoolService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndpointBundle.SCHOOL)
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<SchoolDto>> createSchool(@Valid @RequestBody SchoolDto schoolDto){
        SchoolDto createSchool = schoolService.createSchool(schoolDto);
        if (createSchool != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.CREATED.getCode(),
                    ValidationMessages.CREATED_SUCCESSFULL,
                    createSchool
            ));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NO_CONTENT.getCode(),
                    ValidationMessages.CREATED_FAILED,
                    null
            ));
        }
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<SchoolDto>>> getAllStudents(){
        List<SchoolDto> getAllStudents = schoolService.getAllStudents();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.SUCCESS.getCode(),
                ValidationMessages.RETRIEVED,
                getAllStudents
        ));
    }
}
