package com.cogether.api.liveCoop.web;

import com.cogether.api.liveCoop.service.LiveCoopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class LiveCoopController {

    private final LiveCoopService liveCoopService;


}
