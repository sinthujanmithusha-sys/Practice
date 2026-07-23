package com.sgic.pratice_2.service;

import com.sgic.pratice_2.dto.SchoolDto;
import jakarta.validation.Valid;

import java.util.List;

public interface SchoolService {
    SchoolDto createSchool(@Valid SchoolDto schoolDto);

    List<SchoolDto> getAllStudents();
}
