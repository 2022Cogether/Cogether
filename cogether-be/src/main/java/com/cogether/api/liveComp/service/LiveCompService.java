package com.cogether.api.liveComp.service;

import com.cogether.api.liveComp.repository.LiveCompRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LiveCompService {

    private final LiveCompRepository liveCompRepository;


}
