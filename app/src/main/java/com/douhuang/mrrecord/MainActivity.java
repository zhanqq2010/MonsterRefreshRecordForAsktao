package com.douhuang.mrrecord;

import android.app.Dialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.douhuang.mrrecord.adapter.MonsterRefreshRecordRecyclerViewAdapter;
import com.douhuang.mrrecord.custom.AddMonsterRecordPopup;
import com.douhuang.mrrecord.entity.MonsterRecord;
import com.lxj.xpopup.XPopup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    AppCompatButton btnDelete;
    List<MonsterRecord> monsterList = new ArrayList<>();
    RecyclerView recyclerView;


    private static final long DOUBLE_CLICK_INTERVAL = 1000; // 两次点击间隔，单位毫秒
    private long lastBackPressTime = 0;


    MonsterRefreshRecordRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        btnDelete = findViewById(R.id.btnDel);



        initData();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MonsterRefreshRecordRecyclerViewAdapter(monsterList);
        recyclerView.setAdapter(mAdapter);

        btnDelete.setOnClickListener(v -> {
            showMultiPopup();
        });




    }

    private void initData() {

    }



    public void showMultiPopup(){
//        final BasePopupView loadingPopup = new XPopup.Builder(this).asLoading();
//        loadingPopup.show();
//        new XPopup.Builder(MainActivity.this)
//                .autoDismiss(false)
//                .asBottomList("haha", new String[]{"点我显示弹窗", "点我显示弹窗", "点我显示弹窗", "点我显示弹窗"}, new OnSelectListener() {
//                    @Override
//                    public void onSelect(int position, String text) {
//                        new XPopup.Builder(MainActivity.this).asConfirm("测试", "aaaa", new OnConfirmListener() {
//                            @Override
//                            public void onConfirm() {
//                                loadingPopup.dismiss();
//                            }
//                        }).show();
//                    }
//                }).show();

        new XPopup.Builder(MainActivity.this)
                .hasShadowBg(false)
                .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                .isViewMode(true)
                .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
//                        .isThreeDrag(true) //是否开启三阶拖拽，如果设置enableDrag(false)则无效
                .asCustom(new AddMonsterRecordPopup(MainActivity.this))
                .show();

//        initTimePicker();
//        pvTime.show();

//        initCustomTimePicker();
//        pvCustomTime.show();


    }




    private TimePickerView pvTime, pvCustomTime;
    ConstraintLayout mView;


    private void initTimePicker() {//Dialog 模式下，在底部弹出
//        Toast.makeText(getApplicationContext(), "initTimePicker", Toast.LENGTH_SHORT).show();

        pvTime = new TimePickerBuilder(MainActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
//                Toast.makeText(MainActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                Log.i("pvTime", "onTimeSelect");

            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                })
                .setType(new boolean[]{true, true, true, true, true, true})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("pvTime", "onCancelClickListener");
                    }
                })
                .setItemVisibleCount(5) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
                .setLineSpacingMultiplier(2.0f)
                .isAlphaGradient(true)
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.3f);
            }
        }
    }

    private void initCustomTimePicker() {
//        Toast.makeText(this, "initCustomTimePicker", Toast.LENGTH_SHORT).show();
        /**
         * @description
         *
         * 注意事项：
         * 1.自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针.
         * 具体可参考demo 里面的两个自定义layout布局。
         * 2.因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
         * setRangDate方法控制起始终止时间(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
         */
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2014, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2027, 2, 28);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerBuilder(MainActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
//                btn_CustomTime.setText(getTime(date));
//                Toast.makeText(getApplicationContext(), getTime(date), Toast.LENGTH_SHORT).show();
            }
        })
                /*.setType(TimePickerView.Type.ALL)//default is all
                .setCancelText("Cancel")
                .setSubmitText("Sure")
                .setContentTextSize(18)
                .setTitleSize(20)
                .setTitleText("Title")
                .setTitleColor(Color.BLACK)
               /*.setDividerColor(Color.WHITE)//设置分割线的颜色
                .setTextColorCenter(Color.LTGRAY)//设置选中项的颜色
                .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
                .setTitleBgColor(Color.DKGRAY)//标题背景颜色 Night mode
                .setBgColor(Color.BLACK)//滚轮背景颜色 Night mode
                .setSubmitColor(Color.WHITE)
                .setCancelColor(Color.WHITE)*/
                /*.animGravity(Gravity.RIGHT)// default is center*/
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })
                .setContentTextSize(18)
                .setType(new boolean[]{false, false, false, true, true, true})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public void processData(String monsterName, String time, String circuitName) {
        if (TextUtils.isEmpty(monsterName) || TextUtils.isEmpty(time)  ||    TextUtils.isEmpty(circuitName) ) {
            return;
        }
        MonsterRecord monsterRecord = new MonsterRecord();
        monsterRecord.setMonsterName(monsterName);
        monsterRecord.setMonsterRefreshTime(time);
        monsterRecord.setCircuit(circuitName);
        monsterList.add(monsterRecord);
        notifyDataChanged(monsterList);

//        Toast.makeText(MainActivity.this, "添加记录成功  : " + monsterName + time, Toast.LENGTH_SHORT).show();
    }

    private void notifyDataChanged(List<MonsterRecord> monsterList) {
//        for (int i = 0; i < monsterList.size(); i++) {
//            monsterList.get(i).setIndex(i+ 1);
//        }

        Collections.sort(monsterList, Comparator.comparing(MonsterRecord::getMonsterRefreshTime));
        mAdapter.notifyDataSetChanged();
    }




    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastBackPressTime > DOUBLE_CLICK_INTERVAL) {
            // 第一次点击回退键，记录时间并显示提示
//            Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
        } else {
            // 第二次点击回退键，在指定时间间隔内，则退出应用
            super.onBackPressed();
        }
        lastBackPressTime = System.currentTimeMillis();
    }
}