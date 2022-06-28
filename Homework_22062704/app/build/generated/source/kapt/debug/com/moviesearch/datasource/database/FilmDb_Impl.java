package com.moviesearch.datasource.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class FilmDb_Impl extends FilmDb {
  private volatile FilmDao _filmDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `list_films` (`name` TEXT NOT NULL, `id_kp` INTEGER NOT NULL, `short_description` TEXT NOT NULL, `preview_url` TEXT NOT NULL, `alternativeName` TEXT NOT NULL, `page_id` INTEGER NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, FOREIGN KEY(`page_id`) REFERENCES `pages`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_list_films_id_kp` ON `list_films` (`id_kp`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `pages` (`num` INTEGER NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL)");
        _db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_pages_num` ON `pages` (`num`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `favourites` (`name` TEXT NOT NULL, `id_kp` INTEGER NOT NULL, `short_description` TEXT NOT NULL, `preview_url` TEXT NOT NULL, `alternativeName` TEXT NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL)");
        _db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_favourites_id_kp` ON `favourites` (`id_kp`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '431b7cd6aff5b0b766c0ea0503961d8c')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `list_films`");
        _db.execSQL("DROP TABLE IF EXISTS `pages`");
        _db.execSQL("DROP TABLE IF EXISTS `favourites`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsListFilms = new HashMap<String, TableInfo.Column>(7);
        _columnsListFilms.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsListFilms.put("id_kp", new TableInfo.Column("id_kp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsListFilms.put("short_description", new TableInfo.Column("short_description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsListFilms.put("preview_url", new TableInfo.Column("preview_url", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsListFilms.put("alternativeName", new TableInfo.Column("alternativeName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsListFilms.put("page_id", new TableInfo.Column("page_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsListFilms.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysListFilms = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysListFilms.add(new TableInfo.ForeignKey("pages", "CASCADE", "NO ACTION",Arrays.asList("page_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesListFilms = new HashSet<TableInfo.Index>(1);
        _indicesListFilms.add(new TableInfo.Index("index_list_films_id_kp", true, Arrays.asList("id_kp"), Arrays.asList("ASC")));
        final TableInfo _infoListFilms = new TableInfo("list_films", _columnsListFilms, _foreignKeysListFilms, _indicesListFilms);
        final TableInfo _existingListFilms = TableInfo.read(_db, "list_films");
        if (! _infoListFilms.equals(_existingListFilms)) {
          return new RoomOpenHelper.ValidationResult(false, "list_films(com.moviesearch.datasource.database.Film).\n"
                  + " Expected:\n" + _infoListFilms + "\n"
                  + " Found:\n" + _existingListFilms);
        }
        final HashMap<String, TableInfo.Column> _columnsPages = new HashMap<String, TableInfo.Column>(2);
        _columnsPages.put("num", new TableInfo.Column("num", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPages.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPages = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPages = new HashSet<TableInfo.Index>(1);
        _indicesPages.add(new TableInfo.Index("index_pages_num", true, Arrays.asList("num"), Arrays.asList("ASC")));
        final TableInfo _infoPages = new TableInfo("pages", _columnsPages, _foreignKeysPages, _indicesPages);
        final TableInfo _existingPages = TableInfo.read(_db, "pages");
        if (! _infoPages.equals(_existingPages)) {
          return new RoomOpenHelper.ValidationResult(false, "pages(com.moviesearch.datasource.database.Page).\n"
                  + " Expected:\n" + _infoPages + "\n"
                  + " Found:\n" + _existingPages);
        }
        final HashMap<String, TableInfo.Column> _columnsFavourites = new HashMap<String, TableInfo.Column>(6);
        _columnsFavourites.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavourites.put("id_kp", new TableInfo.Column("id_kp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavourites.put("short_description", new TableInfo.Column("short_description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavourites.put("preview_url", new TableInfo.Column("preview_url", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavourites.put("alternativeName", new TableInfo.Column("alternativeName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavourites.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFavourites = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFavourites = new HashSet<TableInfo.Index>(1);
        _indicesFavourites.add(new TableInfo.Index("index_favourites_id_kp", true, Arrays.asList("id_kp"), Arrays.asList("ASC")));
        final TableInfo _infoFavourites = new TableInfo("favourites", _columnsFavourites, _foreignKeysFavourites, _indicesFavourites);
        final TableInfo _existingFavourites = TableInfo.read(_db, "favourites");
        if (! _infoFavourites.equals(_existingFavourites)) {
          return new RoomOpenHelper.ValidationResult(false, "favourites(com.moviesearch.datasource.database.Favourite).\n"
                  + " Expected:\n" + _infoFavourites + "\n"
                  + " Found:\n" + _existingFavourites);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "431b7cd6aff5b0b766c0ea0503961d8c", "01ed0917d133d9fffa11b8600e0a07df");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "list_films","pages","favourites");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `list_films`");
      _db.execSQL("DELETE FROM `pages`");
      _db.execSQL("DELETE FROM `favourites`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(FilmDao.class, FilmDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public FilmDao filmDao() {
    if (_filmDao != null) {
      return _filmDao;
    } else {
      synchronized(this) {
        if(_filmDao == null) {
          _filmDao = new FilmDao_Impl(this);
        }
        return _filmDao;
      }
    }
  }
}
