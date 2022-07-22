package com.cogether.api.til.service;

import com.cogether.api.til.repository.TilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TilService {

    private final TilRepository tilRepository;


}
