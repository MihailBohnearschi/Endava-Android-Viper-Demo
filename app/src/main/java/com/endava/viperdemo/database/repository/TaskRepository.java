package com.endava.viperdemo.database.repository;

import com.endava.viperdemo.database.dao.DaoSession;
import com.endava.viperdemo.database.domain.Task;
import java.util.List;

public class TaskRepository {

  private final DaoSession daoSession;

  public TaskRepository(DaoSession daoSession) {
    this.daoSession = daoSession;
  }

  public void saveAll(List<Task> incomes) {
    this.daoSession.getTaskDao().saveInTx(incomes);
  }

  public void deleteAll() {
    this.daoSession.getTaskDao().deleteAll();
  }

  public List<Task> getAll() {
    return this.daoSession.getTaskDao().loadAll();
  }
}