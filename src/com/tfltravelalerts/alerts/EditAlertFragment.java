
package com.tfltravelalerts.alerts;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.EditText;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.tfltravelalerts.R;
import com.tfltravelalerts.alerts.events.AlertsUpdatedEvent;
import com.tfltravelalerts.alerts.events.ModifyAlertRequest;
import com.tfltravelalerts.common.eventbus.EventBusFragment;
import com.tfltravelalerts.model.LineStatusAlert;

public class EditAlertFragment extends EventBusFragment {

    public static final String ALERT_ID_KEY = "alertId";

    private int mAlertId;
    private LineStatusAlert mAlert;

    private View mRoot;
    private EditText mAlertTitle;
    private Button mCancelButton;
    private Button mSaveButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrieveArgs();
    }

    private void retrieveArgs() {
        Bundle bundle = getArguments();
        mAlertId = bundle.getInt(ALERT_ID_KEY, -1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflateRootView(inflater, container);
        findViews();
        setupButtons();

        return mRoot;
    }

    private void inflateRootView(LayoutInflater inflater, ViewGroup container) {
        mRoot = inflater.inflate(R.layout.edit_alert_fragment, container, false);
    }

    private void findViews() {
        mAlertTitle = (EditText) mRoot.findViewById(R.id.alert_title);
        mCancelButton = (Button) mRoot.findViewById(R.id.cancel_button);
        mSaveButton = (Button) mRoot.findViewById(R.id.save_button);
    }

    private void setupButtons() {
        mCancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity();
            }
        });

        mSaveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAlert();
                finishActivity();
            }
        });
    }

    private void updateAlert() {
        LineStatusAlert alert = LineStatusAlert.builder(mAlert)
                .title(mAlertTitle.getText().toString())
                .build();

        ModifyAlertRequest request = new ModifyAlertRequest(alert);
        getEventBus().post(request);
    }

    private void finishActivity() {
        getActivity().finish();
    }

    private void updateTitle() {
        mAlertTitle.setText(mAlert.getTitle());
    }

    public void onEventMainThread(AlertsUpdatedEvent event) {
        mAlert = event.getData().getAlertById(mAlertId);

        updateTitle();
    }
}
