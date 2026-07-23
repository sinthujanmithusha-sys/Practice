package com.sgic.pratice_2.service;

import com.sgic.pratice_2.dto.SchoolDto;
import com.sgic.pratice_2.entity.School;
import com.sgic.pratice_2.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchoolServiceImpl implements SchoolService{

    private final SchoolRepository schoolRepository;

    public SchoolServiceImpl(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Override
    public SchoolDto createSchool(SchoolDto schoolDto) {
        School school = new School();
        school.setName(schoolDto.getName());
        School saved = schoolRepository.save(school);
        return new SchoolDto(saved.getId(), saved.getName());
    }

    @Override
    public List<SchoolDto> getAllStudents() {
        return schoolRepository.findAll().stream()
                .map(school1 -> new SchoolDto(school1.getId(), school1.getName()))
                .collect(Collectors.toList());
    }
}
