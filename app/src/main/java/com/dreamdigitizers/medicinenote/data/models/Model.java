package com.dreamdigitizers.medicinenote.data.models;

import java.io.Serializable;

public class Model implements Serializable {
    private long mRowId;

    public long getRowId() {
        return this.mRowId;
    }

    public void setRowId(long pRowId) {
        this.mRowId = pRowId;
    }
}
