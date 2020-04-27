package com.example.fitflex;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast {

        public void Show_Toast(Context context, View view, String content, String type) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = null;

            if (type.equals("error")) {

                layout = inflater.inflate(R.layout.error_toast,
                        (ViewGroup) view.findViewById(R.id.toast_root));

            } else if (type.equals("succes")) {

                layout = inflater.inflate(R.layout.succes_toast,
                        (ViewGroup) view.findViewById(R.id.toast_root));

            }

            TextView text = (TextView) layout.findViewById(R.id.toast_error);
            text.setText(content);

            Toast toast = new Toast(context);
            toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 200);

            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);

            toast.show();
        }

}

