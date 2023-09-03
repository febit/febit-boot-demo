package org.febit.boot.demo.doggy.dao;

import org.febit.boot.jooq.BaseCurdDao;
import org.febit.boot.jooq.IEntity;
import org.febit.boot.jooq.ITable;
import org.jooq.Configuration;
import org.jooq.UpdatableRecord;

public abstract class BaseExtraDao<TB extends ITable<R, ID>, PO extends IEntity<ID>, ID, R extends UpdatableRecord<R>>
        extends BaseCurdDao<TB, PO, ID, R> {

    protected BaseExtraDao(Configuration conf) {
        super(conf);
    }
}
