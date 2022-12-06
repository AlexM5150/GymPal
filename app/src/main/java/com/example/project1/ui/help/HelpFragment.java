package com.example.project1.ui.help;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project1.Navigation;
import com.example.project1.R;
import com.example.project1.UserInfo;
import com.example.project1.databinding.FragmentHelpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HelpFragment extends Fragment {

    private FragmentHelpBinding binding;
    String imgSelected = "hold";

    // Popup
    private Dialog diag1;
    private Dialog diag2;


    private Button popchangepic;
    private Button popchangepass;
    private Button editInfo;
    private TextView closepop;
    private Button changepass;
    private EditText currentpass;
    private EditText newpass;

    private ImageView pic1;
    private ImageView pic2;
    private ImageView pic3;
    private Button changepic;
    private TextView closepop2;

    private Context context1;
    private Context context2;
    private FirebaseUser user;
    Context context;
    UserProfileChangeRequest profileUpdates;

    private Uri test;

    private ArrayList<String> selected;

    private FirebaseAuth firebaseAuth;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        diag1 = new Dialog(context1);
        diag2 = new Dialog(context2);
        user = FirebaseAuth.getInstance().getCurrentUser();
        context = getActivity().getApplicationContext();


        firebaseAuth = FirebaseAuth.getInstance();
        selected = new ArrayList<String>();
        profileUpdates = new UserProfileChangeRequest.Builder().setPhotoUri(null).build();


    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHelpBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        editInfo = root.findViewById(R.id.editinfo_help);
        editInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(context, UserInfo.class);
                startActivity(intent);
            }
        });

        popchangepass = root.findViewById(R.id.changepass_help);
        popchangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowPopup(view);
            }
        });

        popchangepic = root.findViewById(R.id.changepic_help);
        popchangepic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){ ShowPopup2(view);}
        });

        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        context1 = context;
        context2 = context;
    }
    // Method for creating password change popup
    public void ShowPopup(View v){
        diag1.show();
        diag1.setContentView(R.layout.pass_change_pop);
        diag1.setCanceledOnTouchOutside(false);
        closepop = (TextView) diag1.findViewById(R.id.pass_close);
        changepass = (Button) diag1.findViewById(R.id.confirm_pass_change);
        newpass = (EditText) diag1.findViewById(R.id.edt_new_pass);
        closepop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                diag1.dismiss();
            }
        });
        changepass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String newpassenter = newpass.getText().toString();
                user.updatePassword(newpassenter).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(context, "Password Changed", Toast.LENGTH_SHORT).show();
                            Intent nav = new Intent(context, Navigation.class);
                            startActivity(nav);
                        }

                    }
                });
                diag1.dismiss();
            }
        });

    }
    // Method for creating profile picture change popup
    public void ShowPopup2(View v){
        diag2.show();
        diag2.setContentView(R.layout.profile_pic_change_pop);
        diag2.setCanceledOnTouchOutside(false);
        closepop2 = (TextView) diag2.findViewById(R.id.pic_close);
        pic1 = (ImageView) diag2.findViewById(R.id.first_pic);
        pic2 = (ImageView) diag2.findViewById(R.id.second_pic);
        pic3 = (ImageView) diag2.findViewById(R.id.third_pic);
        changepic = (Button) diag2.findViewById(R.id.confirm_pic_change);
        closepop2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){ diag2.dismiss(); }
        });
        pic1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){ imgSelected = "super";
                if(!selected.isEmpty()){
                    pic2.setImageResource(R.drawable.batman);
                    pic3.setImageResource(R.drawable.wonder);
                }
                pic1.setImageResource(R.drawable.supermanselected);
                selected.clear();
                selected.add("super");
            }
        });
        pic2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){ imgSelected = "bat";
                if(!selected.isEmpty()){
                    pic1.setImageResource(R.drawable.superman);
                    pic3.setImageResource(R.drawable.wonder);
                }
                pic2.setImageResource(R.drawable.batmanselected);
                selected.clear();
                selected.add("bat");
            }
        });
        pic3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){ imgSelected = "wonder";
                if(!selected.isEmpty()){
                    pic1.setImageResource(R.drawable.superman);
                    pic2.setImageResource(R.drawable.batman);
                }
                pic3.setImageResource(R.drawable.wonderselected);
                selected.clear();
                selected.add("wonder");
            }
        });
        changepic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FirebaseUser user = firebaseAuth.getCurrentUser();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                switch(imgSelected){
                    case "super":
                        test = Uri.parse("android.resource://com.example.project1/" + R.drawable.superman);
                        break;
                    case "bat":
                        test = Uri.parse("android.resource://com.example.project1/" + R.drawable.batman);
                        break;
                    case "wonder":
                        test = Uri.parse("android.resource://com.example.project1/" + R.drawable.wonder);
                        break;
                }
                databaseReference.child(user.getUid()).child("profilePic").setValue(test.toString());
                diag2.dismiss();
            }
        });
    }
}