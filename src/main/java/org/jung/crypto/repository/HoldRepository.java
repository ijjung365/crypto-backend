package org.jung.crypto.repository;

import org.jung.crypto.domain.Hold;
import org.jung.crypto.domain.User;
import org.jung.crypto.repository.search.HoldSearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HoldRepository extends JpaRepository<Hold, Long> , HoldSearch {
    Optional<Hold> findByUserAndSymbol(User user, String symbol);
    List<Hold> findAllByUser(User user);
}
