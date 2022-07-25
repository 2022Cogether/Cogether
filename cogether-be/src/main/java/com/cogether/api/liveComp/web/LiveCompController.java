package com.cogether.api.liveComp.web;

import com.cogether.api.liveComp.service.LiveCompService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class LiveCompController {

    private final LiveCompService liveCompService;

}
