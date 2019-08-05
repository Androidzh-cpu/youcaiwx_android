package com.ucfo.youcai.widget.flowlayout;

import android.content.Context;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;

/**
 * Author:AND
 * Time: 2019-3-6.  上午 11:23
 * Email:2911743255@qq.com
 * ClassName: TagView
 * Package: com.ucfo.youcai.utils.flowlayout
 * Description:
 * Detail:
 */
public class TagView  extends FrameLayout implements Checkable {

    private static final int[] CHECK_STATE = new int[]{android.R.attr.state_checked};
    private boolean isChecked;

    public TagView(Context context) {
        super(context);
    }

    public View getTagView() {
        return getChildAt(0);
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        int[] states = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(states, CHECK_STATE);
        }
        return states;
    }


    /**
     * Change the checked state of the view
     *
     * @param checked The new checked state
     */
    @Override
    public void setChecked(boolean checked) {
        if (this.isChecked != checked) {
            this.isChecked = checked;
            refreshDrawableState();
        }
    }

    /**
     * @return The current checked state of the view
     */
    @Override
    public boolean isChecked() {
        return isChecked;
    }

    /**
     * Change the checked state of the view to the inverse of its current state
     */
    @Override
    public void toggle() {
        setChecked(!isChecked);
    }

}
