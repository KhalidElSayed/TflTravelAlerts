
package com.tfltravelalerts.statusviewer.service;

import com.tfltravelalerts.common.persistence.SharedPreferencesStore;
import com.tfltravelalerts.model.LineStatusUpdateSet;

/**
 * Persists a {@link LineStatusUpdateSet}. TODO: use a db?
 */
public class LineStatusStore extends SharedPreferencesStore<LineStatusUpdateSet> {

    private static final String LINE_STATUS_UPDATE_SET_KEY = "LineStatusStore.LineStatusUpdateSet";

    public LineStatusStore() {
        super(LineStatusUpdateSet.class, LINE_STATUS_UPDATE_SET_KEY);
    }

}
