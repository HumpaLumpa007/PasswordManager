package com.password.manager.gui.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.password.manager.R;
import com.password.manager.core.Logger;
import com.password.manager.core.User;
import com.password.manager.handler.PathHandler;

import java.io.File;

/**
 * Created by Clemens on 12.12.2014.
 */
public class CreateUserHelper {
    public static void createUser(final Context context) {
        View view = View.inflate(context, R.layout.create_user_layout, null);

        final EditText userEditText = (EditText) view.findViewById(R.id.create_user_user_name_edit_text);
        final EditText newPasswordEditText = (EditText) view.findViewById(R.id.create_user_new_password_edit_text);
        final EditText repeatPasswordEditText = (EditText) view.findViewById(R.id.create_user_repeat_password_edit_text);

        // TODO: on click on text_view make password visible


        final AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(R.string.helper_create_user)
                .setView(view)
                .setCancelable(true)
                .setNegativeButton(R.string.helper_cancel, null)
                .setPositiveButton("OK", null)
                .create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // TODO: extract strings to strings.xml
                        String pathToUser = PathHandler.PathToUsers + File.separator + userEditText.getText().toString() + ".xml";
                        String pathToKey = PathHandler.PathToKeys + File.separator + userEditText.getText().toString() + ".xml";
                        String newPassword = newPasswordEditText.getText().toString();
                        String repeatPassword = repeatPasswordEditText.getText().toString();
                        if (PathHandler.fileExists(pathToUser)) {
                            Logger.show("User already exists! Choose another name", context);
                        } else if (!newPassword.equals(repeatPassword)) {
                            Logger.show("The passwords don't match", context);
                        } else {
                            try {
                                User user = new User(userEditText.getText().toString(), newPassword, pathToKey);
                                user.save();
                                PathHandler.writeFile(pathToKey, "");

                                alertDialog.dismiss();
                            } catch (Exception e) {
                                Logger.show(e.getMessage(), context);
                            }
                        }
                    }
                });
            }
        });
        alertDialog.show();
    }
}
