package com.example.nagakrishna.farmville_new;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Naga Krishna on 27-04-2016.
 */
public class UsersChatAdapter extends RecyclerView.Adapter<UsersChatAdapter.ViewHolderUsers> {

    private List<UsersChatModel> mFireChatUsers;
    private Context mContext;
    private String chatId;
    private TextView mUserFirstName;

    public UsersChatAdapter(Context context, List<UsersChatModel> fireChatUsers) {
        this.mFireChatUsers = mFireChatUsers;
        this.mContext = context;
    }

    @Override
    public UsersChatAdapter.ViewHolderUsers onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderUsers(mContext, LayoutInflater.from(parent.getContext()).inflate(R.layout.user_profile, parent, false));
    }

    @Override
    public void onBindViewHolder(UsersChatAdapter.ViewHolderUsers holder, int position) {
        UsersChatModel fireChatUser=mFireChatUsers.get(position);
        holder.getUserFirstName().setText(fireChatUser.getChatName());
    }



    @Override
    public int getItemCount() {
        return mFireChatUsers.size();
    }

    public void refill(UsersChatModel users) {

        // Add each user and notify recyclerView about change
        mFireChatUsers.add(users);
        notifyDataSetChanged();
    }

//    public void setNameAndCreatedAt(String userName, String createdAt) {
//
//        // Set current user name and time account created at
//        mCurrentUserName=userName;
//        mCurrentUserCreatedAt=createdAt;
//    }

    public void changeUser(int index, UsersChatModel user) {

        // Handle change on each user and notify change
        mFireChatUsers.set(index,user);
        notifyDataSetChanged();
    }


    public class ViewHolderUsers extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Context mContextViewHolder;
        public ViewHolderUsers(Context context, View itemView) {
            super(itemView);
            mUserFirstName=(TextView)itemView.findViewById(R.id.userFirstNameProfile);
            mContextViewHolder=context;

            itemView.setOnClickListener(this);
        }

        public TextView getUserFirstName() {
            return mUserFirstName;
        }
        @Override
        public void onClick(View v) {

            int position=getLayoutPosition(); // Get row position

            UsersChatModel user=mFireChatUsers.get(position); // Get use object

            //Call the intent of ChatActivity to display chat

        }
    }
}
