package com.feicui.youxi;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    final int[] backgrounds = new int[]{R.mipmap.photo0, R.mipmap.photo2,
            R.mipmap.photo4, R.mipmap.photo8, R.mipmap.photo16,
            R.mipmap.photo32, R.mipmap.photo64, R.mipmap.photo128,
            R.mipmap.photo256, R.mipmap.photo512, R.mipmap.photo1024,
            R.mipmap.photo2048, R.mipmap.photo4096, R.mipmap.photo8192,
            R.mipmap.photo16384};
    final int[][] cardsId = new int[][]{
            {R.id.card00, R.id.card01, R.id.card02, R.id.card03},
            {R.id.card10, R.id.card11, R.id.card12, R.id.card13},
            {R.id.card20, R.id.card21, R.id.card22, R.id.card23},
            {R.id.card30, R.id.card31, R.id.card32, R.id.card33}};
    private int[][] matrix = new int[4][4];
    int score = 0;
    int bestScore = 0;
    boolean currentState = true;
    Button newGame = null;
    TextView scoreText = null;
    TextView bestScoreText = null;
    GestureDetector detector = null;
    private int mCard;
    private int mScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();
        setOnListener();
        gameStart();
    }

    private void initialization() {
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                matrix[i][j] = 0;


        newGame = (Button) findViewById(R.id.newgame);
        scoreText = (TextView) findViewById(R.id.scoretext);
        bestScoreText = (TextView) findViewById(R.id.bestscoretext);
        detector = new GestureDetector(MainActivity.this, this);

        MyDatabase helper = new MyDatabase(MainActivity.this, "myrecord", null,
                1);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from myrecord", null);
        if (cursor.moveToNext()) {

            bestScore = cursor.getInt(cursor.getColumnIndex("bestscore"));
            bestScoreText.setText(bestScore + "");
        } else {

            String sql = "insert into myrecord(bestscore) values(0)";
            db.execSQL(sql);
            bestScoreText.setText(bestScore + "");
        }
        db.close();

        scoreText.setText(score + "");
    }

    private void leftShift() {
        int[][] temp = new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0},
                {0, 0, 0, 0}, {0, 0, 0, 0}};
        int nums = 0;
        int ezn = 16;
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                if (matrix[i][j] != 0) {
                    temp[i][nums] = matrix[i][j];
                    ++nums;
                }
            }
            nums = 0;
        }
        for (int i = 0; i < 4; ++i) {
            if ((temp[0][i] != 0) && (temp[0][i] == temp[1][i])) {
                temp[0][i] = temp[0][i] << 1;
                score += temp[0][i];
                temp[1][i] = 0;
                if ((temp[2][i] != 0) && (temp[2][i] == temp[3][i])) {
                    temp[2][i] = temp[2][i] << 1;
                    score += temp[2][i];
                    temp[3][i] = 0;
                }
            } else {
                if ((temp[1][i] != 0) && (temp[1][i] == temp[2][i])) {
                    temp[1][i] = temp[1][i] << 1;
                    score += temp[1][i];
                    temp[2][i] = 0;
                } else {
                    if ((temp[2][i] != 0) && (temp[2][i] == temp[3][i])) {
                        temp[2][i] = temp[2][i] << 1;
                        score += temp[2][i];
                        temp[3][i] = 0;
                    }
                }
            }
        }
        for (int i = 0; i < 4; ++i)
            for (int j = 0; j < 4; ++j)
                matrix[i][j] = 0;
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                if (temp[j][i] != 0) {
                    matrix[nums][i] = temp[j][i];
                    ++nums;
                    --ezn;
                }
            }
            nums = 0;
        }
        if (ezn == 0) {
            currentState = false;
        } else {
            // �������һ�ſ�Ƭ
            Random rand = new Random();
            int where = rand.nextInt(ezn) + 1;
            for (int count = 0, i = 0; i < 4; ++i)
                for (int j = 0; j < 4; ++j) {
                    if (matrix[i][j] == 0)
                        count++;
                    if (count == where) {
                        matrix[i][j] = getCard();
                        return;

                    }
                }
        }
    }

    private void rightShift() {
        int[][] temp = new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0},
                {0, 0, 0, 0}, {0, 0, 0, 0}};
        int nums = 3;
        int ezn = 16;

        for (int i = 0; i < 4; ++i) {
            for (int j = 3; j >= 0; --j) {
                if (matrix[i][j] != 0) {
                    temp[i][nums] = matrix[i][j];
                    --nums;
                }
            }
            nums = 3;
        }
        for (int i = 0; i < 4; ++i) {
            if ((temp[i][3] != 0) && (temp[i][3] == temp[i][2])) {
                temp[i][3] = temp[i][3] << 1;
                score += temp[i][3];
                temp[i][2] = 0;
                if ((temp[i][1] != 0) && (temp[i][1] == temp[i][0])) {
                    temp[i][1] = temp[i][1] << 1;
                    score += temp[i][1];
                    temp[i][0] = 0;
                }
            } else {
                if ((temp[i][2] != 0) && (temp[i][2] == temp[i][1])) {
                    temp[i][2] = temp[i][2] << 1;
                    score += temp[i][2];
                    temp[i][1] = 0;
                } else {
                    if ((temp[i][1] != 0) && (temp[i][1] == temp[i][0])) {
                        temp[i][1] = temp[i][1] << 1;
                        score += temp[i][1];
                        temp[i][0] = 0;
                    }
                }
            }
        }

        for (int i = 0; i < 4; ++i)
            for (int j = 0; j < 4; ++j)
                matrix[i][j] = 0;

        for (int i = 0; i < 4; ++i) {
            for (int j = 3; j >= 0; --j) {
                if (temp[i][j] != 0) {
                    matrix[i][nums] = temp[i][j];
                    --nums;
                    --ezn;
                }
            }
            nums = 3;
        }
        if (ezn == 0) {
            currentState = false;
        } else {
            // �������һ�ſ�Ƭ
            Random rand = new Random();
            int where = rand.nextInt(ezn) + 1;
            for (int count = 0, i = 0; i < 4; ++i)
                for (int j = 0; j < 4; ++j) {
                    if (matrix[i][j] == 0)
                        count++;
                    if (count == where) {
                        matrix[i][j] = getCard();
                        return;
                    }
                }
        }
    }

    private void upShift() {
        int[][] temp = new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0},
                {0, 0, 0, 0}, {0, 0, 0, 0}};
        int nums = 0;
        int ezn = 16;
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                if (matrix[j][i] != 0) {
                    temp[nums][i] = matrix[j][i];
                    ++nums;
                }
            }
            nums = 0;
        }

        for (int i = 0; i < 4; ++i) {
            if ((temp[0][i] != 0) && (temp[0][i] == temp[1][i])) {
                temp[0][i] = temp[0][i] << 1;
                score += temp[0][i];
                temp[1][i] = 0;
                if ((temp[2][i] != 0) && (temp[2][i] == temp[3][i])) {
                    temp[2][i] = temp[2][i] << 1;
                    score += temp[2][i];
                    temp[3][i] = 0;
                }
            } else {
                if ((temp[1][i] != 0) && (temp[1][i] == temp[2][i])) {
                    temp[1][i] = temp[1][i] << 1;
                    score += temp[1][i];
                    temp[2][i] = 0;
                } else {
                    if ((temp[2][i] != 0) && (temp[2][i] == temp[3][i])) {
                        temp[2][i] = temp[2][i] << 1;
                        score += temp[2][i];
                        temp[3][i] = 0;
                    }
                }
            }
        }

        for (int i = 0; i < 4; ++i)
            for (int j = 0; j < 4; ++j)
                matrix[i][j] = 0;

        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                if (temp[j][i] != 0) {
                    matrix[nums][i] = temp[j][i];
                    ++nums;
                    --ezn;
                }
            }
            nums = 0;
        }

        if (ezn == 0) {
            currentState = false;
        } else {
            Random rand = new Random();
            int where = rand.nextInt(ezn) + 1;
            for (int count = 0, i = 0; i < 4; ++i)
                for (int j = 0; j < 4; ++j) {
                    if (matrix[i][j] == 0)
                        count++;
                    if (count == where) {
                        matrix[i][j] = getCard();
                        return;

                    }
                }
        }
    }

    private void downShift() {
        int[][] temp = new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0},
                {0, 0, 0, 0}, {0, 0, 0, 0}};
        int nums = 3;
        // Equal zero numbers����0������������Ϊ�����������һ�ſ�Ƭ��׼��
        int ezn = 16;
        for (int i = 0; i < 4; ++i) {
            for (int j = 3; j >= 0; --j) {
                if (matrix[j][i] != 0) {
                    temp[nums][i] = matrix[j][i];
                    --nums;
                }
            }
            nums = 3;
        }

        // ��temp����ִ�б�Ҫ����ӵ��߼�
        for (int i = 0; i < 4; ++i) {
            if ((temp[3][i] != 0) && (temp[3][i] == temp[2][i])) {
                temp[3][i] = temp[3][i] << 1;
                score += temp[3][i];
                temp[2][i] = 0;
                if ((temp[1][i] != 0) && (temp[1][i] == temp[0][i])) {
                    temp[1][i] = temp[1][i] << 1;
                    score += temp[1][i];
                    temp[0][i] = 0;
                }
            } else {
                if ((temp[2][i] != 0) && (temp[2][i] == temp[1][i])) {
                    temp[2][i] = temp[2][i] << 1;
                    score += temp[2][i];
                    temp[1][i] = 0;
                } else {
                    if ((temp[1][i] != 0) && (temp[1][i] == temp[0][i])) {
                        temp[1][i] = temp[1][i] << 1;
                        score += temp[1][i];
                        temp[0][i] = 0;
                    }
                }
            }
        }

        // ��matrix�����ʼ��
        for (int i = 0; i < 4; ++i)
            for (int j = 0; j < 4; ++j)
                matrix[i][j] = 0;

        // ��temp�������¸�ֵ��matrix���飬���Ұ�temp����ȫ��Ԫ��ѹ�����±�
        for (int i = 0; i < 4; ++i) {
            for (int j = 3; j >= 0; --j) {
                if (temp[j][i] != 0) {
                    matrix[nums][i] = temp[j][i];
                    --nums;
                    --ezn;
                }
            }
            nums = 3;
        }

        // ���������Ȼȫ����Ϊ0��ʾ��Ϸ����
        if (ezn == 0) {
            currentState = false;
        } else {
            // �������һ�ſ�Ƭ
            Random rand = new Random();
            int where = rand.nextInt(ezn) + 1;
            for (int count = 0, i = 0; i < 4; ++i)
                for (int j = 0; j < 4; ++j) {
                    if (matrix[i][j] == 0)
                        count++;
                    if (count == where) {
                        matrix[i][j] = getCard();
                        return;

                    }
                }
        }
    }

    private void setOnListener() {
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                for (int i = 0; i < 4; i++)
                    for (int j = 0; j < 4; j++)
                        matrix[i][j] = 0;
                showMatrix();
                score = 0;
                setScore(0);
                gameStart();
            }
        });
    }

    public void setScore(int score) {
        scoreText.setText(score + "");
        if (score > bestScore) {
            MyDatabase helper = new MyDatabase(MainActivity.this, "myrecord",
                    null, 1);
            SQLiteDatabase db = helper.getWritableDatabase();

            String sql = "update myrecord set bestscore = " + score;
            db.execSQL(sql);
            db.close();

            bestScore = score;
            bestScoreText.setText(bestScore + "");
        }
    }

    public int getCard() {
        Random rand = new Random();
        int num = rand.nextInt(5);
        if (num < 4)
            return 2;
        else
            return 4;
    }

    private void gameStart() {
        Random rand = new Random();
        int num = 0;
        while (num < 2) {
            int row = rand.nextInt(4);
            int column = rand.nextInt(4);
            if (matrix[row][column] == 0) {
                TextView card = (TextView) findViewById(cardsId[row][column]);
                int cardNum = getCard();
                card.setBackgroundResource(backgrounds[cardNum / 2]);
                card.setText(cardNum + "");
                matrix[row][column] = cardNum;
                num++;
            }
        }
        showMatrix();
    }

    private void showMatrix() {
        for (int i = 0; i < 4; ++i)
            for (int j = 0; j < 4; ++j) {
                TextView card = (TextView) findViewById(cardsId[i][j]);
                // �õ����ֶ�Ӧ�ı���ͼƬ
                if (matrix[i][j] == 0) {
                    card.setBackgroundResource(backgrounds[0]);
                    card.setText("");
                } else {
                    int backgroundNum = (int) (Math.log(matrix[i][j]) / Math
                            .log(2));
                    card.setBackgroundResource(backgrounds[backgroundNum]);
                    card.setText(matrix[i][j] + "");
                }
            }
    }

    private void check() {
        if (currentState)
            return;
        else {
            if (score == bestScore) {
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View resultView = inflater.inflate(R.layout.newrecordview, null);
                TextView tv = ((TextView) (resultView.findViewById(R.id.newrecordresultscore)));
                tv.setText(bestScore + "");
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("")
                        .setView(resultView)
                        .setPositiveButton("", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (int i = 0; i < 4; i++)
                                    for (int j = 0; j < 4; j++)
                                        matrix[i][j] = 0;
                                showMatrix();
                                score = 0;
                                setScore(0);
                                gameStart();
                            }
                        })
                        .setNegativeButton("ȡ��", null)
                        .show();
            } else {
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View normalResultView = inflater.inflate(R.layout.normalview, null);
                TextView normalScore =
                        ((TextView) (normalResultView.findViewById(R.id.normalresultscore)));
                TextView normalBestScore =
                        ((TextView) (normalResultView.findViewById(R.id.normalresultbestrecord)));
                normalScore.setText(score + "");
                normalBestScore.setText(bestScore + "");
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("���ź�")
                        .setView(normalResultView)
                        .setPositiveButton("����һ��", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (int i = 0; i < 4; i++)
                                    for (int j = 0; j < 4; j++)
                                        matrix[i][j] = 0;
                                showMatrix();
                                score = 0;
                                setScore(0);
                                gameStart();
                            }
                        })
                        .setNegativeButton("ȡ��", null)
                        .show();
            }
            currentState = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.shiftgame) {
            Intent intent = new Intent(MainActivity.this, ShiftGame.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() - e2.getX() > 50) {
            leftShift();
            showMatrix();
            setScore(score);
            check();
        } else if (e2.getX() - e1.getX() > 50) {
            rightShift();
            showMatrix();
            setScore(score);
            check();
        } else if (e1.getY() - e2.getY() > 40) {
            upShift();
            showMatrix();
            setScore(score);
            check();
        } else if (e2.getY() - e1.getY() > 40) {
            downShift();
            showMatrix();
            setScore(score);
            check();
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        boolean state = detector.onTouchEvent(e);
        if (state)
            return true;
        else
            return super.dispatchTouchEvent(e);
    }
}
