package com.douhuang.mrrecord.custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.douhuang.mrrecord.MainActivity;
import com.douhuang.mrrecord.R;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AddMonsterRecordPopup extends BottomPopupView {

    AppCompatSpinner mSpinnerMonsterName, mSpinnerCircuit;
    String[] defaultData;
    String[] defaultCircuitData;
//    private TimePickerView pvTime;
    private TimePickerView pvTime, pvCustomTime;
    ConstraintLayout mView;

    AppCompatEditText mEtTime;

    AppCompatButton mBtnAddRecord;


    public AddMonsterRecordPopup(@NonNull Context context) {
        super(context);
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_add_record;
    }


    @Override
    protected void onCreate() {
        super.onCreate();
        initData();

        View mViewGroup = View.inflate(getActivity().getApplicationContext().getApplicationContext(),R.layout.dialog_add_record, null);
        mView = mViewGroup.findViewById(R.id.container);

//        initTimePicker();
        initCustomTimePicker();

        mEtTime = findViewById(R.id.etTime);
        mBtnAddRecord = findViewById(R.id.btnAddRecord);

//        mEtTime.setOnClickListener(new OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "onClick", Toast.LENGTH_SHORT).show();
//                pvCustomTime.show();
//            }
//        });

        mEtTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {      //获取焦点

                    pvCustomTime.show();
                    mEtTime.setText("");
//                    Toast.makeText(getContext(), "获取焦点", Toast.LENGTH_SHORT).show();
                }else {
//                    Toast.makeText(getContext(), "失去焦点", Toast.LENGTH_SHORT).show();
                }
            }

        });

        //怪物列表
        mSpinnerMonsterName = findViewById(R.id.spinnerMonsterName);

//        ArrayList<String> list1 = Arrays.stream(defaultData).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
//        mSpinnerMonsterName.setContent(list1);
//        mSpinnerMonsterName.setDefaultContent("请选择怪物");
        // 创建一个 ArrayAdapter 并将默认数据设置到 Spinner 中
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplication().getApplicationContext(), android.R.layout.simple_spinner_item, defaultData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        mSpinnerMonsterName.setOnItemSelectedListener(new SimpleSpinner.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id, String content) {
//
//                Toast.makeText(getContext(),"您选择的是："+content+" 第"+position+"个  id:"+id,Toast.LENGTH_SHORT).show();
//            }
//        });


        mSpinnerMonsterName.setAdapter(adapter);


        //线路列表
        mSpinnerCircuit = findViewById(R.id.spinnerCircuit);

//        ArrayList<String> list2 = Arrays.stream(defaultCircuitData).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
//        mSpinnerMonsterName.setContent(list2);
//        mSpinnerCircuit.setDefaultContent("请选择怪物刷新的线路");
        // 创建一个 ArrayAdapter 并将默认数据设置到 Spinner 中
        ArrayAdapter<String> circuitAdapter = new ArrayAdapter<>(getActivity().getApplication().getApplicationContext(), android.R.layout.simple_spinner_item, defaultCircuitData);
        circuitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//        mSpinnerCircuit.setOnItemSelectedListener(new SimpleSpinner.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id, String content) {
//
//                Toast.makeText(getContext(),"您选择的是："+content+" 第"+position+"个  id:"+id,Toast.LENGTH_SHORT).show();
//            }
//        });
        mSpinnerCircuit.setAdapter(circuitAdapter);


        mBtnAddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String monsterName = mSpinnerMonsterName.getSelectedItem().toString();
                String circuitName = mSpinnerCircuit.getSelectedItem().toString();
                String time = mEtTime.getText().toString();

                if (TextUtils.isEmpty(time) || TextUtils.isEmpty(monsterName) || TextUtils.isEmpty(circuitName) || "请选择怪物名称".equals(monsterName) || "请选择怪物刷新线路".equals(circuitName)) {
                    Toast.makeText(getContext(), "输入有误,请检查！", Toast.LENGTH_SHORT).show();
                    return;
                }

                ((MainActivity)getContext()).processData(monsterName, time, circuitName);
//                processData(monsterName, time);
//                Toast.makeText(getContext(), "添加记录成功  : " + monsterName + time, Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });




    }

    private void initData() {
        defaultData = new String[]{"请选择怪物名称", "孔雀妖姬", "白骨精", "罗刹王","夜叉王", "牛魔王", "百花羞", "百年象精", "百年猪妖", "百年刺猬精", "百年狂狮怪","百年h黑熊精", "牛头怪", "洋头怪"};
//        defaultCircuitData = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};
        defaultCircuitData = new String[]{"请选择怪物刷新线路","1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100"};
    }

    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getScreenHeight(getContext()) * .7f);
    }


    @Override
    protected boolean onBackPressed() {
//        Toast.makeText(getContext(), "拦截返回", Toast.LENGTH_SHORT).show();
        return true;
    }









    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }




    private void initTimePicker() {//Dialog 模式下，在底部弹出
        pvTime = new TimePickerBuilder(getActivity().getApplicationContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
//                Toast.makeText(getActivity().getApplicationContext(), getTime(date), Toast.LENGTH_SHORT).show();
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
        pvCustomTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
//                btn_CustomTime.setText(getTime(date));
                onSelectCallback(date);
//                Toast.makeText(getActivity(), getTime(date), Toast.LENGTH_SHORT).show();
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

    private void onSelectCallback(Date date) {
        mEtTime.setText(getTime(date));

//        Toast.makeText(getActivity(), getTime(date), Toast.LENGTH_SHORT).show();
    }
}
