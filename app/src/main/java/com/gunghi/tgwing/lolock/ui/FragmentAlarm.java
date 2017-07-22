package com.gunghi.tgwing.lolock.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gunghi.tgwing.lolock.R;
import com.gunghi.tgwing.lolock.model.InOutLog;

import java.util.ArrayList;

/**
 * Created by joyeongje on 2017. 7. 20..
 */

public class FragmentAlarm extends Fragment {

    private InOutLogAdapter inOutLogAdapter;
    private ArrayList<InOutLog> inOutLogs;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_alarm, container, false);
        RecyclerView mateRecyclerView = (RecyclerView) rootView.findViewById(R.id.fragmentAlarmRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mateRecyclerView.setLayoutManager(mLayoutManager);
        mateRecyclerView.setHasFixedSize(true);
        mateRecyclerView.scrollToPosition(0);

        inOutLogs = new ArrayList<>();
        inOutLogs.add(new InOutLog());
        inOutLogs.add(new InOutLog());
        inOutLogs.add(new InOutLog());

        //mates.add(new Mate("http://cfile3.uf.tistory.com/image/246667375764E38E1D1A93","임정연","in","10분"));
        //mates.add(new Mate("http://cfile3.uf.tistory.com/image/246667375764E38E1D1A93","임정연","in","10분"));
        //mates.add(new Mate("http://cfile3.uf.tistory.com/image/246667375764E38E1D1A93","임정연","in","10분"));

        inOutLogAdapter = new InOutLogAdapter(inOutLogs,getContext());
        mateRecyclerView.setAdapter(inOutLogAdapter);
        mateRecyclerView.setItemAnimator(new DefaultItemAnimator());
       // getMateList();
        return rootView;
    }

    private class InOutLogAdapter extends RecyclerView.Adapter<InOutLogAdapter.ViewHolder>  {

        private ArrayList<InOutLog> inoutLogs;
        private Context context;

        public InOutLogAdapter(ArrayList<InOutLog> inoutLogs, Context context) {
            this.inoutLogs = inoutLogs;
            this.context = context;

        }

        @Override
        public InOutLogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_alarm, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(InOutLogAdapter.ViewHolder holder, int position) {
            final InOutLog inoutLog = inoutLogs.get(position);

            // TODO: 2017. 7. 22. 타임 가져오긔
            holder.inOutNameTextView.setText("이종구님이 귀가하셨습니다.");
            holder.inOutTime.setText("12분전");
            holder.inOutDate.setText("05/12(수)");

            //Picasso.with(context).load(mateInfo.getMateImageUrl()).into(holder.mateProfile);//Log.d("url",mateInfo.getMateImageUrl());
           // holder.mateName.setText(mateInfo.getMateName());
           // holder.mateDoorOpenTime.setText(mateInfo.getMateDoorOpenTime());
           // holder.mateOutingFlag.setImageResource(R.drawable.ic_out_home);
           // Picasso.with(holder.mateProfile.getContext()).load(mateInfo.getMateImageUrl()).into(holder.mateProfile);

        }

        @Override
        public int getItemCount() {
            return inoutLogs.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
          //  public CircleImageView mateProfile;
          //  public TextView mateName;
          //  public TextView mateDoorOpenTime;
          //  public ImageView mateOutingFlag;
            public ConstraintLayout inOutContainer;
            public TextView         inOutNameTextView;
            public TextView         inOutTime;
            public TextView         inOutDate;

            public ViewHolder(View itemView) {
                super(itemView);
                inOutContainer = (ConstraintLayout) itemView.findViewById(R.id.cardAlarm);
                inOutNameTextView = (TextView) itemView.findViewById(R.id.inOutName);
                inOutTime = (TextView) itemView.findViewById(R.id.inOutTime);
                inOutDate = (TextView) itemView.findViewById(R.id.inOutDate);
             //   mateProfile = (CircleImageView) itemView.findViewById(R.id.mateProfileImageView);
             //   mateName = (TextView) itemView.findViewById(R.id.mateNameTextView);
             //   mateDoorOpenTime = (TextView) itemView.findViewById(R.id.mateOutingTimeTextView);
             //   mateOutingFlag = (ImageView) itemView.findViewById(R.id.mateOutingFlag);
            }
        }
    }









}
