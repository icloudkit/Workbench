package cn.com.teamlink.workbench.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TableView extends ViewGroup {

    // 起始X坐标
    private static final int STARTX = 0;
    // 起始Y坐标
    private static final int STARTY = 0;
    // 表格边框宽度
    private static final int BORDER = 2;

    // 行数
    private int mRow;
    // 列数
    private int mCol;

    public TableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 默认行数为3
        this.mRow = 3;
        // 默认列数为3
        this.mCol = 3;
        // 添加子控件
        this.addOtherView(context);
    }

    public TableView(Context context, int row, int col) {
        super(context);
        if (row > 20 || col > 20) {
            // 大于20行时，设置行数为20行
            this.mRow = 20;
            // 大于20列时，设置列数为20列
            this.mCol = 20;
        } else if (row == 0 || col == 0) {
            this.mRow = 3;
            this.mCol = 3;
        } else {
            this.mRow = row;
            this.mCol = col;
        }
        // 添加子控件
        this.addOtherView(context);
    }

    public void addOtherView(Context context) {
        int value = 1;
        for (int i = 1; i <= mRow; i++) {
            for (int j = 1; j <= mCol; j++) {
                TextView view = new TextView(context);
                view.setText(String.valueOf(value++));
                view.setTextColor(Color.rgb(79, 129, 189));
                view.setGravity(Gravity.CENTER);
                if (i % 2 == 0) {
                    view.setBackgroundColor(Color.rgb(219, 238, 243));
                } else {
                    view.setBackgroundColor(Color.rgb(235, 241, 221));
                }
                this.addView(view);
            }
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(BORDER);
        paint.setColor(Color.rgb(79, 129, 189));
        paint.setStyle(Paint.Style.STROKE);
        // 绘制外部边框
        canvas.drawRect(STARTX, STARTY, getWidth() - STARTX, getHeight() - STARTY, paint);
        // 画列分割线
        for (int i = 1; i < mCol; i++) {
            canvas.drawLine((getWidth() / mCol) * i, STARTY, (getWidth() / mCol) * i, getHeight() - STARTY, paint);
        }
        // 画行分割线
        for (int j = 1; j < mRow; j++) {
            canvas.drawLine(STARTX, (getHeight() / mRow) * j, getWidth() - STARTX, (getHeight() / mRow) * j, paint);
        }
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int x = STARTX + BORDER;
        int y = STARTY + BORDER;
        int i = 0;
        int count = getChildCount();
        for (int j = 0; j < count; j++) {
            View child = getChildAt(j);
            child.layout(x, y, x + getWidth() / mCol - BORDER * 2, y + getHeight() / mRow - BORDER * 2);
            if (i >= (mCol - 1)) {
                i = 0;
                x = STARTX + BORDER;
                y += getHeight() / mRow;
            } else {
                i++;
                x += getWidth() / mCol;
            }
        }
    }

    public void setRow(int row) {
        this.mRow = row;
    }

    public void setCol(int col) {
        this.mCol = col;
    }

}

/*
// 然后我们在Activity中使用我们的控件：
public class MainActivity extends Activity implements OnClickListener{
    private Button btn;
    private EditText row;
    private EditText col;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btn = (Button)findViewById(R.id.button1);
        row = (EditText)findViewById(R.id.editRow);
        col = (EditText)findViewById(R.id.editCol);
        row.setError("请输入小于20的整数");
        col.setError("请输入小于20的整数");
        btn.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        Bundle bun = new Bundle();
        if("".equals(row.getText().toString())){
            Toast.makeText(this, "行数不能为空", Toast.LENGTH_SHORT).show();
            return;
        }else if("".equals(col.getText().toString())){
            Toast.makeText(this, "列数不能为空", Toast.LENGTH_SHORT).show();
            return;
        }else{
            int rowNum = Integer.parseInt(row.getText().toString());
            int colNum = Integer.parseInt(col.getText().toString());
            bun.putInt("row", rowNum);
            bun.putInt("col", colNum);
            intent.setClass(MainActivity.this, TableActivity.class);
            intent.putExtras(bun);
            startActivity(intent);
        }
    }

}
*/

/*
public class TableActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        Bundle bun = intent.getExtras();
        int row = bun.getInt("row");
        int col = bun.getInt("col");
        TableView table = new TableView(this, row, col);
        setContentView(table);
    }
}
*/