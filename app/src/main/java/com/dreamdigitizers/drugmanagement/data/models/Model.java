package com.dreamdigitizers.drugmanagement.data.models;

import java.io.Serializable;

public class Model implements Serializable {
    private long _id;

    public long get_id() {
        return this._id;
    }

    public void setId(long pId) {
        this._id = pId;
    }
}
