package com.share.learn.fragment.teacher;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.share.learn.R;
import com.share.learn.activity.teacher.ChatMsgActivity;
import com.share.learn.adapter.ChatMsgViewAdapter;
import com.share.learn.adapter.TeacherAssetAdapter;
import com.share.learn.bean.ChatMsgEntity;
import com.share.learn.bean.msg.Message;
import com.share.learn.fragment.BaseFragment;
import com.share.learn.utils.SoundMeter;
import com.share.learn.view.CustomListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @desc 老师信息-->老师评价
 * @creator caozhiqing
 * @data 2016/3/10
 */
public class ChatMsgFragment extends BaseFragment implements View.OnClickListener{

    private TextView mBtnRcd;
    private EditText mEditTextContent;
    private RelativeLayout mBottom;
    private ListView mListView;
    private ChatMsgViewAdapter mAdapter;
    private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();
    private boolean isShosrt = false;
    private LinearLayout voice_rcd_hint_loading, voice_rcd_hint_rcding,
            voice_rcd_hint_tooshort;
    private ImageView img1, sc_img1;
    private SoundMeter mSensor;
    private View rcChat_popup;
    private LinearLayout del_re;
    private ImageView chatting_mode_btn, volume;
    private boolean btn_vocie = false;
    private int flag = 1;
    private Handler mHandler = new Handler();
    private String voiceName;
    private long startVoiceT, endVoiceT;


    @Override
    protected void requestData() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initTitle();
        initData();

    }


    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.listview);
		/* Called when the activity is first created. */
        Button mBtnSend = (Button) view.findViewById(R.id.btn_send);
        mBtnRcd = (TextView) view.findViewById(R.id.btn_rcd);
        mBtnSend.setOnClickListener(this);
        mBottom = (RelativeLayout) view.findViewById(R.id.btn_bottom);
        chatting_mode_btn = (ImageView) view.findViewById(R.id.ivPopUp);
        volume = (ImageView) view.findViewById(R.id.volume);
        rcChat_popup = view.findViewById(R.id.rcChat_popup);
        img1 = (ImageView)view.findViewById(R.id.img1);
        sc_img1 = (ImageView) view.findViewById(R.id.sc_img1);
        del_re = (LinearLayout) view.findViewById(R.id.del_re);
        voice_rcd_hint_rcding = (LinearLayout) view
                .findViewById(R.id.voice_rcd_hint_rcding);
        voice_rcd_hint_loading = (LinearLayout) view
                .findViewById(R.id.voice_rcd_hint_loading);
        voice_rcd_hint_tooshort = (LinearLayout) view
                .findViewById(R.id.voice_rcd_hint_tooshort);
        mSensor = new SoundMeter();
        mEditTextContent = (EditText) view.findViewById(R.id.et_sendmessage);

        //语音文字切换按钮
        chatting_mode_btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if (btn_vocie) {
                    mBtnRcd.setVisibility(View.GONE);
                    mBottom.setVisibility(View.VISIBLE);
                    btn_vocie = false;
                    chatting_mode_btn
                            .setImageResource(R.drawable.chatting_setmode_msg_btn);

                } else {
                    mBtnRcd.setVisibility(View.VISIBLE);
                    mBottom.setVisibility(View.GONE);
                    chatting_mode_btn
                            .setImageResource(R.drawable.chatting_setmode_voice_btn);
                    btn_vocie = true;
                }
            }
        });
        mBtnRcd.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                //按下语音录制按钮时返回false执行父类OnTouch
                return false;
            }
        });
    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_send:
                send();
                break;
        }
    }

    private void initTitle(){

        setLeftHeadIcon(0);
        setTitleText("消息");

    }

    private void send() {
        String contString = mEditTextContent.getText().toString();
        if (contString.length() > 0) {
            ChatMsgEntity entity = new ChatMsgEntity();
            entity.setDate(getDate());
            entity.setName("高富帅");
            entity.setMsgType(false);
            entity.setText(contString);

            mDataArrays.add(entity);
            mAdapter.notifyDataSetChanged();

            mEditTextContent.setText("");

            mListView.setSelection(mListView.getCount() - 1);
        }
    }

    private String getDate() {
        Calendar c = Calendar.getInstance();

        String year = String.valueOf(c.get(Calendar.YEAR));
        String month = String.valueOf(c.get(Calendar.MONTH));
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String mins = String.valueOf(c.get(Calendar.MINUTE));

        StringBuffer sbBuffer = new StringBuffer();
        sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":"
                + mins);

        return sbBuffer.toString();
    }

    private String[] msgArray = new String[] { "有人就有恩怨","有恩怨就有江湖","人就是江湖","你怎么退出？ ","生命中充满了巧合","两条平行线也会有相交的一天。","有恩怨就有江湖","人就是江湖","你怎么退出？ ","生命中充满了巧合","两条平行线也会有相交的一天。","有恩怨就有江湖","人就是江湖","你怎么退出？ ","生命中充满了巧合","两条平行线也会有相交的一天。","有恩怨就有江湖","人就是江湖","你怎么退出？ ","生命中充满了巧合","两条平行线也会有相交的一天。","有恩怨就有江湖","人就是江湖","你怎么退出？ ","生命中充满了巧合","两条平行线也会有相交的一天。","有恩怨就有江湖","人就是江湖","你怎么退出？ ","生命中充满了巧合","两条平行线也会有相交的一天。","有恩怨就有江湖","人就是江湖","你怎么退出？ ","生命中充满了巧合","两条平行线也会有相交的一天。","有恩怨就有江湖","人就是江湖","你怎么退出？ ","生命中充满了巧合","两条平行线也会有相交的一天。"};

    private String[] dataArray = new String[] { "2012-10-31 18:00",
            "2012-10-31 18:10", "2012-10-31 18:11", "2012-10-31 18:20",
            "2012-10-31 18:30", "2012-10-31 18:35"};
    private final static int COUNT = 6;

    public void initData() {
        for (int i = 0; i < COUNT; i++) {
            ChatMsgEntity entity = new ChatMsgEntity();
            entity.setDate(dataArray[i]);
            if (i % 2 == 0) {
                entity.setName("白富美");
                entity.setMsgType(true);
            } else {
                entity.setName("高富帅");
                entity.setMsgType(false);
            }

            entity.setText(msgArray[i]);
            mDataArrays.add(entity);
        }

        mAdapter = new ChatMsgViewAdapter(mActivity, mDataArrays);
        mListView.setAdapter(mAdapter);

    }



    private static final int POLL_INTERVAL = 300;

    private Runnable mSleepTask = new Runnable() {
        public void run() {
            stop();
        }
    };

    private Runnable mPollTask = new Runnable() {
        public void run() {
            double amp = mSensor.getAmplitude();
            updateDisplay(amp);
            mHandler.postDelayed(mPollTask, POLL_INTERVAL);

        }
    };

    private void start(String name) {
        mSensor.start(name);
        mHandler.postDelayed(mPollTask, POLL_INTERVAL);
    }

    private void stop() {
        mHandler.removeCallbacks(mSleepTask);
        mHandler.removeCallbacks(mPollTask);
        mSensor.stop();
        volume.setImageResource(R.drawable.amp1);
    }

    private void updateDisplay(double signalEMA) {

        switch ((int) signalEMA) {
            case 0:
            case 1:
                volume.setImageResource(R.drawable.amp1);
                break;
            case 2:
            case 3:
                volume.setImageResource(R.drawable.amp2);

                break;
            case 4:
            case 5:
                volume.setImageResource(R.drawable.amp3);
                break;
            case 6:
            case 7:
                volume.setImageResource(R.drawable.amp4);
                break;
            case 8:
            case 9:
                volume.setImageResource(R.drawable.amp5);
                break;
            case 10:
            case 11:
                volume.setImageResource(R.drawable.amp6);
                break;
            default:
                volume.setImageResource(R.drawable.amp7);
                break;
        }
    }


}