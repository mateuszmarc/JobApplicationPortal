package com.marcykiewicz.mateusz.joba_application_portal.service;

import com.marcykiewicz.mateusz.joba_application_portal.entity.UsersType;
import com.marcykiewicz.mateusz.joba_application_portal.repository.UsersTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersTypeService {

    private final UsersTypeRepository usersTypeRepository;

    public List<UsersType> findAll() {
      return usersTypeRepository.findAll();
    }
}
