package ru.study.crush;

import java.util.List;

import ru.study.crush.model.User;
import ru.study.crush.model.UserModel;

public class Presenter {
    private MainFragment fragment;
    private final UserModel model;

    public Presenter(UserModel model) {
        this.model = model;
    }

    public void attachView(MainFragment fragment) {
        this.fragment = fragment;
    }

    public void add() {
        User user = new User();
        user.setName((String) fragment.getUIData().get("name"));
        fragment.showProgress();
        model.add(user, new UserModel.CompleteCallback() {
            @Override
            public void onComplete() {
                fragment.hideProgress();
                getAll();
            }
        });
    }

    public void getAll() {
        model.getAll(new UserModel.LoadUserCallback() {
            @Override
            public void onLoad(List<User> users) {
                fragment.showUsers(users);
            }
        });
    }

    public void deleteAll() {
        fragment.showProgress();
        model.deleteAll(new UserModel.CompleteCallback() {
            @Override
            public void onComplete() {
                fragment.hideProgress();
                getAll();
            }
        });
    }
}