package com.example.project1.ui.contact;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class GroupChat extends Fragment {

    private LinearLayout mChatLayout;
    private EditText mMessageEditText;
    private Button mSendButton;

    private DatabaseReference mMessagesRef;
    private ChildEventListener mMessagesListener;
    private Map<String, View> mMessageViews = new HashMap<>();

    private String mUserId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_room, container, false);
        mChatLayout = view.findViewById(R.id.chat_layout);
        mMessageEditText = view.findViewById(R.id.message_edit_text);
        mSendButton = view.findViewById(R.id.send_button);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        mMessagesRef = FirebaseDatabase.getInstance().getReference().child("messages");
        mMessagesListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String message = snapshot.child("message").getValue(String.class);
                String sender = snapshot.child("sender").getValue(String.class);
                String key = snapshot.getKey();
                displayMessage(key, sender, message);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String message = snapshot.child("message").getValue(String.class);
                String sender = snapshot.child("sender").getValue(String.class);
                String key = snapshot.getKey();
                displayMessage(key, sender, message);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String key = snapshot.getKey();
                removeMessage(key);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        mMessagesRef.addChildEventListener(mMessagesListener);

        mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        return view;
    }

    private void sendMessage() {
        // Sends message from the EditText, sets what the message is as well as the sender based on the Id
        String message = mMessageEditText.getText().toString().trim();
        if (!message.isEmpty()) {
            String key = mMessagesRef.push().getKey();
            Map<String, Object> messageData = new HashMap<>();
            messageData.put("message", message);
            messageData.put("sender", mUserId);
            mMessagesRef.child(key).setValue(messageData);
            mMessageEditText.setText("");
        }
    }

    private void displayMessage(String key, String sender, String message) {
        if (!mMessageViews.containsKey(key)) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View messageView = inflater.inflate(R.layout.item_message, mChatLayout, false);
            TextView messageTextView = messageView.findViewById(R.id.message_text_view);
            messageTextView.setText(message);
            TextView senderTextView = messageView.findViewById(R.id.sender_text_view);
            // Checks to see who the message is being sent from, if it's sent from yourself it puts it as
            // Me, else it will try to find the displayName.  If none will put ID instead.
            if (sender.equals(mUserId)) {
                senderTextView.setText("Me");
            } else {
                // Gets reference to database in regards to the sender
                DatabaseReference senderRef = FirebaseDatabase.getInstance().getReference().child("users").child(sender);
                senderRef.addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // DisplayName is set as the display Message name, if not it will send ID
                        String displayName = snapshot.child("displayName").getValue(String.class);
                        if (displayName != null) {
                            senderTextView.setText(displayName);
                        } else {
                            senderTextView.setText(sender);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        senderTextView.setText(sender);
                    }
                });
            }
            mMessageViews.put(key, messageView);
            mChatLayout.addView(messageView);
        } else {
            View messageView = mMessageViews.get(key);
            TextView messageTextView = messageView.findViewById(R.id.message_text_view);
            messageTextView.setText(message);
        }
    }




    private void removeMessage(String key) {
        if (mMessageViews.containsKey(key)) {
            View messageView = mMessageViews.get(key);
            mChatLayout.removeView(messageView);
            mMessageViews.remove(key);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMessagesRef.removeEventListener(mMessagesListener);
    }
}