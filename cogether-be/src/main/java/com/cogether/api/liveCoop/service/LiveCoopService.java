package com.cogether.api.liveCoop.service;

import com.cogether.api.liveCoop.repository.LiveCoopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LiveCoopService {

    private final LiveCoopRepository liveCoopRepository;


}
