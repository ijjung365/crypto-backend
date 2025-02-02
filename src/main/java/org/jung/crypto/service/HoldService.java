package org.jung.crypto.service;

import org.jung.crypto.dto.HoldDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HoldService {
    public List<HoldDTO> fetchHolds(Pageable pageable);
}
