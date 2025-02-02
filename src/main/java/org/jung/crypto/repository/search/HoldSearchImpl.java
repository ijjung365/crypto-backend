package org.jung.crypto.repository.search;

import com.querydsl.jpa.JPQLQuery;
import org.jung.crypto.domain.Hold;
import org.jung.crypto.domain.QHold;
import org.jung.crypto.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class HoldSearchImpl extends QuerydslRepositorySupport implements HoldSearch {
    public HoldSearchImpl() {
        super(Hold.class);
    }

    @Override
    public List<Hold> search(User user, Pageable pageable) {
        QHold hold = QHold.hold;
        JPQLQuery<Hold> query = from(hold);

        query.where(hold.user.eq(user));
        query.orderBy(hold.updatedDate.desc());

        this.getQuerydsl().applyPagination(pageable, query);

        return query.fetch();
    }
}
