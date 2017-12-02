package com.gunghi.tgwing.lolock.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gunghi.tgwing.lolock.R;
import com.gunghi.tgwing.lolock.Response.ResponseInOutLog;
import com.gunghi.tgwing.lolock.model.InOutLog;
import com.gunghi.tgwing.lolock.model.UserInfo;
import com.gunghi.tgwing.lolock.network.LoLockService;
import com.gunghi.tgwing.lolock.network.LoLockServiceGenarator;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by joyeongje on 2017. 7. 20..
 */

public class FragmentAlarm extends Fragment {

    private InOutLogAdapter inOutLogAdapter;
    private ArrayList<InOutLog> inOutLogs;

    Date date;

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
        //mates.add(new Mate("http://cfile3.uf.tistory.com/image/246667375764E38E1D1A93","임정연","in","10분"));
        //mates.add(new Mate("http://cfile3.uf.tistory.com/image/246667375764E38E1D1A93","임정연","in","10분"));
        //mates.add(new Mate("http://cfile3.uf.tistory.com/image/246667375764E38E1D1A93","임정연","in","10분"));

        inOutLogAdapter = new InOutLogAdapter(inOutLogs,getContext());
        mateRecyclerView.setAdapter(inOutLogAdapter);
        mateRecyclerView.setItemAnimator(new DefaultItemAnimator());
        date = new Date();
        getInOutLogList();
       // getMateList();
        return rootView;
    }

    private void getInOutLogList() {
        LoLockService loLockService = LoLockServiceGenarator.createService(LoLockService.class);
        Call<ResponseInOutLog> callLolockService = loLockService.getInOutLog(UserInfo.getInstance().getDevideId());
        callLolockService.enqueue(new Callback<ResponseInOutLog>() {
            @Override
            public void onResponse(Call<ResponseInOutLog> call, Response<ResponseInOutLog> response) {
                Log.d("Success","Suc");
                alramDataMappingUi(response.body());
            }

            @Override
            public void onFailure(Call<ResponseInOutLog> call, Throwable t) {
                Log.d("Fail","Fail");
            }
        });
    }

    private void alramDataMappingUi(ResponseInOutLog response) {

        for(ResponseInOutLog.Result result : response.getResults()) {
            String name = result.getName();
            int outFlag = result.getOutFlag();
            ResponseInOutLog.OutTime outTime = result.getOutTime();
            int strangFlag = result.getStrangeFlag();
            inOutLogs.add(new InOutLog(name,outFlag,outTime,strangFlag));
        }
        inOutLogAdapter.notifyDataSetChanged();
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

            String nameText = "";

            if(inoutLog.getStrangeFlag() == 1 ) {
                holder.inOutContainer.setBackgroundColor(Color.parseColor("#FF9797"));
                holder.inOutContainer.setAlpha(0.85f);
                holder.inoutImageView.setImageResource(R.drawable.ic_alarm_known);
                nameText = "외부인의 침입이 의심됩니다!";

            } else {
                holder.inoutImageView.setImageResource(R.drawable.ic_alarm_unknown);
                nameText = inoutLog.getName() + "님이 ";
                // 1일때 나간거
                if(inoutLog.getOutingFlag() == 0) {
                    nameText += "귀가하였습니다.";
                } else {
                    nameText += "외출하셨습니다.";
                }
            }

            holder.inOutNameTextView.setText(nameText);
            long diff = inoutLog.getInOutDate().getTimeStamp();

            Log.d("timeStamp",String.valueOf(diff));
            long diffMinutes = diff / (60 * 1000);
          //  long diffHours = diff / (60 * 60 * 1000);

            String duringTime = diffMinutes/60 + "시간 " +
                    diffMinutes%60 + "분전";
            holder.inOutTime.setText(duringTime);
            holder.inOutDate.setText(inoutLog.getInOutDate().getMonth() + "/" +
                    inoutLog.getInOutDate().getDay() + "(" + inoutLog.getInOutDate().getDayName() + ")");

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
            public ImageView        inoutImageView;

            public ViewHolder(View itemView) {
                super(itemView);
                inOutContainer = (ConstraintLayout) itemView.findViewById(R.id.cardAlarm);
                inOutNameTextView = (TextView) itemView.findViewById(R.id.inOutName);
                inOutTime = (TextView) itemView.findViewById(R.id.inOutTime);
                inOutDate = (TextView) itemView.findViewById(R.id.inOutDate);
                inoutImageView = (ImageView) itemView.findViewById(R.id.icAlarmImageView);

             //   mateProfile = (CircleImageView) itemView.findViewById(R.id.mateProfileImageView);
             //   mateName = (TextView) itemView.findViewById(R.id.mateNameTextView);
             //   mateDoorOpenTime = (TextView) itemView.findViewById(R.id.mateOutingTimeTextView);
             //   mateOutingFlag = (ImageView) itemView.findViewById(R.id.mateOutingFlag);
            }
        }
    }









}
