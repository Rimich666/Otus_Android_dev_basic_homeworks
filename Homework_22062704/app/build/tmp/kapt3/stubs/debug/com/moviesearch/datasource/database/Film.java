package com.moviesearch.datasource.database;

import java.lang.System;

@androidx.room.Entity(tableName = "list_films", indices = {@androidx.room.Index(unique = true, value = {"id_kp"})}, foreignKeys = {@androidx.room.ForeignKey(entity = com.moviesearch.datasource.database.Page.class, childColumns = {"page_id"}, onDelete = 5, parentColumns = {"id"})})
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B!\b\u0016\u0012\u0010\u0010\u0002\u001a\f\u0012\u0004\u0012\u00020\u0004\u0012\u0002\b\u00030\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007B5\u0012\u0006\u0010\b\u001a\u00020\u0004\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\u0004\u0012\u0006\u0010\f\u001a\u00020\u0004\u0012\u0006\u0010\r\u001a\u00020\u0004\u0012\u0006\u0010\u000e\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\u001d\u001a\u00020\u0004H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\nH\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0004H\u00c6\u0003J\t\u0010 \u001a\u00020\u0004H\u00c6\u0003J\t\u0010!\u001a\u00020\u0004H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0006H\u00c6\u0003JE\u0010#\u001a\u00020\u00002\b\b\u0002\u0010\b\u001a\u00020\u00042\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\u00042\b\b\u0002\u0010\f\u001a\u00020\u00042\b\b\u0002\u0010\r\u001a\u00020\u00042\b\b\u0002\u0010\u000e\u001a\u00020\u0006H\u00c6\u0001J\u0013\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\'\u001a\u00020\nH\u00d6\u0001J\t\u0010(\u001a\u00020\u0004H\u00d6\u0001R\u0016\u0010\r\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001e\u0010\u0012\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0016\u0010\t\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\b\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0011R\u0016\u0010\u000e\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0014R\u0016\u0010\f\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0011R\u0016\u0010\u000b\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0011\u00a8\u0006)"}, d2 = {"Lcom/moviesearch/datasource/database/Film;", "", "map", "", "", "page_id", "", "(Ljava/util/Map;J)V", "name", "idKp", "", "shortDescription", "previewUrl", "alternativeName", "page", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;J)V", "getAlternativeName", "()Ljava/lang/String;", "id", "getId", "()J", "setId", "(J)V", "getIdKp", "()I", "getName", "getPage", "getPreviewUrl", "getShortDescription", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class Film {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = null;
    @androidx.room.ColumnInfo(name = "id_kp")
    private final int idKp = 0;
    @org.jetbrains.annotations.NotNull()
    @androidx.room.ColumnInfo(name = "short_description")
    private final java.lang.String shortDescription = null;
    @org.jetbrains.annotations.NotNull()
    @androidx.room.ColumnInfo(name = "preview_url")
    private final java.lang.String previewUrl = null;
    @org.jetbrains.annotations.NotNull()
    @androidx.room.ColumnInfo(name = "alternativeName")
    private final java.lang.String alternativeName = null;
    @androidx.room.ColumnInfo(name = "page_id")
    private final long page = 0L;
    @androidx.room.PrimaryKey(autoGenerate = true)
    private long id = 0L;
    
    @org.jetbrains.annotations.NotNull()
    public final com.moviesearch.datasource.database.Film copy(@org.jetbrains.annotations.NotNull()
    java.lang.String name, int idKp, @org.jetbrains.annotations.NotNull()
    java.lang.String shortDescription, @org.jetbrains.annotations.NotNull()
    java.lang.String previewUrl, @org.jetbrains.annotations.NotNull()
    java.lang.String alternativeName, long page) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public Film(@org.jetbrains.annotations.NotNull()
    java.lang.String name, int idKp, @org.jetbrains.annotations.NotNull()
    java.lang.String shortDescription, @org.jetbrains.annotations.NotNull()
    java.lang.String previewUrl, @org.jetbrains.annotations.NotNull()
    java.lang.String alternativeName, long page) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int getIdKp() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getShortDescription() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPreviewUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAlternativeName() {
        return null;
    }
    
    public final long component6() {
        return 0L;
    }
    
    public final long getPage() {
        return 0L;
    }
    
    public final long getId() {
        return 0L;
    }
    
    public final void setId(long p0) {
    }
    
    public Film(@org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, ?> map, long page_id) {
        super();
    }
}