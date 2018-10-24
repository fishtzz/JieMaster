package com.szmaster.jiemaster;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.bumptech.glide.Glide;
import com.szmaster.jiemaster.bus.IUser;
import com.szmaster.jiemaster.bus.UserBus;
import com.szmaster.jiemaster.db.PreferenceImp;
import com.szmaster.jiemaster.model.IModel;
import com.szmaster.jiemaster.model.ReviseUserImgModel;
import com.szmaster.jiemaster.model.ReviseUsernameModel;
import com.szmaster.jiemaster.model.User;
import com.szmaster.jiemaster.network.base.ApiManager;
import com.szmaster.jiemaster.utils.CommonUtil;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by jiangsiyu on 2018/10/19.
 */

public class UserSettingActivity extends AppCompatActivity implements View.OnClickListener, IUser, Observer<IModel> {

    private ImageView avatar;
    private View setAvatar;
    private TextView tvNickname;
    private View setNickname;
    private TextView tvMobile;
    private User user;
    private File outputImage;
    private static final String IMG_CACHE = "jiemaster/avatar.jpg";

    public static final int TAKE_PHOTO = 1;

    public static final int CHOOSE_PHOTO = 2;

    private Uri imageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        UserBus.getInstance().registerIUser(this);
        user = UserBus.getInstance().getUser();
        if (null == user) {
            finish();
        } else {
            initView();
        }
    }

    private void initView() {
        avatar = findViewById(R.id.avatar);
        Glide.with(this).load(user.getUserImg()).into(avatar);
        setAvatar = findViewById(R.id.set_avatar);
        setAvatar.setOnClickListener(this);
        tvNickname = findViewById(R.id.tv_nickname);
        tvNickname.setText(user.getUserName());
        setNickname = findViewById(R.id.set_nickname);
        setNickname.setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        tvMobile = findViewById(R.id.tv_mobile);
        tvMobile.setText(user.getMobile());
        findViewById(R.id.btn_logout).setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        UserBus.getInstance().unregisterIUser(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.set_avatar:
                showDialogReviseUserImg();
                break;
            case R.id.set_nickname:
                showDialogReviseUsername();
                break;

            case R.id.btn_logout:
                UserBus.getInstance().logout();
                startActivity(new Intent(this, RegisterOrLoginActivity.class));
                finish();
                break;
            default:
                break;
        }

    }

    private void showDialogReviseUsername() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        final EditText editText = new EditText(this);
        editText.setHint(user.getUserName());
        editText.setHintTextColor(getResources().getColor(R.color.text_grey));
        editText.setTextColor(getResources().getColor(R.color.text_black));
        builder.setTitle(R.string.revise_username)
                .setView(editText)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (reviseUsername(editText.getText().toString())) {
                            dialog.dismiss();
                        } else {
                            return;
                        }
                    }
                })
                .show();
    }

    private void showDialogReviseUserImg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        builder.setTitle(R.string.revise_user_img)
                .setNegativeButton(R.string.camera, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ContextCompat.checkSelfPermission(UserSettingActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(UserSettingActivity.this, new String[]{Manifest.permission.CAMERA}, 2);
                        } else {
                            launchCamera();
                        }

                    }
                })
                .setPositiveButton(R.string.gallery, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ContextCompat.checkSelfPermission(UserSettingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(UserSettingActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        } else {
                            openAlbum();
                        }
                    }
                })
                .show();
    }

    private void launchCamera() {

        //创建File对象，用于存储拍照后的照片
        outputImage = new File(getExternalCacheDir(), IMG_CACHE);

//        Log.d(UserSettingActivity.class, "getExternalCacheDir().getPath() -> " + getExternalCacheDir().getPath());
//        Log.d(UserSettingActivity.class, "getCacheDir().getPath() -> " + getCacheDir().getPath());
//        Log.d(UserSettingActivity.class, "getFilesDir().getPath() -> " + getFilesDir().getPath());
//        Log.d(UserSettingActivity.class, "getExternalFilesDir().getPath() -> " + getExternalFilesDir("/1234").getPath());

        try {
            if (!outputImage.getParentFile().exists()) {
                outputImage.getParentFile().mkdir();
            }
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(UserSettingActivity.this, "com.szmaster.jiemaster.fileprovider", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        } //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    private void reviseUserImg(File img) {
        int time = (int) (System.currentTimeMillis() / 1000);
        HashMap<String, String> map = new HashMap<>();
        map.put(Constants.KEY_TIME, time + "");
        map.put(Constants.KEY_IMEI, PreferenceImp.getIMEICache());
        map.put(Constants.KEY_MAC, PreferenceImp.getMacCache());
        map.put(Constants.KEY_SERIALNUMBER, Build.SERIAL);
        map.put(Constants.KEY_VERSION, CommonUtil.getVersionName());
        map.put(Constants.KEY_CHANNEL, CommonUtil.getChannel());
        map.put("userId", user.getUserId());
        map.put("token", user.getToken());

        RequestBody body1 = RequestBody.create(MediaType.parse("image/png"), img);
        HashMap<String, RequestBody> params = new HashMap<>();
        params.put("userId", createRequestBody(user.getUserId()));
        params.put("token", createRequestBody(user.getToken()));
        params.put(Constants.KEY_TIME, createRequestBody(time + ""));
        params.put(Constants.KEY_IMEI, createRequestBody(PreferenceImp.getIMEICache()));
        params.put(Constants.KEY_MAC, createRequestBody(PreferenceImp.getMacCache()));
        params.put(Constants.KEY_SERIALNUMBER, createRequestBody(Build.SERIAL));
        params.put(Constants.KEY_VERSION, createRequestBody(CommonUtil.getVersionName()));
        params.put(Constants.KEY_CHANNEL, createRequestBody(CommonUtil.getChannel()));
        params.put(Constants.KEY_SIGN, createRequestBody(CommonUtil.getSign(map)));

        params.put("userImg\"; filename=\"" + img.getName(), body1);
        ApiManager.getArdApi().reviseUserImg(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    private RequestBody createRequestBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

    private boolean reviseUsername(String username) {
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, R.string.input_username, Toast.LENGTH_SHORT).show();
        } else {
            ApiManager.getArdApi().reviseUsername(user.getUserId(), username, user.getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this);
        }
        return !TextUtils.isEmpty(username);
    }

    @Override
    public void onLogin(User user) {
        initView();
    }

    @Override
    public void onLogout() {

    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);//打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    launchCamera();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    reviseUserImg(outputImage);
//                    try {
//                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//                        ((ImageView) findViewById(R.id.img_test)).setImageBitmap(bitmap);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) { //判断手机系统版本号
                    handleImageOnKitKat(data);
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) { //如果是document类型的Uri,则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) { //如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) { //如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath);//根据图片路径显示图片
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            File file = new File(imagePath);
            if (file.exists()) {
                reviseUserImg(file);
            }
        } else {
            Toast.makeText(this, "failed to get iamge", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(IModel iModel) {
        if (iModel instanceof ReviseUsernameModel) {
            ReviseUsernameModel model = (ReviseUsernameModel) iModel;
            if (200 == model.getCode()) {
                user.setUserName(model.getData().getUsername());
                UserBus.getInstance().login(user);
            } else {
                Toast.makeText(UserSettingActivity.this, model.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if (iModel instanceof ReviseUserImgModel) {
            ReviseUserImgModel reviseUserImgModel = (ReviseUserImgModel) iModel;
            if (200 == reviseUserImgModel.getCode()) {
                user.setUserImg(reviseUserImgModel.getData().getUserImg());
                UserBus.getInstance().login(user);
            } else {
                Toast.makeText(UserSettingActivity.this, reviseUserImgModel.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onComplete() {

    }
}

