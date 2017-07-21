package com.gunghi.tgwing.lolock.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.gunghi.tgwing.lolock.Response.ResponseMate;
import com.gunghi.tgwing.lolock.model.Mate;
import com.gunghi.tgwing.lolock.network.LoLockService;
import com.gunghi.tgwing.lolock.network.LoLockServiceGenarator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by joyeongje on 2017. 7. 1..
 */

public class FragmentMate extends Fragment {

    private ArrayList<Mate> mates;
    private MateAdapter mateAdapter;
    private final String TAG = "FragmentMate";
    private TextView mateNumberTextView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_mate, container, false);
        mateNumberTextView = (TextView)rootView.findViewById(R.id.mateNumberTextView);

        RecyclerView mateRecyclerView = (RecyclerView) rootView.findViewById(R.id.fragmentFamiliyRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mateRecyclerView.setLayoutManager(mLayoutManager);
        mateRecyclerView.setHasFixedSize(true);
        mateRecyclerView.scrollToPosition(0);

        mates = new ArrayList<>();


        //mates.add(new Mate("http://cfile3.uf.tistory.com/image/246667375764E38E1D1A93","임정연","in","10분"));
        //mates.add(new Mate("http://cfile3.uf.tistory.com/image/246667375764E38E1D1A93","임정연","in","10분"));
        //mates.add(new Mate("http://cfile3.uf.tistory.com/image/246667375764E38E1D1A93","임정연","in","10분"));

        mateAdapter = new MateAdapter(mates,getContext());
        mateRecyclerView.setAdapter(mateAdapter);
        mateRecyclerView.setItemAnimator(new DefaultItemAnimator());
        getMateList();

        return rootView;
    }

    private void getMateList() {
        LoLockService loLockService = LoLockServiceGenarator.createService(LoLockService.class);
        String ltid = "00000174d02544fffef0103d";
        Call<ResponseMate> responseMate = loLockService.getHomeMateResponse(ltid);
        responseMate.enqueue(new Callback<ResponseMate>() {
            @Override
            public void onResponse(Call<ResponseMate> call, Response<ResponseMate> response) {
                Log.d(TAG,"Response" + response.toString());

                if(response.isSuccessful()) {
                    Log.d(TAG,"RoomMateList Response Success");
                    for (Mate mate : response.body().getMates()) {
                        mates.add(mate);
                    }
                    if(response.body().getMateNumber() != null) {
                        mateNumberTextView.setText("가족(" + response.body().getMateNumber() + ")" );
                    };
                    mateAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<ResponseMate> call, Throwable t) {
                Log.d(TAG,"URL or Server Error");
            }
        });

    }

    private class MateAdapter extends RecyclerView.Adapter<MateAdapter.ViewHolder>  {

        private ArrayList<Mate> mates;
        private Context context;

        public MateAdapter(ArrayList<Mate> mates, Context context) {
            this.mates = mates;
            this.context = context;

        }

        @Override
        public MateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_mate, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MateAdapter.ViewHolder holder, int position) {
            final Mate mateInfo = mates.get(position);
            //Picasso.with(context).load(mateInfo.getMateImageUrl()).into(holder.mateProfile);
            //Log.d("url",mateInfo.getMateImageUrl());
            holder.mateName.setText(mateInfo.getMateName());
            holder.mateDoorOpenTime.setText(mateInfo.getMateDoorOpenTime());
            holder.mateOutingFlag.setImageResource(R.drawable.ic_out_home);
            Picasso.with(holder.mateProfile.getContext()).load(mateInfo.getMateImageUrl()).into(holder.mateProfile);
            // TODO: 2017. 7. 1. 이미지 수정하는거 추가해야됨.!

        }

        @Override
        public int getItemCount() {
            return mates.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public CircleImageView mateProfile;
            public TextView    mateName;
            public TextView mateDoorOpenTime;
            public ImageView mateOutingFlag;

            public ViewHolder(View itemView) {
                super(itemView);
                mateProfile = (CircleImageView) itemView.findViewById(R.id.mateProfileImageView);
                mateName = (TextView) itemView.findViewById(R.id.mateNameTextView);
                mateDoorOpenTime = (TextView) itemView.findViewById(R.id.mateOutingTimeTextView);
                mateOutingFlag = (ImageView) itemView.findViewById(R.id.mateOutingFlag);
            }
        }
    }
}
