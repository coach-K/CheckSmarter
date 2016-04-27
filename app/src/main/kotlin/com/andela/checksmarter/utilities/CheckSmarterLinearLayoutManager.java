package com.andela.checksmarter.utilities;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * Created by CodeKenn on 25/04/16.
 */
public class CheckSmarterLinearLayoutManager extends LinearLayoutManager {
    public CheckSmarterLinearLayoutManager(Context context) {
        super(context);
    }

    public CheckSmarterLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CheckSmarterLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }
}
