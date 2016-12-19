package com.zhy.flowlayout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by zhy on 15/9/10.
 */
public class SingleChooseFragment extends Fragment {
    private String[] mColor = new String[]{"红色", "白色", "黑色", "蓝色"};
    private String[] mSize = new String[]{"15", "16", "17", "18"};

    private TagFlowLayout mColorFlowLayout;
    private TagFlowLayout mSizeFlowLayout;

    private Set<Integer> mColorSelectd;
    private Set<Integer> mSizeSelectd;
    private String redFlag = "";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_single_choose, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final LayoutInflater mInflater = LayoutInflater.from(getActivity());
        mColorFlowLayout = (TagFlowLayout) view.findViewById(R.id.flowlayout_color);
        mSizeFlowLayout = (TagFlowLayout) view.findViewById(R.id.flowlayout_size);


        final TagAdapter<String> colorAdapter = new TagAdapter<String>(mColor) {
            @Override
            public View getView(FlowLayout parent, int position, String colorString) {
                TextView tvColor = (TextView) mInflater.inflate(R.layout.tv, mColorFlowLayout, false);
                tvColor.setText(colorString);
                return tvColor;
            }
        };

        final TagAdapter<String> sizeAdapter = new TagAdapter<String>(mSize) {
            @Override
            public View getView(FlowLayout parent, int position, String sizeString) {
                TextView tvSize = (TextView) mInflater.inflate(R.layout.tv, mSizeFlowLayout, false);

                if (!redFlag.isEmpty() && "红色".equals(redFlag)) {
                    if ("17".equals(sizeString) || "18".equals(sizeString)) {
                        tvSize.setBackgroundResource(R.drawable.shape_specification_no_this_size);

                    }
                } else {
                    tvSize.setBackgroundResource(R.drawable.tag_bg);
                }

                tvSize.setText(sizeString);
                return tvSize;
            }
        };

        mColorFlowLayout.setAdapter(colorAdapter);
        mSizeFlowLayout.setAdapter(sizeAdapter);

        mColorFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(getActivity(), mColor[position], Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mSizeFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(getActivity(), mSize[position], Toast.LENGTH_SHORT).show();
                return true;
            }
        });


        mColorFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (selectPosSet != null && selectPosSet.size() > 0) {
                    Iterator<Integer> iterator = selectPosSet.iterator();
                    while (iterator.hasNext()) {
                        Integer colorInteger = iterator.next();
                        String color = mColor[colorInteger];
                        if ("红色".equals(color)) {
                            redFlag = "红色";
                            sizeAdapter.notifyDataChanged();
                            ArrayList<Integer> arrayList = new ArrayList<>();
                            arrayList.add(2);
                            arrayList.add(3);
                            mSizeFlowLayout.setCanNotSelectPosition(arrayList);

                        } else {
                            redFlag = "";
                            sizeAdapter.notifyDataChanged();
                            mSizeFlowLayout.clearList();
                        }
                    }

                } else {
                    redFlag = "";
                    sizeAdapter.notifyDataChanged();
                }


            }
        });

        mSizeFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (selectPosSet != null && selectPosSet.size() > 0) {
                    Iterator<Integer> iterator = selectPosSet.iterator();
                    while (iterator.hasNext()) {
                        Integer sizeInteger = iterator.next();
                    }

                } else {

                }

            }
        });
    }




    private StateListDrawable addStateDrawable(Context context, int idNormal, int idChecked) {
        StateListDrawable sd = new StateListDrawable();
        Drawable normal = idNormal == -1 ? null : context.getResources().getDrawable(idNormal);
        Drawable checked = idChecked == -1 ? null : context.getResources().getDrawable(idChecked);
        //注意该处的顺序，只要有一个状态与之相配，背景就会被换掉
        //所以不要把大范围放在前面了，如果sd.addState(new[]{},normal)放在第一个的话，就没有什么效果了
        sd.addState(new int[]{android.R.attr.state_checked}, checked);
        sd.addState(new int[]{}, normal);
        return sd;
    }




}
