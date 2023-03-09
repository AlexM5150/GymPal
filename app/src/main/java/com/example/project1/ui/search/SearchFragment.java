package com.example.project1.ui.search;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.project1.UserProfile;
import com.example.project1.databinding.ActivitySearchBinding;

import com.example.project1.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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


        FirebaseRecyclerAdapter<UserProfile, UsersViewHolder> adapter = new FirebaseRecyclerAdapter<UserProfile, UsersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(UsersViewHolder holder, int position, UserProfile model) {
                holder.setDetails(getActivity().getApplicationContext(), model.getDisplayName(), model.getProfilePic(), model.getLocation());
            }

            @Override
            public UsersViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);

                return new UsersViewHolder(view);
            }
        };

        userList.setAdapter(adapter);
        adapter.startListening();
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu, inflater);
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_about).setVisible(false);
    }

}