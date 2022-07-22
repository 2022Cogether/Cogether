package com.cogether.api.til.web;

import com.cogether.api.til.service.TilService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class TilController {

    private final TilService tilService;



}
