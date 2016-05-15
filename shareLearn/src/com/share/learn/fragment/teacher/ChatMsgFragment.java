package com.share.learn.fragment.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.share.learn.R;
import com.share.learn.activity.MainActivity;
import com.share.learn.activity.teacher.ChatMsgActivity;
import com.share.learn.adapter.ChatMsgViewAdapter;
import com.share.learn.adapter.TeacherAssetAdapter;
import com.share.learn.bean.ChatMsgEntity;
import com.share.learn.bean.LoginInfo;
import com.share.learn.bean.UserInfo;
import com.share.learn.bean.msg.Message;
import com.share.learn.fragment.BaseFragment;
import com.share.learn.help.RequestHelp;
import com.share.learn.help.RequsetListener;
import com.share.learn.parse.BaseParse;
import com.share.learn.parse.ChatMsgParse;
import com.share.learn.parse.LoginInfoParse;
import com.share.learn.utils.BaseApplication;
import com.share.learn.utils.SoundMeter;
import com.share.learn.utils.URLConstants;
import com.share.learn.utils.WaitLayer;
import com.share.learn.view.CustomListView;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @desc 老师信息-->老师评价
 * @creator caozhiqing
 * @data 2016/3/10
 */
public class ChatMsgFragment extends BaseFragment implements View.OnClickListener,RequsetListener{

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
    private Handler mHandler = new Handler();
    private String voiceName;
    private long startVoiceT, endVoiceT;

    private String teacherId = "";

    private ChatMsgEntity chaMsg ;
    private ChatMsgEntity sendChatMsg ;

    private int flag = 1;//1 列表 2 发送信息

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = mActivity.getIntent();
        if(intent != null && intent.hasExtra("teacherId") && intent.hasExtra("bundle")){
            teacherId =  intent.getStringExtra("teacherId");
            chaMsg = (ChatMsgEntity) intent.getSerializableExtra("bundle");
        }
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

        setLoadingDilog(WaitLayer.DialogType.NOT_NOMAL);
        flag = 1;
        requestTask();
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
        setTitleText(chaMsg.getTeacherName());

    }

    private void send() {
        String contString = mEditTextContent.getText().toString();
        if(!TextUtils.isEmpty(contString)){
            flag = 2;
            requestTask();
        }else {
            toasetUtil.showInfo("请输入信息");
        }
    }

    @Override
    protected void requestData(int requestType) {
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);
        RequestParam param = new RequestParam();
        Map postParams = null;

        if(flag == 1){
            postParams =  RequestHelp.getBaseParaMap("MessageChat") ;
            postParams.put("teacherId", teacherId);
            if (BaseApplication.getUserInfo() != null){
                postParams.put("studentImg", BaseApplication.getUserInfo().getHeadImg());
            }
            param.setmParserClassName(new ChatMsgParse());
        }else {
            postParams =  RequestHelp.getBaseParaMap("MessagePost") ;
            param.setmParserClassName(new BaseParse());
            sendChatMsg = new ChatMsgEntity();
            sendChatMsg.setReceiverId(chaMsg.getReceiverId());
            sendChatMsg.setTeacherImg(chaMsg.getTeacherImg());
            sendChatMsg.setTeacherName(chaMsg.getTeacherName());
            sendChatMsg.setDirection(chaMsg.getDirection());
            sendChatMsg.setContent(mEditTextContent.getText().toString());
            SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");//设置日期格式
            sendChatMsg.setCreateTime(df.format(new Date()));
            UserInfo userInfo = BaseApplication.getUserInfo();
            if(userInfo != null){
                sendChatMsg.setSenderId(userInfo.getId());
                sendChatMsg.setStudentImg(userInfo.getHeadImg());
                postParams.put("studentName",TextUtils.isEmpty(userInfo.getNickName())?"":userInfo.getNickName());
            }
            postParams.put("receiverId",sendChatMsg.getReceiverId());
            postParams.put("content",sendChatMsg.getContent());
            postParams.put("studentImg",TextUtils.isEmpty(sendChatMsg.getStudentImg())?"":sendChatMsg.getStudentImg());
            postParams.put("teacherImg",TextUtils.isEmpty(sendChatMsg.getTeacherImg())?"":sendChatMsg.getTeacherImg());
            postParams.put("teacherName",TextUtils.isEmpty(sendChatMsg.getTeacherName())?"":sendChatMsg.getTeacherName());
        }

        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(),createMyReqErrorListener(), param);

    }

    @Override
    public void handleRspSuccess(int requestType,Object obj) {
        if(flag == 1){
            JsonParserBase<ArrayList<ChatMsgEntity>> jsonParserBase = (JsonParserBase<ArrayList<ChatMsgEntity>>)obj;
            if ((jsonParserBase != null)){
                mDataArrays.clear();

                if(jsonParserBase.getData() != null){
                    mDataArrays.addAll(jsonParserBase.getData());
                }
                mAdapter.notifyDataSetChanged();
                mListView.setSelection(mAdapter.getCount()-1);
            }
        }else {
            mDataArrays.add(sendChatMsg);
            mAdapter.notifyDataSetChanged();
            mEditTextContent.setText("");
            sendChatMsg = null;
        }

    }


    public void initData() {
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