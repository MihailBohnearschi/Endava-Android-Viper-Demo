package com.endava.viperdemo.application.dagger;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.endava.viperdemo.database.dao.DaoMaster;
import com.endava.viperdemo.database.dao.DaoSession;
import com.endava.viperdemo.database.repository.TaskRepository;
import com.endava.viperdemo.utils.MigrationUtils;
import dagger.Module;
import dagger.Provides;

@Module
class DataModule {

  private static final String DB_NAME = "cashex-db";
  private static final String DB_PASSWORD = "db_secret_password";

  @AppScope
  @Provides
  SharedPreferences sharedPreferences(Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context);
  }

  @AppScope
  @Provides
  DaoSession daoSession(Context context) {
    DaoMaster.OpenHelper helper = new MigrationUtils.MyOpenHelper(context, DB_NAME, null);
    DaoMaster daoMaster = new DaoMaster(helper.getEncryptedWritableDb(DB_PASSWORD));
    return daoMaster.newSession();
  }

  @AppScope
  @Provides
  public TaskRepository taskRepository(DaoSession daoSession) {
    return new TaskRepository(daoSession);
  }
}