package com.moviesearch.datasource.database;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class FilmDao_Impl implements FilmDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Film> __insertionAdapterOfFilm;

  private final EntityInsertionAdapter<Page> __insertionAdapterOfPage;

  private final EntityInsertionAdapter<Favourite> __insertionAdapterOfFavourite;

  private final SharedSQLiteStatement __preparedStmtOfDeleteFavouriteIdKp;

  public FilmDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFilm = new EntityInsertionAdapter<Film>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `list_films` (`name`,`id_kp`,`short_description`,`preview_url`,`alternativeName`,`page_id`,`id`) VALUES (?,?,?,?,?,?,nullif(?, 0))";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Film value) {
        if (value.getName() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getName());
        }
        stmt.bindLong(2, value.getIdKp());
        if (value.getShortDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getShortDescription());
        }
        if (value.getPreviewUrl() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getPreviewUrl());
        }
        if (value.getAlternativeName() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getAlternativeName());
        }
        stmt.bindLong(6, value.getPage());
        stmt.bindLong(7, value.getId());
      }
    };
    this.__insertionAdapterOfPage = new EntityInsertionAdapter<Page>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `pages` (`num`,`id`) VALUES (?,nullif(?, 0))";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Page value) {
        stmt.bindLong(1, value.getNum());
        stmt.bindLong(2, value.getId());
      }
    };
    this.__insertionAdapterOfFavourite = new EntityInsertionAdapter<Favourite>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `favourites` (`name`,`id_kp`,`short_description`,`preview_url`,`alternativeName`,`id`) VALUES (?,?,?,?,?,nullif(?, 0))";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Favourite value) {
        if (value.getName() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getName());
        }
        stmt.bindLong(2, value.getIdKp());
        if (value.getShortDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getShortDescription());
        }
        if (value.getPreviewUrl() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getPreviewUrl());
        }
        if (value.getAlternativeName() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getAlternativeName());
        }
        stmt.bindLong(6, value.getId());
      }
    };
    this.__preparedStmtOfDeleteFavouriteIdKp = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM favourites WHERE favourites.id_kp = ?";
        return _query;
      }
    };
  }

  @Override
  public long insertFilm(final Film film) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfFilm.insertAndReturnId(film);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public long insertPage(final Page page) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfPage.insertAndReturnId(page);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public long insertFavourite(final Favourite film) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfFavourite.insertAndReturnId(film);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteFavouriteIdKp(final int idKp) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteFavouriteIdKp.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, idKp);
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteFavouriteIdKp.release(_stmt);
    }
  }

  @Override
  public long getPage(final int page) {
    final String _sql = "SELECT pages.id FROM pages WHERE pages.num = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, page);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final long _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getLong(0);
      } else {
        _result = 0L;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Favourite> getFavourites() {
    final String _sql = "SELECT * FROM favourites";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfIdKp = CursorUtil.getColumnIndexOrThrow(_cursor, "id_kp");
      final int _cursorIndexOfShortDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "short_description");
      final int _cursorIndexOfPreviewUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "preview_url");
      final int _cursorIndexOfAlternativeName = CursorUtil.getColumnIndexOrThrow(_cursor, "alternativeName");
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final List<Favourite> _result = new ArrayList<Favourite>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Favourite _item;
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final int _tmpIdKp;
        _tmpIdKp = _cursor.getInt(_cursorIndexOfIdKp);
        final String _tmpShortDescription;
        if (_cursor.isNull(_cursorIndexOfShortDescription)) {
          _tmpShortDescription = null;
        } else {
          _tmpShortDescription = _cursor.getString(_cursorIndexOfShortDescription);
        }
        final String _tmpPreviewUrl;
        if (_cursor.isNull(_cursorIndexOfPreviewUrl)) {
          _tmpPreviewUrl = null;
        } else {
          _tmpPreviewUrl = _cursor.getString(_cursorIndexOfPreviewUrl);
        }
        final String _tmpAlternativeName;
        if (_cursor.isNull(_cursorIndexOfAlternativeName)) {
          _tmpAlternativeName = null;
        } else {
          _tmpAlternativeName = _cursor.getString(_cursorIndexOfAlternativeName);
        }
        _item = new Favourite(_tmpName,_tmpIdKp,_tmpShortDescription,_tmpPreviewUrl,_tmpAlternativeName);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public long getLiked(final int idKp) {
    final String _sql = "SELECT id FROM favourites WHERE id_kp = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, idKp);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final long _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getLong(0);
      } else {
        _result = 0L;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Film> getPageFilms(final long pageId) {
    final String _sql = "SELECT * FROM list_films WHERE list_films.page_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, pageId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfIdKp = CursorUtil.getColumnIndexOrThrow(_cursor, "id_kp");
      final int _cursorIndexOfShortDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "short_description");
      final int _cursorIndexOfPreviewUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "preview_url");
      final int _cursorIndexOfAlternativeName = CursorUtil.getColumnIndexOrThrow(_cursor, "alternativeName");
      final int _cursorIndexOfPage = CursorUtil.getColumnIndexOrThrow(_cursor, "page_id");
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final List<Film> _result = new ArrayList<Film>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Film _item;
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final int _tmpIdKp;
        _tmpIdKp = _cursor.getInt(_cursorIndexOfIdKp);
        final String _tmpShortDescription;
        if (_cursor.isNull(_cursorIndexOfShortDescription)) {
          _tmpShortDescription = null;
        } else {
          _tmpShortDescription = _cursor.getString(_cursorIndexOfShortDescription);
        }
        final String _tmpPreviewUrl;
        if (_cursor.isNull(_cursorIndexOfPreviewUrl)) {
          _tmpPreviewUrl = null;
        } else {
          _tmpPreviewUrl = _cursor.getString(_cursorIndexOfPreviewUrl);
        }
        final String _tmpAlternativeName;
        if (_cursor.isNull(_cursorIndexOfAlternativeName)) {
          _tmpAlternativeName = null;
        } else {
          _tmpAlternativeName = _cursor.getString(_cursorIndexOfAlternativeName);
        }
        final long _tmpPage;
        _tmpPage = _cursor.getLong(_cursorIndexOfPage);
        _item = new Film(_tmpName,_tmpIdKp,_tmpShortDescription,_tmpPreviewUrl,_tmpAlternativeName,_tmpPage);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
