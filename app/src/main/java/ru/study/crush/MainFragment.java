package ru.study.crush;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.study.crush.model.User;
import ru.study.crush.model.UserModel;

public class MainFragment extends Fragment {
    private static final String TAG = MainFragment.class.getSimpleName();
    private Presenter presenter;

    private EditText editTextName;
    private Button buttonAdd;
    private Button buttonClear;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private ProgressDialog progressDialog;

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
        buttonClear = getActivity().findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.deleteAll();
            }
        });
        recyclerView = getActivity().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        userAdapter = new UserAdapter();
        recyclerView.setAdapter(userAdapter);
        presenter.getAll();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private class UserHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public UserHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }

        void bind(User user) {
            textView.setText("id: " + user.getId() + ", name: " + user.getName());
        }
    }

    private class UserAdapter extends RecyclerView.Adapter<UserHolder> {
        List<User> users = new ArrayList<>();

        @Override
        public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new UserHolder(view);
        }

        @Override
        public void onBindViewHolder(UserHolder holder, int position) {
            holder.bind(users.get(position));
        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        public void setUsers(List<User> users) {
            this.users.clear();
            this.users.addAll(users);
            notifyDataSetChanged();
        }
    }

    public Map getUIData() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", editTextName.getText().toString());
        return map;
    }

    public void showUsers(List<User> users) {
        userAdapter.setUsers(users);
    }

    public void showProgress() {
        progressDialog = ProgressDialog.show(getActivity(), "", "Пожалуйста, подождите");
    }

    public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}