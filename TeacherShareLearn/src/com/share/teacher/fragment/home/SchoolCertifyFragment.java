package com.share.teacher.fragment.home;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.*;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.share.teacher.R;
import com.share.teacher.activity.ChooseCityActivity;
import com.share.teacher.activity.teacher.ChooseJoinorActivity;
import com.share.teacher.activity.teacher.SchoolCertifyActivity;
import com.share.teacher.bean.DataMapConstants;
import com.share.teacher.fragment.BaseFragment;
import com.share.teacher.utils.*;
import com.share.teacher.view.UpdateAvatarPopupWindow;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * @desc 学历认证
 * @creator caozhiqing
 * @data 2016/3/10
 */
public class SchoolCertifyFragment extends BaseFragment implements View.OnClickListener {


    @Bind(R.id.degree_name)
    TextView degreeName;
    @Bind(R.id.degreeRl)
    RelativeLayout degreeRl;
    @Bind(R.id.school_name)
    EditText schoolName;
    @Bind(R.id.schoolRl)
    RelativeLayout schoolRl;
    @Bind(R.id.major_name)
    EditText majorName;
    @Bind(R.id.majorRl)
    RelativeLayout majorRl;
    @Bind(R.id.uploadLL)
    LinearLayout uploadLL;
    @Bind(R.id.schoolCertifyImg)
    ImageView schoolCertifyImg;

    private String requstValue = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_schoo_certify, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitle();
        initView(view);
    }

    private void initView(View view) {

        uploadLL.setOnClickListener(this);
        schoolCertifyImg.setOnClickListener(this);
        schoolCertifyImg.setVisibility(View.GONE);

        degreeRl.setOnClickListener(this);
        schoolRl.setOnClickListener(this);
        majorRl.setOnClickListener(this);
    }


    private void initTitle() {
        setTitleText(R.string.school_certify);
        setLeftHeadIcon(0);
        setHeaderRightText(R.string.sure, new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (TextUtils.isEmpty(requstValue)) {
                    toasetUtil.showInfo("请选择学历");
                    return;
                }
                if (TextUtils.isEmpty(schoolName.getText())) {
                    toasetUtil.showInfo("请输入院校");
                    return;
                }
                if (TextUtils.isEmpty(majorName.getText())) {
                    toasetUtil.showInfo("请输入专业");
                    return;
                }

                if (m_obj_IconBp == null) {
                    toasetUtil.showInfo("请选择相关证件");
                    return;
                }

                setLoadingDilog(WaitLayer.DialogType.MODALESS);
                showLoadingDilog("正在上传...");
                if (NetUtils.isConnected(getActivity())) {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
//                    showUIDialogState(true);
                            uploadFileM();
                        }
                    }, "upPic1").start();
                } else {
                    toasetUtil.showInfo("网络连接出错,无法上传头像");
                }

                Intent intent = new Intent();
                mActivity.setResult(Activity.RESULT_OK, intent);
                mActivity.finish();
            }
        });
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.degreeRl:
                intent = new Intent(mActivity, ChooseJoinorActivity.class);
                intent.putExtra("joniorId",requstValue);
                intent.setFlags(12);
                startActivityForResult(intent, URLConstants.CHOOSE_DEGREE_REQUEST_CODE);
                break;
            case R.id.choose_jonior:
                intent = new Intent(mActivity, ChooseJoinorActivity.class);
                startActivityForResult(intent, URLConstants.CHOOSE_JOINOR_REQUEST_CODE);
                break;
            case R.id.uploadLL:
                m_obj_menuWindow = new UpdateAvatarPopupWindow(getActivity(), v, itemsOnClick);
                m_obj_menuWindow.showAtLocation(uploadLL, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.schoolCertifyImg:
                uploadLL.performClick();
                break;

            default:
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAMEIA:// 来自相机，去裁剪
                    if (data == null) {
                        if (SDCardUtils.checkSDCardStatus()) {
                            File tempFile = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
                            startPhotoZoom(Uri.fromFile(tempFile));
                        } else {
                            Toast.makeText(getActivity(), "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG).show();
                        }
                    } else if (data.getExtras() != null) {
                        if (SDCardUtils.checkSDCardStatus()) {
                            File tempFile = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
                            startPhotoZoom(Uri.fromFile(tempFile));
                        } else {
                            Toast.makeText(getActivity(), "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
                case REQUEST_PHOTO:// 来自相册，去裁剪
                    if (data != null) {
                        Uri mImageUri = data.getData();
                        String path = getPath(getActivity(), mImageUri);
                        mImageUri = Uri.fromFile(new File(path));
                        try {
                            startPhotoZoom(mImageUri);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case RESULT_REQUEST:// 保存修改的头像并上传服务器
                    if (data != null) {
                        getImageToView(data);
                    }
                    break;
                case URLConstants.CHOOSE_DEGREE_REQUEST_CODE://选择学历
                    requstValue = data.getStringExtra(URLConstants.CHOOSE);
                    degreeName.setText(DataMapConstants.getDegree().get(requstValue));
                    break;
//                case Constant.MODIFY_INFO:// 修改个人信息
//                    requestData();
//                    break;


            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**********************************************
     * 修改头像start
     *****************************************************/
//    private RoundImageView mHeadImg;
    // popupwidos
    private UpdateAvatarPopupWindow m_obj_menuWindow = null;
    // 头像BitMap
    private Bitmap m_obj_IconBp = null;
    private String userId = "";
    private String postUrl = "";
    private String m_str_uploadMsg = "";
    // 上传图片的状态
    private static final int UPLOAD_START = 1;// 开始上传
    private static final int UPLOAD_OK = 2;// 结束上传(成功)
    private static final int UPLOAD_ERROR = 3;// 结束上传(失败)
    private static final int REQUEST_PHOTO = 4;// 相册选择头像
    private static final int REQUEST_CAMEIA = 5;// 相机选择头像
    private static final int RESULT_REQUEST = 6;// 裁剪的结果
    /* 头像名称 */
    private static final String IMAGE_FILE_NAME = "avatar.jpg";

    // 选择相册还是拍照
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            m_obj_menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_camera:
                    goCamera();
                    break;
                case R.id.btn_photo:
                    Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
                    getImage.setType("image/*");
                    startActivityForResult(getImage, REQUEST_PHOTO);
                    break;
            }

        }

    };

    private void goCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
            startActivityForResult(takeIntent, REQUEST_CAMEIA);
        } else {
            Toast.makeText(getActivity(), "无法拍照", Toast.LENGTH_LONG).show();
        }
    }

    private void uploadFileM() {

        AppLog.Loge("开始上传头像------------");
        String fore_name = UUID.randomUUID().toString();
        String fileName = fore_name + ".jpg"; // 报文中的文件名参数
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        m_obj_IconBp = Utils.compressImage(m_obj_IconBp);// 压缩到100kb
        byte[] picByte = BitMap2Byte(m_obj_IconBp);
        try {
            postUrl = URLConstants.TEACHER_UPLOAD;
            String param = new String();
            param = "cmd=UploadEdu" + "&appVersion=" + BaseApplication.getInstance().appVersion + "&clientType=3" +
                    "&accessToken=" + BaseApplication.getInstance().accessToken + "&deviceId=000000" + "&spaceCode=1003"
               +"&education="+requstValue+"&profession="+majorName.getText().toString()+"&college="+schoolName.getText().toString();

            postUrl = postUrl + "?" + param;
            URL url = new URL(postUrl);
            AppLog.Logi(SchoolCertifyFragment.class + "", "url = " + postUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            /*
             * Output to the connection. Default is false, set to true because
			 * post method must write something to the connection
			 */
            con.setDoOutput(true);
			/* Read from the connection. Default is true. */
            con.setDoInput(true);
			/* Post cannot use caches */
            con.setUseCaches(false);
			/* Set the post method. Default is GET */
            con.setRequestMethod("POST");
			/* 设置请求属性 */
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

			/* 设置StrictMode 否则HTTPURLConnection连接失败，因为这是在主进程中进行网络连接 */
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
			/* 设置DataOutputStream，getOutputStream中默认调用connect() */
            DataOutputStream ds = new DataOutputStream(con.getOutputStream());
            ds.writeBytes(twoHyphens + boundary + end);
            //avatar 需要与后台约定的字段.
            ds.writeBytes("Content-Disposition: form-data; " + "name=\"avatar\";filename=\"" + fileName + "\"" + end);
            ds.writeBytes(end);
            AppLog.Logi(SchoolCertifyActivity.class + "", "图片字节:" + picByte.toString());
            ds.write(picByte, 0, picByte.length);
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

            ds.writeBytes(param);
            ds.close();
			/* 从返回的输入流读取响应信息 */
            InputStream is = con.getInputStream(); // input from the connection
            // 正式建立HTTP连接
            int ch;
            final StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
			/* 显示网页响应内容 */
            // Toast.makeText(getActivity(), b.toString().trim(),
            // Toast.LENGTH_SHORT).show();// Post成功
            AppLog.Logi(SchoolCertifyFragment.class + "", "响应内容:" + b.toString().trim());
            // 解析响应的数据
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    parseDataFromServer(b.toString().trim());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
			/* 显示异常信息 */
            Message msg = handler.obtainMessage();
            msg.what = SHOW_ERROR;
            msg.obj = getResources().getString(R.string.upload_fail);
            handler.removeMessages(msg.what);
            handler.sendMessage(msg);
        }
    }

    // 解析服务器响应的数据
    private void parseDataFromServer(String data) {
        try {

            dismissLoadingDilog();
            if (data != null) {
                JsonParserBase<Object> result = ParserUtil.fromJsonBase(data, new TypeToken<JsonParserBase<Object>>() {
                }.getType());
                if (result != null && URLConstants.SUCCESS_CODE.equals(result.getRespCode())) {
                    LinkedTreeMap<String,String> linkedTreeMap = (LinkedTreeMap<String,String>)result.getData();
                    schoolCertifyImg.setImageBitmap(m_obj_IconBp);// 上传成功设置头像
                    schoolCertifyImg.setVisibility(View.VISIBLE);
                    ImageLoader.getInstance().displayImage(linkedTreeMap.get("url").toString(),schoolCertifyImg, ImageLoaderUtil.mHallIconLoaderOptions);
                    toasetUtil.showSuccess(R.string.upload_success);
                } else {
                    toasetUtil.showInfo(result.getRespDesc());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    // 将BitMap转换成字节流
    private byte[] BitMap2Byte(Bitmap bitmap) {
        if (null == bitmap) {
            return null;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        AppLog.Logi(SchoolCertifyFragment.class + "", "byte = " + byteArray);
        AppLog.Logi(SchoolCertifyFragment.class + "", "byteString = " + byteArray.toString());
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
    }

    protected Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case UPLOAD_START:
                    uploadFileM();
                    break;
                case UPLOAD_OK:
                    schoolCertifyImg.setImageBitmap(m_obj_IconBp);// 上传成功设置头像
                    toasetUtil.showSuccess(R.string.upload_success);
                    break;
                case UPLOAD_ERROR:
                    Toast.makeText(getActivity(), m_str_uploadMsg, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

    };

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", true);
        // aspectX aspectY 是宽高的比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param data
     */
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras == null) {
            return;
        }
        Bitmap bitmap = extras.getParcelable("data");
        Drawable drawable = new BitmapDrawable(bitmap);
        schoolCertifyImg.setImageDrawable(drawable);
        schoolCertifyImg.setVisibility(View.VISIBLE);

        /********** 上传图片 ***************/
        m_obj_IconBp = bitmap;// 用于上传服务器
        /*setLoadingDilog(WaitLayer.DialogType.MODALESS);
        showLoadingDilog("正在上传...");
        if (NetUtils.isConnected(getActivity())) {
            new Thread(new Runnable() {

                @Override
                public void run() {
//                    showUIDialogState(true);
                    uploadFileM();
                }
            }, "upPic1").start();
        } else {
            toasetUtil.showInfo("网络连接出错,无法上传头像");
        }*/

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= 19;

        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
    /******************************************** 修改头像end *****************************************************/

}