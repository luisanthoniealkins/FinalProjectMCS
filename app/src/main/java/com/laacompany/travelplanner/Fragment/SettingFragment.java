package com.laacompany.travelplanner.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.laacompany.travelplanner.Handle.Handle;
import com.laacompany.travelplanner.LoginActivity;
import com.laacompany.travelplanner.R;

public class SettingFragment extends Fragment implements View.OnClickListener{

    private TextView mTVEmail, mTVPhone;
    private Button mBTNLogout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_setting, container,false);

        mTVEmail = v.findViewById(R.id.id_fragment_setting_tv_email);
        mTVPhone = v.findViewById(R.id.id_fragment_setting_tv_phone);



        mBTNLogout = v.findViewById(R.id.id_fragment_setting_btn_logout);
        mBTNLogout.setOnClickListener(this);

        return v;
    }

    @Override
    public void onResume() {
        mTVEmail.setText(Handle.sCurrentUser.getEmail());
        mTVPhone.setText(Handle.sCurrentUser.getPhone());
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.id_fragment_setting_btn_logout){
            Handle.mAuth.signOut();
            startActivity(LoginActivity.newIntent(getActivity()));
        }
    }
}
