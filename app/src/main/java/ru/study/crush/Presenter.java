package ru.study.crush;

import ru.study.crush.model.User;

public class Presenter {
    private MainFragment fragment;
    private final User user;

    public Presenter(User user) {
        this.user = user;
    }
}
