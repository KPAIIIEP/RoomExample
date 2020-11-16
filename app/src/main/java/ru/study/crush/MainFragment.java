package ru.study.crush;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import ru.study.crush.model.User;
import ru.study.crush.model.UserDao;
import ru.study.crush.model.UserDatabase;

public class MainFragment extends Fragment {
    private Presenter presenter;

    public static Fragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (presenter == null) {
            presenter = new Presenter(new User());
        }
        UserDatabase db = UserDatabase.getInstance(getContext());
        UserDao userDao = db.userDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
