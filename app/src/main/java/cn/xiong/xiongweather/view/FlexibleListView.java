package cn.xiong.xiongweather.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ListView;

/**
 * Created by Administrator on 2017/8/11.
 */

public class FlexibleListView extends ListView{

    private Context context;
    private static int mMaxOverDistance = 50;

    public FlexibleListView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public FlexibleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public FlexibleListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
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
