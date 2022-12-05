package com.example.gympal.food;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gympal.HttpHandler;
import com.example.gympal.Navigation;
import com.example.gympal.R;
import com.example.gympal.UserProfile;
import com.example.gympal.databinding.FoodFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoodFragment extends Fragment {
    private Toolbar toolbar;
    private String user;
    private SearchView sv;
    private FoodFragmentBinding binding;

    private View root;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase userData;

    private static final String appKey = "7f277e1bb0cb1d3b0b756e3cf375365c";
    private static final String appId = "7da7c17c";
    private String baseURL = "https://api.nutritionix.com/v1_1/search/";
    private String baseURL2 = "?fields=item_name%2Citem_id%2Cbrand_name%2Cnf_calories%2Cnf_total_fat&appId=" +
            appId + "&appKey=" + appKey;
    private String query = "";

    ArrayList<String> foodItems;
    ArrayList<String> brandNames;
    ArrayList<String> nf_calories;

    TextView foodNametxt;
    TextView brandNametxt;
    TextView caloriestxt;

    public static String foodName;
    public static String brandName;
    public static String calories;

    private ProgressDialog pDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FoodFragmentBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        foodNametxt = root.findViewById(R.id.recent_item);
        brandNametxt = root.findViewById(R.id.recent_brand);
        caloriestxt = root.findViewById(R.id.recent_calories);

        foodItems = new ArrayList<>();
        brandNames = new ArrayList<>();
        nf_calories = new ArrayList<>();
        setHasOptionsMenu(true);
        pDialog = new ProgressDialog(getActivity().getApplicationContext());
        UserProfile info = new UserProfile();

        firebaseAuth = FirebaseAuth.getInstance();
        userData = FirebaseDatabase.getInstance();


        new GetFood().execute();
        return root;

    }

    private class GetFood extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = ProgressDialog.show(getActivity(), "Searching", "Finding your calories");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();

            String jsonStr = sh.makeServiceCall(baseURL + query + baseURL2);

            if(jsonStr != null)
                try{
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONArray hits = jsonObject.getJSONArray("hits");
                    foodItems.clear();
                    brandNames.clear();
                    nf_calories.clear();
                    for(int i = 0; i < hits.length(); i++){
                        JSONObject d = hits.getJSONObject(i);
                        JSONObject fields = d.getJSONObject("fields");
                        String itemName = fields.getString("item_name");
                        String brandName = fields.getString("brand_name");
                        String calories = fields.getString("nf_calories");

                        foodItems.add(itemName);
                        brandNames.add(brandName);
                        nf_calories.add(calories);



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(pDialog.isShowing()) {
                pDialog.dismiss();
            }

            RecyclerView rv = root.findViewById(R.id.food_list);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
            rv.setLayoutManager(llm);
            rv.setAdapter(new SimpleItemRecyclerViewAdapter(foodItems, brandNames, nf_calories));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search, menu);
        menu.findItem(R.id.action_settings).setVisible(false);
        setHasOptionsMenu(true);

        sv = (SearchView) menu.findItem(R.id.action_search).getActionView();

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String s) {
                String userSearchParse = s.replace(" ", "%20");
                query = userSearchParse;
                new GetFood().execute();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    //Recyclerview adapter class
    public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter <SimpleItemRecyclerViewAdapter.ViewHolder> {
        private ArrayList<String> foodItems;
        private ArrayList<String> brandNames;
        private ArrayList<String> nf_calories;

        SimpleItemRecyclerViewAdapter(ArrayList<String> foodItems, ArrayList<String> brandNames, ArrayList<String> nf_calories) {
            this.foodItems = foodItems;
            this.brandNames = brandNames;
            this.nf_calories = nf_calories;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout,
                    parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.foodName.setText(foodItems.get(position));
            holder.brandView.setText("Brand: " + brandNames.get(position));
            holder.calorieView.setText("Calories: " + nf_calories.get(position));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent navigation = new Intent(getActivity().getApplicationContext(), Navigation.class);
                    firebaseAuth = FirebaseAuth.getInstance();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference.child(user.getUid()).child("currentFoodName").setValue(foodItems.get(position));
                    databaseReference.child(user.getUid()).child("currentBrandName").setValue(brandNames.get(position));
                    databaseReference.child(user.getUid()).child("currentFoodCalories").setValue(nf_calories.get(position));
                    foodName = foodItems.get(position);
                    brandName = brandNames.get(position);
                    calories = nf_calories.get(position);
                    navigation.putExtra("displayName", user.getDisplayName());
                    startActivity(navigation);
//                    Intent intent = new Intent(getActivity().getApplicationContext(), HomeFragment.class);
//                    intent.putExtra("FROM_ACTIVITY", "TEST");
//                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return foodItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            View mView;
            TextView foodName;
            TextView brandView;
            TextView calorieView;

            public ViewHolder(View itemView) {
                super(itemView);
                mView = itemView;
                foodName = itemView.findViewById(R.id.item_name);
                brandView = itemView.findViewById(R.id.brand_name);
                calorieView = itemView.findViewById(R.id.nf_calories);

            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}