package com.yiyue.user.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yiyue.user.R;

/**
 * Dialog工具类
 */
public class DialogUtil {

    private static boolean isFist = true;// 第一次点击

    public static void showRewardDialog(Context context, String msg, final ClickListenerInterface listener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.theme_dialog);
        builder.setCancelable(false);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_welfare, null);
        final TextView tv_receive = view.findViewById(R.id.tv_receive);
        TextView tv_welfare = view.findViewById(R.id.tv_welfare);
        tv_welfare.setText(msg);
        ImageView iv_close = view.findViewById(R.id.iv_close);
        final AlertDialog dialog = builder.create();

        tv_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFist) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    if (listener != null) {
                        listener.doConfirm();
                    }
                } else {
                    // 第一次
                    isFist = false;
                    tv_receive.setText("立即使用");
                }
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
        dialog.setContentView(view);
    }

    public interface ClickListenerInterface {

        void doConfirm();
    }
}
