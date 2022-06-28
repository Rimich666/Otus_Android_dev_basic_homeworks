package com.moviesearch.datasource.database;

import java.lang.System;

@androidx.room.Entity(tableName = "pages", indices = {@androidx.room.Index(unique = true, value = {"num"})})
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001R\u001e\u0010\u0005\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0015"}, d2 = {"Lcom/moviesearch/datasource/database/Page;", "", "num", "", "(I)V", "id", "", "getId", "()J", "setId", "(J)V", "getNum", "()I", "component1", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
public final class Page {
    private final int num = 0;
    @androidx.room.PrimaryKey(autoGenerate = true)
    private long id = 0L;
    
    @org.jetbrains.annotations.NotNull()
    public final com.moviesearch.datasource.database.Page copy(int num) {
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
    
    public Page(int num) {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    public final int getNum() {
        return 0;
    }
    
    public final long getId() {
        return 0L;
    }
    
    public final void setId(long p0) {
    }
}