package com.app.barcodeattendance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class View_attendance extends Fragment {

    public Func func;

    public String response;
    public TextView fname,level,course_code,dept,start_date,end_date;

    public Button scan;


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                // if the intentResult is not null we'll set
                // the content and format of scan message
                Toast.makeText(getActivity(), intentResult.getContents(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), intentResult.getFormatName(), Toast.LENGTH_SHORT).show();;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.view_attendance, container, false);

        func = new Func(getActivity());

        String attendance_id = getArguments().getString("view_id");
        getActivity().setTitle("Mark Attendance");

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("attendance", Context.MODE_PRIVATE);
        response = sharedPreferences.getString("attendance",null);

        fname = root.findViewById(R.id.staff_name);
        course_code = root.findViewById(R.id.course_code);
        dept = root.findViewById(R.id.dept);
        start_date = root.findViewById(R.id.start_date);
        level = root.findViewById(R.id.level);
        end_date = root.findViewById(R.id.end_date);

        scan = root.findViewById(R.id.scan);


        String id;

        try {

            JSONObject object = new JSONObject(response);
            JSONArray data = object.getJSONArray("attendance");

            for (int i =0; i < data.length(); i++){
                JSONObject attendance_data = data.getJSONObject(i);

                id = attendance_data.getString("id");

                if (attendance_id.equals(id)){

                    fname.setText(attendance_data.getString("fname"));
                    course_code.setText(attendance_data.getString("title")+" ("+attendance_data.getString("code")+") ");
                    dept.setText(attendance_data.getString("name"));
                    start_date.setText(attendance_data.getString("start_time"));
                    end_date.setText(attendance_data.getString("end_time"));
                    level.setText(attendance_data.getString("level"));

                    break;
                }

            }

        }catch (JSONException e){
            e.printStackTrace();
        }

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
                intentIntegrator.setPrompt("Scan a barcode or QR Code");
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.initiateScan();

            }
        });

    }
}


