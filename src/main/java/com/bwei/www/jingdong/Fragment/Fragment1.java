package com.bwei.www.jingdong.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.www.jingdong.Activity.BunnerActivity;
import com.bwei.www.jingdong.Activity.FlGoodsXiangqingActivity;
import com.bwei.www.jingdong.Activity.Fragment1_Activity;
import com.bwei.www.jingdong.Adapter.MyTuiJianAdapter;
import com.bwei.www.jingdong.Api.ApiUrl;
import com.bwei.www.jingdong.Bean.LunBoBean;
import com.bwei.www.jingdong.Qrcode.QrCode;
import com.bwei.www.jingdong.R;
import com.bwei.www.jingdong.entry.CallBack;
import com.bwei.www.jingdong.entry.IView;
import com.bwei.www.jingdong.http.HttpUtils;
import com.bwei.www.jingdong.imageLoader.MLimageLoader;
import com.bwei.www.jingdong.other.TitleimgActivity;
import com.bwei.www.jingdong.presenter.TuiJian_Presenter;
import com.bwei.www.jingdong.zuheView.ENoticeView;
import com.bwei.www.jingdong.zuheView.Item;
import com.bwei.www.jingdong.zuheView.MyView;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @作者 任百慧
 * @时间 2017/11/5 19:46
 */

public class Fragment1 extends Fragment implements View.OnClickListener, IView {
    private static final String TAG = "Fragment1";
    int REQUEST_CODE = 0;
    private View view;
    private MyView tit;
    private ArrayList<String> list = new ArrayList<>();
    private Banner banner;
    private ImageView img;
    private ViewPager vp;
    ArrayList<Fragment> fragList = new ArrayList<>();
    ArrayList<ImageView> imglist = new ArrayList<>();
    private LinearLayout ll;
    private ImageView imageView;
    private RecyclerView rv_tj;
    ArrayList<LunBoBean.TuijianBean.ListBean> listBeen = new ArrayList<>();
    private MyTuiJianAdapter jianAdapter;
    private TextView miaosha_time;
    private TextView miaosha_shi;
    private TextView miaosha_minter;
    private TextView miaosha_second;
    ArrayList<Item> items = new ArrayList<>();

   Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setTime();
            sendEmptyMessageDelayed(0, 1000);
        }
    };
    private ENoticeView eNoticeView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1, container, false);
        banner = (Banner) view.findViewById(R.id.banner);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ZXingLibrary.initDisplayOpinion(getActivity());
        //初始化控件
        initView();
        handler.sendEmptyMessage(0);
        //创建fragment对象
        Vp_Recycle_Fragment1 vp_recycle_fragment1 = new Vp_Recycle_Fragment1();
        Vp_Recycle_Fragment2 vp_recycle_fragment2 = new Vp_Recycle_Fragment2();
        //添加到集合
        fragList.add(vp_recycle_fragment1);
        fragList.add(vp_recycle_fragment2);
        FragmentManager manager = getActivity().getSupportFragmentManager();
        //设置ViewPager适配器
        vp.setAdapter(new MyAdapter(manager));
        ViewPagerListener();

        //自绘控件顶部点击事件
        tit.setOntitClickLstener(new MyView.OntitClickLstener() {
            @Override
            public void onsaoyisaoClick(View v) {
                //扫描二维码
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }

            @Override
            public void onsousuoClick(View v) {
                //搜索商品
                final Intent intent = new Intent(getActivity(), Fragment1_Activity.class);
                startActivity(intent);
            }

            @Override
            public void onxiaoxiClick(View v) {
                //跳转webView
                Uri uri = Uri.parse("http://baike.baidu.com/view/753813.htm");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
        addView();
        //初始化点
        initDot();
        initpre();
        //京东快报
        String[] str={"三星Note7爆炸事件再次升温","全国房价急速下滑！","喜讯：二胎政策全面实施","人生，一分拥有，一分失去","换个角度，换来不一样的世界"};

        for(int i = 0;i<5;i++){
            Item item = new Item();
            item.setName(str[i]);
            items.add(item);
        }
        eNoticeView.setData(items);

    }

    private void initpre() {
        jianAdapter = new MyTuiJianAdapter(getActivity(), listBeen);
        final GridLayoutManager manager = new GridLayoutManager(getActivity(),2);
        rv_tj.setLayoutManager(manager);
        TuiJian_Presenter tuiJian_presenter = new TuiJian_Presenter();
        tuiJian_presenter.attachView(this);
        tuiJian_presenter.getViews();
        rv_tj.setAdapter(jianAdapter);
        jianAdapter.setOnItemClick(new MyTuiJianAdapter.OnItemClick() {
            @Override
            public void OnItemClick(View view, int position) {
                final int pid = listBeen.get(position).getPid();
                final Intent intent = new Intent(getActivity(), FlGoodsXiangqingActivity.class);
                intent.putExtra("pid",pid+"");
                startActivity(intent);

            }
        });
    }

    private void ViewPagerListener() {
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                for (int j = 0; j <imglist.size() ; j++) {
                    if(j%imglist.size()==i)
                    {
                        imglist.get(j%fragList.size()).setImageResource(R.drawable.s_dot);
                    }else
                    {
                        imglist.get(j).setImageResource(R.drawable.k_dot);

                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    //初始化点
    private void initDot() {
        if(imglist!=null){
            imglist.clear();
        }
        for (int i = 0; i <fragList.size() ; i++) {
            imageView = new ImageView(getActivity());
            if(i==0)
            {
                //第一张，让其圆点进行选中状态
                imageView.setImageResource(R.drawable.s_dot);
            }else
            {
                imageView.setImageResource(R.drawable.k_dot);
                //其它的都不选中
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            params.setMargins(5,0,5,0);
            ll.addView(imageView,params);
            imglist.add(imageView);
        }

    }

    private void initView() {
        tit = (MyView) view.findViewById(R.id.tit);
        vp = (ViewPager) view.findViewById(R.id.vp_recycle);
        ll = (LinearLayout) view.findViewById(R.id.ll);
        rv_tj = (RecyclerView) view.findViewById(R.id.rv_tj);
        eNoticeView = (ENoticeView) view.findViewById(R.id.noticeView);

        miaosha_time = (TextView)view.findViewById(R.id.tv_miaosha_time);
        miaosha_shi = (TextView) view.findViewById(R.id.tv_miaosha_shi);
        miaosha_minter = (TextView) view.findViewById(R.id.tv_miaosha_minter);
        miaosha_second = (TextView) view.findViewById(R.id.tv_miaosha_second);

    }


    private void addView() {
        final HashMap<String, String> map = new HashMap<>();
        HttpUtils.getInstanse().get(ApiUrl.LUNBO, map, new CallBack() {
            @Override
            public void onSuccess(String tag, Object o) {
                LunBoBean lunBoBean = (LunBoBean) o;
                final List<LunBoBean.DataBean> data = lunBoBean.getData();
                for (int i = 0; i < data.size(); i++) {
                    LunBoBean.DataBean dataBean = data.get(i);
                    String icon = dataBean.getIcon();
                    list.add(icon);
                }
                //设置banner样式
                // banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
                //设置图片加载器
                banner.setImageLoader(new MLimageLoader());
                //设置图片集合
                banner.setImages(list);
                //设置banner动画效果
                //banner.setBannerAnimation(Transformer.RotateDown);
                //设置标题集合（当banner样式有显示title时）
                //banner.setBannerTitles(imageTitle);
                //设置轮播时间
                banner.setDelayTime(3000);
                //设置指示器位置（当banner模式中有指示器时）
                //banner.setIndicatorGravity(BannerConfig.CENTER);
                //banner设置方法全部调用完毕时最后调用

                banner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        Intent intent = new Intent(getActivity(), BunnerActivity.class);
                        startActivity(intent);
                    }

                });
                banner.start();
            }
            @Override
            public void onFailed(String tag, Exception e) {
                Log.i(TAG, "onFailure: " + e.getMessage());
            }
        }, LunBoBean.class, "lunbo");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        new QrCode().result(getActivity(),requestCode,data);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), TitleimgActivity.class);
        startActivity(intent);
    }

    @Override
    public void Success(List<LunBoBean.TuijianBean.ListBean> list) {
        if(list!=null){
            listBeen.addAll(list);
            jianAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void onFailed(Exception e) {

    }

    private class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragList.get(position);
    }

        @Override
        public int getCount() {
            return fragList.size();
        }
    }
    //京东秒杀
    //秒杀倒计时
    public void setTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String format = df.format(curDate);
        StringBuffer buffer = new StringBuffer();
        String substring = format.substring(0, 11);
        buffer.append(substring);
        Log.d("ccc", substring);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour % 2 == 0) {
            miaosha_time.setText(hour + "点场");
            buffer.append((hour + 2));
            buffer.append(":00:00");
        } else {
            miaosha_time.setText((hour - 1) + "点场");
            buffer.append((hour + 1));
            buffer.append(":00:00");
        }
        String totime = buffer.toString();
        try {
            Date date = null;
            date = df.parse(totime);
            java.util.Date date1 = df.parse(format);
            long defferenttime = date.getTime() - date1.getTime();
            long days = defferenttime / (1000 * 60 * 60 * 24);
            long hours = (defferenttime - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minute = (defferenttime - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            long seconds = defferenttime % 60000;
            long second = Math.round((float) seconds / 1000);
            miaosha_shi.setText("0" + hours + "");
            if (minute >= 10) {
                miaosha_minter.setText(minute + "");
            } else {
                miaosha_minter.setText("0" + minute + "");
            }
            if (second >= 10) {
                miaosha_second.setText(second + "");
            } else {
                miaosha_second.setText("0" + second + "");
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
