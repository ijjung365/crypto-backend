package org.jung.crypto.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.jung.crypto.domain.QTransaction;
import org.jung.crypto.domain.Transaction;
import org.jung.crypto.domain.TransactionType;
import org.jung.crypto.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class TransactionSearchImpl extends QuerydslRepositorySupport implements TransactionSearch {
    public TransactionSearchImpl() {
        super(Transaction.class);
    }

    @Override
    public List<Transaction> search(User user, TransactionType transactionType, Pageable pageable) {
        QTransaction transaction = QTransaction.transaction;
        JPQLQuery<Transaction> query = from(transaction);

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if(transactionType == TransactionType.BUY){
            booleanBuilder.and(transaction.type.eq(TransactionType.BUY));
        } else if(transactionType == TransactionType.SELL){
            booleanBuilder.and(transaction.type.eq(TransactionType.SELL));
        }
        booleanBuilder.and(transaction.user.eq(user));

        query.where(booleanBuilder);
        query.orderBy(transaction.updatedDate.desc());

        this.getQuerydsl().applyPagination(pageable, query);
        return query.fetch();
    }
}
