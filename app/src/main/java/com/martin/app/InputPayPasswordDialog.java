package com.martin.app;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.Arrays;

/**
 * <pre>
 *     e-mail : majingze@shuangke.net.cn
 *     time   : 2017/12/14
 *     desc   :
 *     version: 1.0
 *     Copyright: Copyright（c）2017
 *     Company:爽客智能设备有限公司
 * </pre>
 *
 * @author majingze
 */
public class InputPayPasswordDialog extends DialogFragment {

    /**
     * 删除功能位置
     */
    public static final int DELETE_POSITION = 11;
    /**
     * 空白键位置
     */
    public static final int SPACE_POSITION = 9;

    /**
     * 密码最大长度
     */
    public static final int PASSWORD_MAX_LENGTH = 6;

    private String[] calculatorValue = new String[]{"1", "2", "3", "4", "5", "6",
            "7", "8", "9", "", "0", "删除"};

    private RecyclerView calculator;
    private PayPasswordEditText passwordView;

    private StringBuilder password = new StringBuilder();

    public static InputPayPasswordDialog newInstance() {
        return new InputPayPasswordDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View rootView = inflater.inflate(R.layout.input_pay_password_layout, container, false);
        calculator = rootView.findViewById(R.id.calculator);
        passwordView = rootView.findViewById(R.id.passwordView);
        initCalculatorView();
        getDialog().getWindow().setWindowAnimations(R.style.animate_dialog);
        return rootView;
    }

    private void initCalculatorView() {
        calculator.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        BaseQuickAdapter adapter = new CalculatorAdapter(R.layout.calculator_item, Arrays.asList(calculatorValue));
        calculator.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == DELETE_POSITION) {
                    if (StringUtils.isEmpty(password)) {
                        //没有输入密码时，删除键不起作用
                        return;
                    }
                    password.deleteCharAt(password.length() - 1);
                } else if (position == SPACE_POSITION) {
                    return;
                } else {
                    if (password.length() >= PASSWORD_MAX_LENGTH) {
                        //密码最大长度为6
                        return;
                    }
                    password.append(calculatorValue[position]);
                }
                passwordView.setText(password.toString());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}

