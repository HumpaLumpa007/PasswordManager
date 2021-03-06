package com.password.manager;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.password.manager.core.Logger;
import com.password.manager.core.PasswordListAdapter;
import com.password.manager.core.query.Query;
import com.password.manager.core.query.QueryCommand;

public class BottomSearchFragment extends Fragment {

    private EditText searchText;
    private ImageButton closeButton;
    private PasswordListAdapter passwordListAdapter;

    public BottomSearchFragment() {}

    public void setPasswordListAdapter(PasswordListAdapter passwordListAdapter) {
        this.passwordListAdapter = passwordListAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_search_fragment, container, false);
        closeButton = (ImageButton) view.findViewById(R.id.bottom_search_close_button);
        searchText = (EditText) view.findViewById(R.id.bottom_search_search_text_edit_text);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.password_list_relative_layout, new BottomButtonFragment())
                        .commit();
            }
        });

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (s.length() > 0) {
                        passwordListAdapter
                                .order(Query.build(
                                        QueryCommand.CONTAINS.toString(),
                                        s.toString()
                                ));
                    } else {
                        passwordListAdapter.reset();
                    }
                } catch (Exception e) {
//                    Logger.show(e.getMessage(), getActivity());
                }
            }
        });

        return view;
    }


}
