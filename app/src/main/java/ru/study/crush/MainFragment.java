package ru.study.crush;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import ru.study.crush.model.User;
import ru.study.crush.model.UserModel;

public class MainFragment extends Fragment {
    private Presenter presenter;

    private EditText editTextName;
    private Button buttonAdd;

    public static Fragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (presenter == null) {
            presenter = new Presenter(new UserModel(getContext()));
        }
        presenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        editTextName = getActivity().findViewById(R.id.editTextName);
        buttonAdd = getActivity().findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.add();
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public Map getUIData() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", editTextName.getText().toString());
        return map;
    }
}
