package com.feicui.youxi;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

/**
 * Created by AAAAA on 2016/8/5.
 */
public class ShiftGame extends Activity implements GestureDetector.OnGestureListener {
    int[] nums = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
            0 };
    Button[] bns = new Button[16];
    Button freshData = null;
    Button askhelp = null;
    boolean result = false;
    GestureDetector detector = null;
    private int helpTimes = 0;
    final int flipLength = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_game);
        final Random rand=new Random();
        detector=new GestureDetector(ShiftGame.this,this);
        for (int i = 0; i < 15; i++) {
            int j = rand.nextInt(15);
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
            bns[0] = (Button) findViewById(R.id.bn0);
            bns[1] = (Button) findViewById(R.id.bn1);
            bns[2] = (Button) findViewById(R.id.bn2);
            bns[3] = (Button) findViewById(R.id.bn3);
            bns[4] = (Button) findViewById(R.id.bn4);
            bns[5] = (Button) findViewById(R.id.bn5);
            bns[6] = (Button) findViewById(R.id.bn6);
            bns[7] = (Button) findViewById(R.id.bn7);
            bns[8] = (Button) findViewById(R.id.bn8);
            bns[9] = (Button) findViewById(R.id.bn9);
            bns[10] = (Button) findViewById(R.id.bn10);
            bns[11] = (Button) findViewById(R.id.bn11);
            bns[12] = (Button) findViewById(R.id.bn12);
            bns[13] = (Button) findViewById(R.id.bn13);
            bns[14] = (Button) findViewById(R.id.bn14);
            bns[15] = (Button) findViewById(R.id.bn15);
            freshData = (Button) findViewById(R.id.refresh);
            askhelp = (Button) findViewById(R.id.askhelp);
            for (int i = 0; i < 15; i++)
                bns[i].setText(String.valueOf(nums[i]));
            bns[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    action(0, 1, 4);
                    check();
                }
            });
        bns[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action(1, 0, 2, 5);
                check();
            }
        });
        bns[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action(2, 1, 3, 6);
                check();
            }
        });

        bns[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action(3,2,7);
                check();
            }
        });

        bns[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action(4, 0, 5, 8);
                check();
            }
        });

        bns[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action(5, 1, 4, 6, 9);
                check();
            }
        });

        bns[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action(6, 2, 5, 7, 10);
                check();
            }
        });

        bns[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action(7, 3, 6, 11);
                check();
            }
        });
        bns[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action(8, 4, 9, 12);
                check();
            }
        });

        bns[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action(9, 5, 8, 10, 13);
                check();
            }
        });

        bns[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action(10, 6, 9, 11, 14);
                check();
            }
        });
        bns[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action(11, 7, 10, 15);
                check();
            }
        });
        bns[12].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action(12, 8, 13);
                check();
            }
        });
        bns[13].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action(13, 9, 12, 14);
                check();
            }
        });
        bns[14].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action(14, 10, 13, 15);
                check();
            }
        });
        bns[15].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action(15, 11, 14);
                check();
            }
        });
        freshData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 15; i++) {
                    if (nums[i] == 0)
                        bns[i].setBackgroundResource(R.mipmap.blank_7);
                    nums[i] = i + 1;
                }
                nums[15] = 0;
                for (int i = 0; i < 15; i++) {
                    int j = rand.nextInt(15);
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                }
                for (int i = 0; i < 15; i++)
                    bns[i].setText(String.valueOf(nums[i]));
                bns[15].setText("");
                bns[15].setBackgroundResource(R.mipmap.blank_1);
                helpTimes = 0;
            }
        });
        askhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (helpTimes == 0) {
                    LayoutInflater inflater = LayoutInflater
                            .from(ShiftGame.this);
                    final View askHelpView = inflater.inflate(R.layout.askhelp,
                            null);
                    new AlertDialog.Builder(ShiftGame.this)
                            .setTitle("��������")
                            .setView(askHelpView)
                            .setPositiveButton("����",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {
                                            int num1 = Integer
                                                    .valueOf(((EditText) (askHelpView
                                                            .findViewById(R.id.helpnum1)))
                                                            .getText()
                                                            .toString());
                                            int num2 = Integer
                                                    .valueOf(((EditText) (askHelpView
                                                            .findViewById(R.id.helpnum2)))
                                                            .getText()
                                                            .toString());
                                            if ((num1 > 0 && num1 <= 16)
                                                    && (num2 > 0 && num2 <= 16)) {
                                                int swap1 = 0, swap2 = 0;
                                                for (int i = 0; i < 16; i++) {
                                                    if (nums[i] == num1)
                                                        swap1 = i;
                                                    if (nums[i] == num2)
                                                        swap2 = i;
                                                }
                                                bns[swap1].setText(String
                                                        .valueOf(num2));
                                                bns[swap2].setText(String
                                                        .valueOf(num1));
                                                int temp = nums[swap1];
                                                nums[swap1] = nums[swap2];
                                                nums[swap2] = temp;
                                                helpTimes++;
                                            }
                                        }
                                    }).setNegativeButton("ȡ��", null).show();
                } else {
                    new AlertDialog.Builder(ShiftGame.this).setTitle("��ʾ")
                            .setMessage("����ʹ�ù�һ�ΰ�����������ʹ�ð���")
                            .setPositiveButton("ȷ��", null).show();
                }
            }
        });
    }
    boolean upCase(int where){
        if (where == 0 || where == 1 || where == 2 || where == 3)
            return false;
        else if (nums[where - 4] == 0) {
            swap(where, where - 4);
            return true;
        } else
            return false;
    }
    boolean downCase(int where){
        if (where == 12 || where == 13 || where == 14 || where == 15)
            return false;
        else if (nums[where + 4] == 0) {
            swap(where, where + 4);
            return true;
        } else
            return false;
    }
    boolean leftCase(int where) {
        if (where % 4 == 0)
            return false;
        else if (nums[where - 1] == 0) {
            swap(where, where - 1);
            return true;
        } else
            return false;
    }

    // ���һ���
    boolean rightCase(int where) {
        if (where % 4 == 3)
            return false;
        else if (nums[where + 1] == 0) {
            swap(where, where + 1);
            return true;
        } else
            return false;
    }
    private void swap(int a,int b){
        bns[a].setText("");
        bns[a].setBackgroundResource(R.mipmap.photo2048);
        bns[b].setText(String.valueOf(nums[a]));
        bns[b].setBackgroundResource(R.mipmap.photo4096);
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }
    private void action(int a0, int a1, int a2) {
        if (nums[a1] != 0 && nums[a2] != 0)
            return;
        else if (nums[a1] == 0)
            swap(a0, a1);
        else if (nums[a2] == 0)
            swap(a0, a2);
    }
    private void action(int a0, int a1, int a2, int a3) {
        if (nums[a1] != 0 && nums[a2] != 0 && nums[a3] != 0)
            return;
        else if (nums[a1] == 0)
            swap(a0, a1);
        else if (nums[a2] == 0)
            swap(a0, a2);
        else if (nums[a3] == 0)
            swap(a0, a3);
    }
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }
    private void action(int a0, int a1, int a2, int a3, int a4) {
        if (nums[a1] != 0 && nums[a2] != 0 && nums[a3] != 0 && nums[a4] != 0)
            return;
        else if (nums[a1] == 0)
            swap(a0, a1);
        else if (nums[a2] == 0)
            swap(a0, a2);
        else if (nums[a3] == 0)
            swap(a0, a3);
        else if (nums[a4] == 0)
            swap(a0, a4);
    }
    private void check() {
        result = ((nums[0] == 1) && (nums[1] == 2) && nums[2] == 3 && (nums[3] == 4));
        if (result)
            result = (result && (nums[4] == 5) && (nums[5] == 6)
                    && nums[6] == 7 && (nums[7] == 8));
        else
            return;
        if (result)
            result = (result && (nums[8] == 9) && (nums[9] == 10)
                    && nums[10] == 11 && (nums[11] == 12));
        else
            return;
        if (result)
            result = (result && (nums[12] == 13) && (nums[13] == 14) && nums[14] == 15);
        if (result) {
            new AlertDialog.Builder(ShiftGame.this).setTitle("��ϲ")
                    .setMessage("You Win������\n").setPositiveButton("ȷ��", null)
                    .setIcon(getResources().getDrawable(R.mipmap.bomb))
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shift_game,menu);
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent arg1) {
        boolean state = detector.onTouchEvent(arg1);
        if (state)
            return true;
        else
            return super.dispatchTouchEvent(arg1);
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX , float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (e1.getX() - e2.getX() > flipLength) {
            for (int i = 0; i < 16; i++) {
                if (leftCase(i))
                    return true;
            }
        } else if (e2.getX() - e1.getX() > flipLength) {
            for (int i = 0; i < 16; i++) {
                if (rightCase(i))
                    return true;
            }
        } else if (e1.getY() - e2.getY() > flipLength - 25) {
            for (int i = 0; i < 16; i++) {
                if (upCase(i))
                    return true;
            }
        } else if (e2.getY() - e1.getY() > flipLength - 25) {
            for (int i = 0; i < 16; i++) {
                if (downCase(i))
                    return true;
            }
        } else
            return false;
        return false;
    }
}
