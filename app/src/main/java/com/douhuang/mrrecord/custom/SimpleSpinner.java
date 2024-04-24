package com.douhuang.mrrecord.custom;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.douhuang.mrrecord.R;

import java.util.ArrayList;

public class SimpleSpinner extends androidx.appcompat.widget.AppCompatSpinner {

    private ArrayAdapter adapter;
    private ArrayList<String> contents;
    private String defaultContent;
    private Context context;
    public SimpleSpinner(Context context) {
        super(context);
        onCreate(context);
    }

    public SimpleSpinner(Context context, int mode) {
        super(context, mode);
        onCreate(context);
    }

    public SimpleSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        onCreate(context);
    }

    public SimpleSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onCreate(context);
    }
    /**
     * @author gaoql
     * @time 2022/3/1 11:05
     * @description 初始化
     */
    private void onCreate(Context context){
        this.context = context;
    }
    /**
     * @author gaoql
     * @time 2022/3/1 11:44
     * @description 设置spinner数据
     */
    public void setContent(ArrayList<String> contents){
        this.contents = contents;
        if(adapter!=null){
            adapter = null;
        }
        adapter = new ArrayAdapter(getContext(), R.layout.sp_item_select,contents.toArray(new String[]{}));
        adapter.setDropDownViewResource(R.layout.sp_item_dropdown);
        this.setAdapter(adapter);
    }



    /**
     * @author gaoql
     * @time 2022/3/3 9:12
     * @description 设置默认显示内容
     */
    public void setDefaultContent(String defaultContent){
        this.defaultContent = defaultContent;
    }
    /**
     * @author gaoql
     * @time 2022/3/1 13:26
     * @description 选择事件
     */
    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener){
        setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //首次刚加载时 判断是否设置默认显示内容，如果有则显示默认内容,逻辑是把第一条的view内容修改掉
                if(!TextUtils.isEmpty(defaultContent)){
                    if(view instanceof TextView){
                        TextView textView = (TextView) view;
                        textView.setText(defaultContent);
                        textView.setTextColor(Color.parseColor("#999999"));
                        defaultContent = "";
                    }
                }else{
                    //把刚刚修改的内容和颜色都修改回来
                    if(position == 0){
                        if(view instanceof TextView){
                            TextView textView = (TextView) view;
                            textView.setText(contents.get(0));
                            textView.setTextColor(Color.parseColor("#ffffff"));
                        }
                    }
                    onItemSelectedListener.onItemSelected(parent,view,position,id,contents.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public interface OnItemSelectedListener{
        void onItemSelected(AdapterView<?> adapterView, View view, int position, long id, String content);
    }
    /**
     * @author gaoql
     * @time 2022/3/3 10:22
     * @description 解决 选择相同选项时 点击不触发问题，逻辑是 如果点击的是相同选项则 强制调用一次条码选择事件
     */
    @Override
    public void setSelection(int position) {
        super.setSelection(position);
        boolean sameSelected = position == getSelectedItemPosition();
        if (sameSelected) {
            getOnItemSelectedListener().onItemSelected(this, getSelectedView(),
                    position, getSelectedItemId());
        }
    }
}