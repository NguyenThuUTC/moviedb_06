package com.framgia.movie06.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.framgia.movie06.data.model.Cast;
import com.framgia.movie06.data.model.Genre;
import com.framgia.movie06.data.model.Movie;
import com.framgia.movie06.data.model.ProductionCompany;
import com.framgia.movie06.data.model.ProductionCountry;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 11/29/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "movie.db";
    public static final int DATABASE_VERSION = 8;

    public static final String TABLE_GENRE = "tbl_genre";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COMMAND_DELETE_TABLE_GENRE = "DROP TABLE IF EXISTS " + TABLE_GENRE;
    public static final String COMMAND_CREATE_TABLE_GENRE = "CREATE TABLE " + TABLE_GENRE + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_NAME + " TEXT)";

    public static final String TABLE_MOVIE = "tbl_movie";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_COUNTRIES = "countries";
    public static final String COLUMN_POSTER_PATH = "poster_path";
    public static final String COLUMN_RELEASE_DATE = "release_date";
    public static final String COLUMN_FIRST_AIR_DATE = "first_air_date";
    public static final String COLUMN_VOTE_COUNT = "vote_count";
    public static final String COLUMN_VOTE_AVERAGE = "vote_average";
    public static final String COLUMN_OVERVIEW = "overview";
    public static final String COMMAND_DELETE_TABLE_MOVIE = "DROP TABLE IF EXISTS " + TABLE_MOVIE;
    public static final String COMMAND_CREATE_TABLE_MOVIE = "CREATE TABLE "
            + TABLE_MOVIE
            + "("
            +
            COLUMN_ID
            + " INTEGER PRIMARY KEY,"
            + COLUMN_TITLE
            + " TEXT ,"
            + COLUMN_NAME
            + " TEXT ,"
            + COLUMN_COUNTRIES
            + " TEXT,"
            + COLUMN_POSTER_PATH
            + " TEXT, "
            + COLUMN_RELEASE_DATE
            + ", TEXT, "
            + COLUMN_FIRST_AIR_DATE
            + " TEXT ,"
            + COLUMN_VOTE_COUNT
            + " INTEGER, "
            + COLUMN_VOTE_AVERAGE
            + " REAL, "
            + COLUMN_OVERVIEW
            + " TEXT )";

    public static final String TABLE_GENRE_MOVIE = "tbl_genre_movie";
    public static final String COLUMN_ID_GENRE = "id_genre";
    public static final String COLUMN_ID_MOVIE = "id_movie";
    public static final String COMMAND_DELETE_TABLE_GENRE_MOVIE =
            "DROP TABLE IF EXISTS " + TABLE_GENRE_MOVIE;
    public static final String COMMAND_CREATE_TABLE_GENRE_MOVIE =
            "CREATE TABLE "
                    + TABLE_GENRE_MOVIE
                    + "("
                    +
                    COLUMN_ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_ID_GENRE
                    + " INTEGER,"
                    + COLUMN_ID_MOVIE
                    + " INTERGER)";

    public static final String TABLE_COMPANY = "tbl_company";
    public static final String COMMAND_DELETE_TABLE_COMPANY =
            "DROP TABLE IF EXISTS " + TABLE_COMPANY;
    public static final String COMMAND_CREATE_TABLE_COMPANY =
            "CREATE TABLE " + TABLE_COMPANY + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_NAME + " TEXT)";

    public static final String TABLE_COMPANY_MOVIE = "tbl_company_movie";
    public static final String COLUMN_ID_COMPANY = "id_company";
    public static final String COMMAND_DELETE_TABLE_COMPANY_MOVIE =
            "DROP TABLE IF EXISTS " + TABLE_COMPANY_MOVIE;
    public static final String COMMAND_CREATE_TABLE_COMPANY_MOVIE =
            "CREATE TABLE "
                    + TABLE_COMPANY_MOVIE
                    + "("
                    +
                    COLUMN_ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_ID_COMPANY
                    + " INTEGER,"
                    + COLUMN_ID_MOVIE
                    + " INTERGER)";

    public static final String TABLE_COUNTRY = "tbl_country";
    public static final String COMMAND_DELETE_TABLE_COUNTRY =
            "DROP TABLE IF EXISTS " + TABLE_COUNTRY;
    public static final String COMMAND_CREATE_TABLE_COUNTRY =
            "CREATE TABLE " + TABLE_COUNTRY + "(" +
                    COLUMN_ID + " TEXT PRIMARY KEY, " + COLUMN_NAME + " TEXT)";

    public static final String TABLE_COUNTRY_MOVIE = "tbl_country_movie";
    public static final String COLUMN_ID_COUNTRY = "id_country";
    public static final String COMMAND_DELETE_TABLE_COUNTRY_MOVIE =
            "DROP TABLE IF EXISTS " + TABLE_COUNTRY_MOVIE;
    public static final String COMMAND_CREATE_TABLE_COUNTRY_MOVIE = "CREATE TABLE "
            + TABLE_COUNTRY_MOVIE
            + "("
            + COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ID_COUNTRY
            + " TEXT,"
            + COLUMN_ID_MOVIE
            + " INTERGER)";

    public static final String TABLE_CAST = "tbl_cast";
    public static final String COLUMN_CHARACTER = "character";
    public static final String COLUMN_PROFILE_PATH = "profile_path";
    public static final String COMMAND_DELETE_TABLE_CAST = "DROP TABLE IF EXISTS " + TABLE_CAST;
    public static final String COMMAND_CREATE_TABLE_CAST = "CREATE TABLE "
            + TABLE_CAST
            + "("
            +
            COLUMN_ID
            + " INTEGER PRIMARY KEY, "
            + COLUMN_CHARACTER
            + " TEXT,"
            + COLUMN_NAME
            + " TEXT,"
            + COLUMN_PROFILE_PATH
            + " TEXT)";

    public static final String TABLE_CAST_MOVIE = "tbl_cast_movie";
    public static final String COLUMN_ID_CAST = "id_country";
    public static final String COMMAND_DELETE_TABLE_CAST_MOVIE =
            "DROP TABLE IF EXISTS " + TABLE_CAST_MOVIE;
    public static final String COMMAND_CREATE_TABLE_CAST_MOVIE = "CREATE TABLE "
            + TABLE_CAST_MOVIE
            + "("
            + COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ID_CAST
            + " INTEGER,"
            + COLUMN_ID_MOVIE
            + " INTERGER)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(COMMAND_CREATE_TABLE_MOVIE);
        sqLiteDatabase.execSQL(COMMAND_CREATE_TABLE_GENRE);
        sqLiteDatabase.execSQL(COMMAND_CREATE_TABLE_GENRE_MOVIE);
        sqLiteDatabase.execSQL(COMMAND_CREATE_TABLE_CAST);
        sqLiteDatabase.execSQL(COMMAND_CREATE_TABLE_CAST_MOVIE);
        sqLiteDatabase.execSQL(COMMAND_CREATE_TABLE_COUNTRY);
        sqLiteDatabase.execSQL(COMMAND_CREATE_TABLE_COUNTRY_MOVIE);
        sqLiteDatabase.execSQL(COMMAND_CREATE_TABLE_COMPANY);
        sqLiteDatabase.execSQL(COMMAND_CREATE_TABLE_COMPANY_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(COMMAND_DELETE_TABLE_MOVIE);
        sqLiteDatabase.execSQL(COMMAND_DELETE_TABLE_GENRE);
        sqLiteDatabase.execSQL(COMMAND_DELETE_TABLE_GENRE_MOVIE);
        sqLiteDatabase.execSQL(COMMAND_DELETE_TABLE_CAST);
        sqLiteDatabase.execSQL(COMMAND_DELETE_TABLE_CAST_MOVIE);
        sqLiteDatabase.execSQL(COMMAND_DELETE_TABLE_COUNTRY);
        sqLiteDatabase.execSQL(COMMAND_DELETE_TABLE_COUNTRY_MOVIE);
        sqLiteDatabase.execSQL(COMMAND_DELETE_TABLE_COMPANY);
        sqLiteDatabase.execSQL(COMMAND_DELETE_TABLE_COMPANY_MOVIE);

        sqLiteDatabase.execSQL(COMMAND_CREATE_TABLE_MOVIE);
        sqLiteDatabase.execSQL(COMMAND_CREATE_TABLE_GENRE);
        sqLiteDatabase.execSQL(COMMAND_CREATE_TABLE_GENRE_MOVIE);
        sqLiteDatabase.execSQL(COMMAND_CREATE_TABLE_CAST);
        sqLiteDatabase.execSQL(COMMAND_CREATE_TABLE_CAST_MOVIE);
        sqLiteDatabase.execSQL(COMMAND_CREATE_TABLE_COUNTRY);
        sqLiteDatabase.execSQL(COMMAND_CREATE_TABLE_COUNTRY_MOVIE);
        sqLiteDatabase.execSQL(COMMAND_CREATE_TABLE_COMPANY);
        sqLiteDatabase.execSQL(COMMAND_CREATE_TABLE_COMPANY_MOVIE);
    }

    public boolean insertDataGenre(Genre genre) {
        long result = -1;
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ID, genre.getId());
            contentValues.put(COLUMN_NAME, genre.getName());
            result = sqLiteDatabase.insert(TABLE_GENRE, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result != -1;
    }

    public boolean insertDataMovie(Movie movie) {
        long result = -1;
        try {
            String originCountry = "";
            if (movie.getOriginCountry() != null && movie.getOriginCountry().length > 0) {
                for (String country : movie.getOriginCountry()) {
                    originCountry += country + "/";
                }
            }
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ID, movie.getId());
            contentValues.put(COLUMN_TITLE, movie.getTitle());
            contentValues.put(COLUMN_NAME, movie.getName());
            contentValues.put(COLUMN_COUNTRIES, originCountry);
            contentValues.put(COLUMN_POSTER_PATH, movie.getPosterPath());
            contentValues.put(COLUMN_RELEASE_DATE, movie.getReleaseDate());
            contentValues.put(COLUMN_FIRST_AIR_DATE, movie.getFirstAirDate());
            contentValues.put(COLUMN_VOTE_COUNT, movie.getVoteCount());
            contentValues.put(COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
            contentValues.put(COLUMN_OVERVIEW, movie.getOverview());
            result = sqLiteDatabase.insert(TABLE_MOVIE, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result != -1;
    }

    public boolean removeMovie(int id) {
        long result = -1;
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String[] whereArgs = new String[] { String.valueOf(id) };
            result = sqLiteDatabase.delete(TABLE_MOVIE, COLUMN_ID + "=?", whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result != -1;
    }

    public boolean removeGenreMovie(int idGenre, int idMovie) {
        long result = -1;
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String[] whereArgs = new String[] { String.valueOf(idGenre), String.valueOf(idMovie) };
            result = sqLiteDatabase.delete(TABLE_GENRE_MOVIE,
                    COLUMN_ID_GENRE + "=? AND " + COLUMN_ID_MOVIE + "=?", whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result != -1;
    }

    public boolean removeCastMovie(int idCast, int idMovie) {
        long result = -1;
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String[] whereArgs = new String[] { String.valueOf(idCast), String.valueOf(idMovie) };
            result = sqLiteDatabase.delete(TABLE_CAST_MOVIE,
                    COLUMN_ID_CAST + "=? AND " + COLUMN_ID_MOVIE + "=?", whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result != -1;
    }

    public boolean removeCountryMovie(String idCountry, int idMovie) {
        long result = -1;
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String[] whereArgs = new String[] { idCountry, String.valueOf(idMovie) };
            result = sqLiteDatabase.delete(TABLE_COUNTRY_MOVIE,
                    COLUMN_ID_COUNTRY + "=? AND " + COLUMN_ID_MOVIE + "=?", whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result != -1;
    }

    public boolean removeCompanyMovie(int idCompany, int idMovie) {
        long result = -1;
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String[] whereArgs =
                    new String[] { String.valueOf(idCompany), String.valueOf(idMovie) };
            result = sqLiteDatabase.delete(TABLE_COMPANY_MOVIE,
                    COLUMN_ID_COMPANY + "=? AND " + COLUMN_ID_MOVIE + "=?", whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result != -1;
    }

    public boolean insertDataGenreMovie(int idGenre, int idMovie) {
        long result = -1;
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ID_GENRE, idGenre);
            contentValues.put(COLUMN_ID_MOVIE, idMovie);
            result = sqLiteDatabase.insert(TABLE_GENRE_MOVIE, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result != -1;
    }

    public boolean insertDataCompany(ProductionCompany company) {
        long result = -1;
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ID, company.getId());
            contentValues.put(COLUMN_NAME, company.getName());
            result = sqLiteDatabase.insert(TABLE_COMPANY, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result != -1;
    }

    public boolean insertDataCompanyMovie(int idCompany, int idMovie) {
        long result = -1;
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ID_COMPANY, idCompany);
            contentValues.put(COLUMN_ID_MOVIE, idMovie);
            result = sqLiteDatabase.insert(TABLE_COMPANY_MOVIE, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result != -1;
    }

    public boolean insertDataCountry(ProductionCountry country) {
        long result = -1;
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ID, country.getIso());
            contentValues.put(COLUMN_NAME, country.getName());
            result = sqLiteDatabase.insert(TABLE_COUNTRY, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result != -1;
    }

    public boolean insertDataCountryMovie(String idCountry, int idMovie) {
        long result = -1;
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ID_COUNTRY, idCountry);
            contentValues.put(COLUMN_ID_MOVIE, idMovie);
            result = sqLiteDatabase.insert(TABLE_COUNTRY_MOVIE, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result != -1;
    }

    public boolean insertDataCast(Cast cast) {
        long result = -1;
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ID, cast.getId());
            contentValues.put(COLUMN_CHARACTER, cast.getCharacter());
            contentValues.put(COLUMN_NAME, cast.getName());
            contentValues.put(COLUMN_PROFILE_PATH, cast.getProfilePath());
            result = sqLiteDatabase.insert(TABLE_CAST, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result != -1;
    }

    public boolean insertDataCastMovie(int idCast, int idMovie) {
        long result = -1;
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ID_CAST, idCast);
            contentValues.put(COLUMN_ID_MOVIE, idMovie);
            result = sqLiteDatabase.insert(TABLE_CAST_MOVIE, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result != -1;
    }

    public Movie getMovie(int idMovie) {
        Movie movie = null;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String selection = COLUMN_ID + "=?";
            String[] selectionArgs = new String[] { String.valueOf(idMovie) };
            Cursor cursor =
                    sqLiteDatabase.query(TABLE_MOVIE, null, selection, selectionArgs, null, null,
                            null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String originCountries = cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRIES));
                String posterPath = cursor.getString(cursor.getColumnIndex(COLUMN_POSTER_PATH));
                String releaseDate = cursor.getString(cursor.getColumnIndex(COLUMN_RELEASE_DATE));
                String firstAirDate =
                        cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_AIR_DATE));
                int voteCount = cursor.getInt(cursor.getColumnIndex(COLUMN_VOTE_COUNT));
                float voteAverage = cursor.getFloat(cursor.getColumnIndex(COLUMN_VOTE_AVERAGE));
                String overview = cursor.getString(cursor.getColumnIndex(COLUMN_OVERVIEW));
                String[] ori = originCountries.split("/");
                movie = new Movie(id, title, name, ori, posterPath, releaseDate, firstAirDate,
                        voteCount, voteAverage, overview);
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return movie;
    }

    public Cast getCast(int idCast) {
        Cast cast = null;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String selection = COLUMN_ID + "=?";
            String[] selectionArgs = new String[] { String.valueOf(idCast) };
            Cursor cursor =
                    sqLiteDatabase.query(TABLE_CAST, null, selection, selectionArgs, null, null,
                            null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String character = cursor.getString(cursor.getColumnIndex(COLUMN_CHARACTER));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String profilePath = cursor.getString(cursor.getColumnIndex(COLUMN_PROFILE_PATH));

                cast = new Cast(id, character, name, profilePath);
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return cast;
    }

    public List<Movie> getAllMovie() {
        List<Movie> results = null;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            Cursor cursor = sqLiteDatabase.query(TABLE_MOVIE, new String[] {
                    COLUMN_ID, COLUMN_TITLE, COLUMN_NAME, COLUMN_COUNTRIES, COLUMN_POSTER_PATH,
                    COLUMN_RELEASE_DATE, COLUMN_FIRST_AIR_DATE, COLUMN_VOTE_COUNT,
                    COLUMN_VOTE_AVERAGE, COLUMN_OVERVIEW
            }, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                results = new ArrayList<>();
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                    String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                    String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                    String originCountry =
                            cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRIES));
                    String posterPath = cursor.getString(cursor.getColumnIndex(COLUMN_POSTER_PATH));
                    String releaseDate =
                            cursor.getString(cursor.getColumnIndex(COLUMN_RELEASE_DATE));
                    String firstAirDate =
                            cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_AIR_DATE));
                    int voteCount = cursor.getInt(cursor.getColumnIndex(COLUMN_VOTE_COUNT));
                    float voteAverage = cursor.getFloat(cursor.getColumnIndex(COLUMN_VOTE_AVERAGE));
                    String overview = cursor.getString(cursor.getColumnIndex(COLUMN_OVERVIEW));
                    String[] ori = originCountry.split("/");
                    results.add(
                            new Movie(id, title, name, ori, posterPath, releaseDate, firstAirDate,
                                    voteCount, voteAverage, overview));
                } while (cursor.moveToNext());
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return results;
    }

    public List<Cast> getAllCast() {
        List<Cast> results = null;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            Cursor cursor =
                    sqLiteDatabase.query(TABLE_CAST, null, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                results = new ArrayList<>();
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                    String character = cursor.getString(cursor.getColumnIndex(COLUMN_CHARACTER));
                    String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                    String profilePath =
                            cursor.getString(cursor.getColumnIndex(COLUMN_PROFILE_PATH));
                    results.add(new Cast(id, character, name, profilePath));
                } while (cursor.moveToNext());
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return results;
    }

    public List<Genre> getAllGenre() {
        List<Genre> results = null;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            Cursor cursor =
                    sqLiteDatabase.query(TABLE_GENRE, new String[] { COLUMN_ID, COLUMN_NAME }, null,
                            null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                results = new ArrayList<>();
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                    String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                    results.add(new Genre(id, name));
                } while (cursor.moveToNext());
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return results;
    }

    public String getNameCountry(String idCountry) {
        String result = null;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String selection = COLUMN_ID + "=?";
            String[] selectionArgs = new String[] { idCountry };
            Cursor cursor =
                    sqLiteDatabase.query(TABLE_COUNTRY, null, selection, selectionArgs, null, null,
                            null, null);
            if (cursor != null && cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }

    public String getNameGenre(int idGenre) {
        String result = null;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String selection = COLUMN_ID + "=?";
            String[] selectionArgs = new String[] { String.valueOf(idGenre) };
            Cursor cursor =
                    sqLiteDatabase.query(TABLE_GENRE, new String[] { COLUMN_ID, COLUMN_NAME },
                            selection, selectionArgs, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }

    public List<Integer> getIdGenreMovie(int idMovie) {
        List<Integer> results = new ArrayList<>();
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String selection = COLUMN_ID_MOVIE + "=?";
            String[] selectionArgs = new String[] { String.valueOf(idMovie) };
            Cursor cursor =
                    sqLiteDatabase.query(TABLE_GENRE_MOVIE, null, selection, selectionArgs, null,
                            null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_GENRE));
                    results.add(id);
                } while (cursor.moveToNext());

                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return results;
    }

    public List<Integer> getIdCompanyMovie(int idMovie) {
        List<Integer> results = new ArrayList<>();
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String selection = COLUMN_ID_MOVIE + "=?";
            String[] selectionArgs = new String[] { String.valueOf(idMovie) };
            Cursor cursor =
                    sqLiteDatabase.query(TABLE_COMPANY_MOVIE, null, selection, selectionArgs, null,
                            null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_COMPANY));
                    results.add(id);
                } while (cursor.moveToNext());

                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return results;
    }

    public List<String> getIdCountryMovie(int idMovie) {
        List<String> results = new ArrayList<>();
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String selection = COLUMN_ID_MOVIE + "=?";
            String[] selectionArgs = new String[] { String.valueOf(idMovie) };
            Cursor cursor =
                    sqLiteDatabase.query(TABLE_COUNTRY_MOVIE, null, selection, selectionArgs, null,
                            null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID_COUNTRY));
                    results.add(id);
                } while (cursor.moveToNext());

                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return results;
    }

    public List<Integer> getIdCastMovie(int idMovie) {
        List<Integer> results = new ArrayList<>();
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String selection = COLUMN_ID_MOVIE + "=?";
            String[] selectionArgs = new String[] { String.valueOf(idMovie) };
            Cursor cursor =
                    sqLiteDatabase.query(TABLE_CAST_MOVIE, null, selection, selectionArgs, null,
                            null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_CAST));
                    results.add(id);
                } while (cursor.moveToNext());

                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return results;
    }

    public boolean checkIdGenreMovie(int idMovie, int idGenre) {
        boolean results = false;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String selection = COLUMN_ID_MOVIE + "=? AND " + COLUMN_ID_GENRE + "=?";
            String[] selectionArgs =
                    new String[] { String.valueOf(idMovie), String.valueOf(idGenre) };
            Cursor cursor =
                    sqLiteDatabase.query(TABLE_GENRE_MOVIE, null, selection, selectionArgs, null,
                            null, null, null);
            if (cursor != null) {
                results = true;
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return results;
    }

    public String getNameCompany(int idCompany) {
        String result = null;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String selection = COLUMN_ID + "=?";
            String[] selectionArgs = new String[] { String.valueOf(idCompany) };
            Cursor cursor =
                    sqLiteDatabase.query(TABLE_COMPANY, null, selection, selectionArgs, null, null,
                            null, null);
            if (cursor != null && cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }
}
