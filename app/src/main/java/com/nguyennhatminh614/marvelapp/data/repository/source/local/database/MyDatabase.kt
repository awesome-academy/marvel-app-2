package com.nguyennhatminh614.marvelapp.data.repository.source.local.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class MyDatabase(
    context: Context?,
    name: String? = DATABASE_NAME,
    factory: SQLiteDatabase.CursorFactory? = null,
    version: Int = VERSION
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.apply {
            execSQL(CREATE_CHARACTER_TABLE)
            execSQL(CREATE_COMIC_TABLE)
            execSQL(CREATE_STORIES_TABLE)
            execSQL(CREATE_SERIES_TABLE)
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.apply {
            execSQL(UPGRADE_TABLE + CHARACTER_TABLE)
            execSQL(UPGRADE_TABLE + COMIC_TABLE)
            execSQL(UPGRADE_TABLE + STORIES_TABLE)
            execSQL(UPGRADE_TABLE + SERIES_TABLE)
        }
        onCreate(p0)
    }

    companion object {
        private const val CHARACTER_TABLE = "CHARACTER"
        private const val COMIC_TABLE = "COMIC"
        private const val SERIES_TABLE = "SERIES"
        private const val STORIES_TABLE = "STORIES"
        private const val DATABASE_NAME = "MarvelAppDB"
        private const val VERSION = 3
        private const val ID = "id"
        private const val NAME = "name"
        private const val TITLE = "title"
        private const val DESCRIPTION = "description"
        private const val THUMBNAIL_LINK = "thumbnailLink"
        private const val FAVORITE = "favorite"
        private const val CREATE_CHARACTER_TABLE =
            "create table $CHARACTER_TABLE ($ID integer primary key, " +
                    "$NAME text, " +
                    "$DESCRIPTION text, " +
                    "$THUMBNAIL_LINK text, " +
                    "$FAVORITE integer)"
        private const val CREATE_COMIC_TABLE = "create table ${COMIC_TABLE} ($ID integer primary key, " +
                "${TITLE} text, " +
                "${DESCRIPTION} text, " +
                "${THUMBNAIL_LINK} text, " +
                "${FAVORITE} integer)"
        private const val CREATE_SERIES_TABLE = "create table $SERIES_TABLE ($ID integer primary key, " +
                "$TITLE text, " +
                "$DESCRIPTION text, " +
                "$THUMBNAIL_LINK text, " +
                "$FAVORITE integer)"
        private const val CREATE_STORIES_TABLE = "create table $STORIES_TABLE ($ID integer primary key, " +
                "$TITLE text, " +
                "$DESCRIPTION text, " +
                "$THUMBNAIL_LINK text, " +
                "$FAVORITE integer)"
        private const val UPGRADE_TABLE = "drop table if exists "

        private var instance: MyDatabase? = null

        fun getInstance(context: Context?) =
            if (instance != null) instance else MyDatabase(context).also { instance = it }
    }
}
