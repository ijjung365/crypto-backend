package org.jung.crypto.repository.search;

import org.jung.crypto.domain.Hold;
import org.jung.crypto.domain.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HoldSearch {
    List<Hold> search(User user, Pageable pageable);
}
