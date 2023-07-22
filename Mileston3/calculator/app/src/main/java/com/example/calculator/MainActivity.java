package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.math.BigDecimal;


/**
 * Runs the main activity.
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView Resultview,screen;
    MaterialButton buttonC,buttonBrackOpen,buttonBrackClose;
    MaterialButton buttonDivide,buttonMultiply,buttonPlus,buttonMinus,buttonEquals;
    MaterialButton button0,button1,button2,button3,button4,button5,button6,button7,button8,button9;
    MaterialButton buttonAC,buttonDot;
    int digitCounter=0;
    boolean flag=true;

    /**
     * Initiate all the button during run.
     * @param savedInstanceState saved state
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resultview = findViewById(R.id.result_tv);
        screen = findViewById(R.id.screen);

        assignId(buttonC,R.id.button_c);
        assignId(buttonBrackOpen,R.id.button_open_bracket);
        assignId(buttonBrackClose,R.id.button_close_bracket);
        assignId(buttonDivide,R.id.button_divide);
        assignId(buttonMultiply,R.id.button_multiply);
        assignId(buttonPlus,R.id.button_plus);
        assignId(buttonMinus,R.id.button_minus);
        assignId(buttonEquals,R.id.button_equals);
        assignId(button0,R.id.button_0);
        assignId(button1,R.id.button_1);
        assignId(button2,R.id.button_2);
        assignId(button3,R.id.button_3);
        assignId(button4,R.id.button_4);
        assignId(button5,R.id.button_5);
        assignId(button6,R.id.button_6);
        assignId(button7,R.id.button_7);
        assignId(button8,R.id.button_8);
        assignId(button9,R.id.button_9);
        assignId(buttonAC,R.id.button_ac);
        assignId(buttonDot,R.id.button_dot);





    }

    /**
     * function to initiate each button.
     * @param btn buttons
     * @param id buttons ids
     */
    void assignId(MaterialButton btn,int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    /**
     * send the digits from the button pressed to calculate the result.
     * @param view: shows the result
     */
    @Override
    public void onClick(View view) {
        MaterialButton button =(MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = screen.getText().toString();


/**
 * shows pop up error if a single digit is pressed more than 10 times.
 */
        if(buttonText.length()>10 ){
            Snackbar mySnackbar = Snackbar.make(view,"Max Digits", 50);
            mySnackbar.show();
//            Resultview.setText("Overflow");
            return ;
        }


        /**
         * clears the result window when AC is pressed.
         */
        if(buttonText.equals("AC")){
            screen.setText("");
            Resultview.setText("0");
            digitCounter=0;
            return;
        }
        /**
         * shows the result when = button is pressed.
         */
        if(buttonText.equals("=")){
//            dataToCalculate = dataToCalculate+buttonText;
            screen.setText(Resultview.getText());
            return;
        }
        /**
         * clears the last digits when c button is pressed.
         */
        if(buttonText.equals("C")){
                if (dataToCalculate.length()==0) flag=false;
                if(flag==true){
                dataToCalculate = dataToCalculate.substring(0,dataToCalculate.length()-1);
                digitCounter--;}
                if (dataToCalculate.length()>0) flag=true;
                //if (dataToCalculate.length()==0) flag=false;
        }else{
            if (buttonText.equals("+") || buttonText.equals("-") || buttonText.equals("*") || buttonText.equals("/") || buttonText.equals("(") || buttonText.equals(")")){
                digitCounter=0;
            }
            else {
                digitCounter++;
            }
//            dataToCalculate = dataToCalculate+buttonText;
            System.out.println(digitCounter);
//
            dataToCalculate = dataToCalculate+buttonText;
        }


//        for(int i=0;i<dataToCalculate.length();i++){
//            if(Character.isDigit(dataToCalculate.charAt(i))){
//                digitCounter++;
//            }
//            if(Character.isSign(dataToCalculate.charAt(i)))
//        }
        if(digitCounter>10 ){
            Snackbar mySnackbar = Snackbar.make(view, "Maximum Digits", 800);
            mySnackbar.show();
//            Resultview.setText("Over");
            return;
        }


//        if(dataToCalculate.length()>10 ){
//            Snackbar mySnackbar = Snackbar.make(view, "Max Digits", 50);
//            mySnackbar.show();
////            Resultview.setText("Over");
//            return;
//        }
        screen.setText(dataToCalculate);

        String Answer = showResult(dataToCalculate);
//        if(Answer.equals("")){
//            Resultview.setText("0");
//        }



        if(Answer.endsWith("")){
            Resultview.setText("");
        }

//        if(Answer.length()==0 ){
//            Resultview.setText("0");
//        }

        /**
         * shows the result if there are no errors.
         */
        if(!Answer.equals("Err")){
            Resultview.setText(Answer);
        }


    }

    /**
     * Calculates the final result and take care of all the errors.
     * @param data:
     *        data is the calculated value
     * @return answer
     */
    String showResult(String data){
        try{
            Context context  = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String Answer =  context.evaluateString(scriptable,data,"Javascript",1,null).toString();
//            if(Answer.endsWith(".0")){
//                Answer = Answer.replace(".0","");
//            }
            double a = Double.parseDouble(Answer);
            double result =Math.round(a * 100) / 100d;
//            BigDecimal Ans = new BigDecimal(a);
//            MathContext mc = new MathContext(2);
//            Ans.round(mc);

//            DecimalFormat df = new DecimalFormat();
//            df.setMaximumFractionDigits(2);
//            df.setMinimumFractionDigits(0);
//            df.setGroupingUsed(false);
//
//            df.format(Ans);


//            Answer= Ans.toString();
//            int n= Answer.indexOf(".");
//            if(n!=-1){
//                Answer=Answer.substring(0,n+3);
//            }



            Answer= Double.toString(result);


//            if(Answer.length()>10 ){
//                //Resultview.setText("Over");
//                return "Overflow";
//            }
//            if(Answer.length()==0){
//                Answer.equals("0");
//            }

            if(Answer.indexOf('E')!=-1){
                double b = Double.parseDouble(Answer);
                BigDecimal big = new BigDecimal(a);

                Answer=big.toString();
            }



            /**
             * removes the decimal if the result is whole number.
             */
            if(Answer.endsWith(".0")){
                Answer = Answer.replace(".0","");
            }
//            BigDecimal Ans = new BigDecimal(Answer);
            return Answer;

        }catch (Exception e){
            return "Err";
        }
    }

}