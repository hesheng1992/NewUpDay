package com.example.administrator.newsupday;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.administrator.newsupday.common.LoadImage;
import com.example.administrator.newsupday.common.SharePrefenceUtil;
import com.example.administrator.newsupday.service.MyReciver;
import com.example.administrator.newsupday.ui.AboutActivity;
import com.example.administrator.newsupday.ui.CollectActivity;
import com.example.administrator.newsupday.ui.FragmentHotPoint;
import com.example.administrator.newsupday.ui.FragmentMessage;
import com.example.administrator.newsupday.ui.FragmentSeach;
import com.example.administrator.newsupday.ui.LoginActivity;
import com.example.administrator.newsupday.ui.SettingActivity;
import com.example.administrator.newsupday.ui.base.MyApplication;
import com.example.administrator.newsupday.ui.fragment.FragmentCollect;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private SharePrefenceUtil sharePrefenceUtil;
    boolean Login_Statce;

    //腾讯登录
//    private Tencent mtencent;
//    private String nickname;//得到用户名字
//    private String figureurl_1;//头像地址
    //    private Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.xiong);
    private LoadImage loadImage = new LoadImage(this, new LoadImage.ImageLoadListener() {
        @Override
        public void imageLoadOK(Bitmap bitmap, String url) {
            drawer_iv.setImageBitmap(bitmap);
        }
    });
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
//                drawer_login.setText(nickname);
//                drawer_iv.setImageBitmap(bitmap);

            }
        }
    };


    //绑定toolbar和drawerlayout的工具类
    private ActionBarDrawerToggle drawerToggle;
    private MyReciver reciver = new MyReciver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            if (intent.getAction().equals("CHANGE")) {
                int color = intent.getIntExtra("color", 0);

                toolbar.setBackgroundColor(color);
                setSupportActionBar(toolbar);
                login_color.setBackgroundColor(color);
                Log.e("TAG", "recivier");
            }
        }
    };

//    private AnimationDrawable mAnimationDrawable;//实现打开和关闭侧拉的监听类

    private RelativeLayout rl_nes_unselected, rl_nes_hot, rl_nes_serch;//三个按钮
    private LinearLayout content;//用来放3个fragment的容器；

    private FragmentHotPoint fragmentHotPoint;
    private FragmentMessage fragmentMessage;
    private FragmentSeach fragmentSeach;
    //使用预加载，show，hide的模式。
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    //点击三个按钮的时候，颜色会改变
    private ImageView nes_unselect, nes_hot, nes_serch, drawer_iv;
    private TextView tv_unselect, tv_nes_hot, tv_nes_serch, drawer_login;
    private LinearLayout login_color;

    //抽屉里的布局初始化
    private RelativeLayout drawer_rl_collect, drawer_rl_about, drawer_rl_set;//收藏功能,关于我们,设置


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mtencent = Tencent.createInstance("1105560454", this);//初始化登录的核心操作类
        sharePrefenceUtil = SharePrefenceUtil.getSharePrefenceUtil(this);
        ShareSDK.initSDK(this);
        initView();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("CAHNGE");
        intentFilter.setPriority(Integer.MAX_VALUE);
        registerReceiver(reciver, intentFilter);
        initTherePoint();


    }

    private void initView() {
        int color = sharePrefenceUtil.getColor();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        login_color = (LinearLayout) findViewById(R.id.login_color);
        if (color != 0) {
            toolbar.setBackgroundColor(getResources().getColor(color));
            login_color.setBackgroundColor(getResources().getColor(color));
        }

        toolbar.setTitle("NewsUpDay");
        toolbar.setNavigationIcon(R.drawable.btn_return);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //菜单选择
                ShareSDK.initSDK(MainActivity.this);
                OnekeyShare oks = new OnekeyShare();
                // 关闭 sso  授权
                oks.disableSSOWhenAuthorize();
// title  标题，印象笔记、邮箱、信息、微信、人人网和 QQ  空间使用
                oks.setTitle(getString(R.string.share));
// titleUrl  是标题的网络链接，仅在人人网和 QQ  空间使用
                oks.setTitleUrl("http://sharesdk.cn");
// text  是分享文本，所有平台都需要这个字段
                oks.setText("Changer's First share test ,like ZhangDan");
// imagePath  是图片的本地路径，Linked-In  以外的平台都支持此参数
// oks.setImagePath("/sdcard/test.jpg");
// url  仅在微信（包括好友和朋友圈）中使用
                oks.setUrl("http://sharesdk.cn");
// comment  是我对这条分享的评论，仅在人人网和 QQ  空间使用
                oks.setComment("my first comment");
                oks.show(MainActivity.this);
                Log.e("TAG", "jieshu");

                return true;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        login_color.setOnClickListener(loginlistener);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerToggle.syncState();
        drawerLayout.setDrawerListener(drawerToggle);

        //抽屉里的布局初始化以及实现功能
        drawer_rl_collect = (RelativeLayout) findViewById(R.id.drawer_rl_collect);
        drawer_rl_about = (RelativeLayout) findViewById(R.id.drawer_rl_about);
        drawer_rl_set = (RelativeLayout) findViewById(R.id.drawer_rl_set);
        drawer_iv = (ImageView) findViewById(R.id.drawer_iv);
//        drawer_iv.setImageBitmap(bitmap);
        drawer_login = (TextView) findViewById(R.id.drawer_login);
        drawer_login.setText("hesheng");
        drawer_rl_about.setOnClickListener(listener);
        drawer_rl_collect.setOnClickListener(listener);
        drawer_rl_set.setOnClickListener(listener);


    }

    private void initTherePoint() {
        rl_nes_hot = (RelativeLayout) findViewById(R.id.rl_nes_hot);
        rl_nes_serch = (RelativeLayout) findViewById(R.id.rl_nes_serch);
        rl_nes_unselected = (RelativeLayout) findViewById(R.id.rl_nes_unselected);
        content = (LinearLayout) findViewById(R.id.content);
        nes_hot = (ImageView) findViewById(R.id.nes_hot);
        nes_serch = (ImageView) findViewById(R.id.nes_serch);
        nes_unselect = (ImageView) findViewById(R.id.nes_unselect);
        tv_nes_hot = (TextView) findViewById(R.id.tv_nes_hot);
        tv_nes_serch = (TextView) findViewById(R.id.tv_nes_serch);
        tv_unselect = (TextView) findViewById(R.id.tv_unselect);
        fragmentSeach = new FragmentSeach();
        fragmentHotPoint = new FragmentHotPoint();
        fragmentMessage = new FragmentMessage();
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.content, fragmentMessage).add(R.id.content, fragmentHotPoint).add(R.id.content, fragmentSeach);
        transaction.hide(fragmentSeach).hide(fragmentHotPoint).show(fragmentMessage);
        nes_unselect.setImageResource(R.drawable.new_selected);
        tv_unselect.setTextColor(Color.BLUE);
        nes_hot.setImageResource(R.drawable.collect_unselected);
        tv_nes_hot.setTextColor(Color.BLACK);
        nes_serch.setImageResource(R.drawable.find_defult);
        tv_nes_serch.setTextColor(Color.BLACK);
        transaction.commit();


        rl_nes_hot.setOnClickListener(onclick);
        rl_nes_unselected.setOnClickListener(onclick);
        rl_nes_serch.setOnClickListener(onclick);
    }

    private View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            transaction = fragmentManager.beginTransaction();

            switch (view.getId()) {
                case R.id.rl_nes_hot:
                    nes_hot.setImageResource(R.drawable.collect_selected);
                    tv_nes_hot.setTextColor(Color.BLUE);
                    transaction.hide(fragmentMessage).hide(fragmentSeach).show(fragmentHotPoint);
                    nes_serch.setImageResource(R.drawable.find_defult);
                    tv_nes_serch.setTextColor(Color.BLACK);
                    nes_unselect.setImageResource(R.drawable.new_unselected);
                    tv_unselect.setTextColor(Color.BLACK);
                    break;
                case R.id.rl_nes_serch:
                    transaction.hide(fragmentHotPoint).hide(fragmentMessage).show(fragmentSeach);
                    nes_serch.setImageResource(R.drawable.find_selected);
                    tv_nes_serch.setTextColor(Color.BLUE);
                    nes_hot.setImageResource(R.drawable.collect_unselected);
                    tv_nes_hot.setTextColor(Color.BLACK);
                    nes_unselect.setImageResource(R.drawable.new_unselected);
                    tv_unselect.setTextColor(Color.BLACK);

                    break;
                case R.id.rl_nes_unselected:
                    transaction.hide(fragmentSeach).hide(fragmentHotPoint).show(fragmentMessage);
                    nes_unselect.setImageResource(R.drawable.new_selected);
                    tv_unselect.setTextColor(Color.BLUE);
                    nes_hot.setImageResource(R.drawable.collect_unselected);
                    tv_nes_hot.setTextColor(Color.BLACK);
                    nes_serch.setImageResource(R.drawable.find_defult);
                    tv_nes_serch.setTextColor(Color.BLACK);

                    break;
            }
            transaction.commit();

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_menu, menu);
        return true;
    }


    //抽屉点击事件
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.drawer_rl_about:
                    Intent intent2 = new Intent(MainActivity.this, AboutActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.drawer_rl_collect:
                    Intent intent = new Intent(MainActivity.this, CollectActivity.class);
                    startActivity(intent);
                    break;
                case R.id.drawer_rl_set:
                    Intent intent1 = new Intent(MainActivity.this, SettingActivity.class);
                    startActivity(intent1);
                    break;


            }


        }
    };

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        int color = sharePrefenceUtil.getColor();
        if (color != 0) {
            toolbar.setBackgroundColor(getResources().getColor(color));
            login_color.setBackgroundColor(getResources().getColor(color));
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(reciver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("TAG", "onStop");
    }

    private View.OnClickListener loginlistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(MainActivity.this, LoginActivity.class);
            startActivityForResult(intent, 1);
        }
    };

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        Log.e("TAG","登录==="+resultCode);
        if (resultCode == 2) {
            Login_Statce = true;
            drawer_login.setText("退出登录");
        }
        if (resultCode == 1) {
            Login_Statce = true;
            Log.e("TAG","登录");
            drawer_login.setText("退出登录");
            String username = data.getStringExtra("username");
            drawer_login.setText(username);
            String icon_path = data.getStringExtra("usericon");
            ImageRequest imageRequest = new ImageRequest(
                    icon_path, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    drawer_iv.setImageBitmap(bitmap);
                }
            }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    drawer_iv.setImageResource(R.drawable.dage);
                }
            });
            MyApplication.getApplication(this).addToRequestQueue(imageRequest);

        }

    }
}

//    //点击登录
//    private View.OnClickListener loginlistener = new View.OnClickListener() {
//
//        @Override
//        public void onClick(View view) {
//
//        }
//    }

//            mtencent.login(MainActivity.this, "all", new IUiListener() {
//                @Override
//                public void onComplete(Object response) {
//                    Toast.makeText(getApplicationContext(), "回调成功",Toast.LENGTH_SHORT).show();
////                    JSONObject responseJsonobject = (JSONObject) response;
////                    final String openid = responseJsonobject.optString("openid");
////                    final String access_token = responseJsonobject.optString("access_token");
////                    final String expires_in = responseJsonobject.optString("expires_in");
//                    Log.e("TAG","登录成功"+response.toString());
//
//                    QQToken qqToken = mtencent.getQQToken();
//                    //得到用户信息的类
//                    UserInfo info=new UserInfo(MainActivity.this,qqToken);
//                    info.getUserInfo(new IUiListener() {
//                        @Override
//                        public void onComplete(Object response) {
//                            JSONObject jsonObject = (JSONObject) response;
//                            nickname = jsonObject.optString("nickname");
//                            try {
//                                figureurl_1 = jsonObject.getString("figureurl_1");
//                                Log.e("TAG","asfesdf");
//                                Bitmap bitmap = loadImage.getBitmap(figureurl_1);
//                                drawer_iv.setImageBitmap(bitmap);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            handler.sendEmptyMessage(100);
//
//                        }
//
//                        @Override
//                        public void onError(UiError uiError) {
//                            Log.e("TAG","获取用户信息失败");
//                        }
//
//                        @Override
//                        public void onCancel() {
//
//                        }
//                    });
//
//                }
//
//                @Override
//                public void onError(UiError uiError) {
//                    Log.e("TAG","授权失败");
//                }
//
//                @Override
//                public void onCancel() {
//
//                }
//            });









