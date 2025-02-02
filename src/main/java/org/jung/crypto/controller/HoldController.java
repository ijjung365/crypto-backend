package org.jung.crypto.controller;

import lombok.RequiredArgsConstructor;
import org.jung.crypto.dto.HoldDTO;
import org.jung.crypto.service.HoldService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hold")
public class HoldController {
    private final HoldService holdService;

    @GetMapping("/get")
    public ResponseEntity<List<HoldDTO>> getHolds(@RequestParam(defaultValue = "1") int page,
                                                  @RequestParam(defaultValue = "30") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return ResponseEntity.ok(holdService.fetchHolds(pageable));
    }
}
