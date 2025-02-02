package org.jung.crypto.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jung.crypto.domain.Hold;
import org.jung.crypto.domain.User;
import org.jung.crypto.dto.CustomUserDetails;
import org.jung.crypto.dto.HoldDTO;
import org.jung.crypto.repository.HoldRepository;
import org.jung.crypto.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Log4j2
public class HoldServiceImpl implements HoldService {
    private final ModelMapper modelMapper;
    private final HoldRepository holdRepository;
    private final UserRepository userRepository;

    @Override
    public List<HoldDTO> fetchHolds(Pageable pageable) {
        log.info("Fetching holds from database");
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(customUserDetails.getUsername()).orElseThrow();
        List<Hold> holdList = holdRepository.search(user, pageable);

        List<HoldDTO> holdDTOList = holdList.stream()
                .map(hold -> modelMapper.map(hold, HoldDTO.class))
                .collect(Collectors.toList());
        log.info(holdDTOList);

        return holdDTOList;
    }
}
