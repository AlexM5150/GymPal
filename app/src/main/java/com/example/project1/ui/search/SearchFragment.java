package com.example.project1.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.project1.ClickedUserProfile;
import com.example.project1.UserProfile;
import com.example.project1.databinding.ActivitySearchBinding;

import com.example.project1.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.jetbrains.annotations.NotNull;


public class SearchFragment extends Fragment {

    private ActivitySearchBinding binding;
    private View root;
    private Query query;
    private EditText userSearchBar;
    private Button userSearchButton;
    private RecyclerView userList;

    private DatabaseReference mUserDatabase;
    private FirebaseRecyclerAdapter<UserProfile, UsersViewHolder> adapter;
    private OnItemClickListener listener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            UserProfile clickedUser = adapter.getItem(position);

            Intent intent = new Intent(getActivity(), ClickedUserProfile.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("clickedUser", clickedUser);
            Log.d("SearchFragment",  "Age " + clickedUser.getAge());
            intent.putExtras(bundle);
            startActivity(intent);

        }

    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = ActivitySearchBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        setHasOptionsMenu(true);

        mUserDatabase = FirebaseDatabase.getInstance().getReference();

        userList = root.findViewById(R.id.user_list);
        userList.setHasFixedSize(true);
        userList.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        userSearchBar = root.findViewById(R.id.user_search_bar);
        userSearchButton = root.findViewById(R.id.user_search_button);

        userSearchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String searchText = userSearchBar.getText().toString();
                firebaseUserSearch(searchText);
            }

        });

        return root;

    }

    private void firebaseUserSearch(String searchText) {
        query = mUserDatabase.orderByChild("displayName").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerOptions<UserProfile> options = new FirebaseRecyclerOptions.Builder<UserProfile>()
                .setQuery(query, UserProfile.class)
                .build();



        adapter = new FirebaseRecyclerAdapter<UserProfile, UsersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(UsersViewHolder holder, int position, UserProfile model) {
                holder.setDetails(getActivity().getApplicationContext(), model.getDisplayName(), model.getProfilePic(), model.getLocation());

            }
            @Override
            public UsersViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);

                UsersViewHolder viewHolder = new UsersViewHolder(view);
                viewHolder.listener = listener;


                return viewHolder;
            }
        };

        userList.setAdapter(adapter);
        adapter.startListening();
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        View mView;
        OnItemClickListener listener;

        public UsersViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            itemView.setOnClickListener(this);


        }

        public void onClick(View view) {
            if (listener != null) {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(view, position);
                }
            }
        }

        public void setDetails(Context ctx, String displayName, String profilePic, String userLocation){

            TextView user_displayName = (TextView) mView.findViewById(R.id.user_list_item_display_name);
            TextView user_location = (TextView) mView.findViewById(R.id.user_list_item_location);
            ImageView user_profilePic = (ImageView) mView.findViewById(R.id.user_list_item_profile_pic);

            user_displayName.setText(displayName);
            user_location.setText(userLocation);
            Glide.with(ctx).load(profilePic).into(user_profilePic);
        }


    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_about).setVisible(false);
    }

}