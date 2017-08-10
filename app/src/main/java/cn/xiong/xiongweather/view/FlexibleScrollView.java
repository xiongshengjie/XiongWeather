package cn.xiong.xiongweather.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2017/8/10.
 */

public class FlexibleScrollView extends ScrollView {
    private Context mContext;
    private static int mMaxOverDistance = 50;

    public FlexibleScrollView(Context context, AttributeSet attrs,
                              int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    public FlexibleScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    public FlexibleScrollView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    private void initView() {
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        float density = metrics.density;
        mMaxOverDistance = (int) (density * mMaxOverDistance);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                   int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        if(!isTouchEvent){
            if((scrollY<0&&deltaX<0)||(scrollY > getHeight()&&deltaX>0)){
                deltaY = 0;
            }
        }
        return super.overScrollBy(deltaX, (deltaY+1)/2, scrollX, scrollY,
                scrollRangeX, scrollRangeY, maxOverScrollX, mMaxOverDistance,
                isTouchEvent);
    }
}
