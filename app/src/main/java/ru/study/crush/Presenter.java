package ru.study.crush;

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
        model.add(user, new UserModel.CompleteCallback() {
            @Override
            public void onComplete() {
                getAll();
            }
        });
    }

    public void getAll() {
        fragment.showUsers(model.getAll());
    }

    public void deleteAll() {
        model.deleteAll(new UserModel.CompleteCallback() {
            @Override
            public void onComplete() {
                getAll();
            }
        });
    }
}
