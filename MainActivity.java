package com.example.jdbc_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    private static final String url="jdbc:mysql://192.168.43.235:3306/Mydb2";
    private static final String user="admin";
    private static final String pass="admin@123";

    EditText p1,d1;
    String p,d;
    CardView cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        p1=(EditText)findViewById(R.id.prn);
        d1=(EditText)findViewById(R.id.dob);

       // p=p1.getText().toString();
        //d=d1.getText().toString();

        Button b=(Button)findViewById(R.id.show);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mydb();
            }
        });
    }

    public void mydb()
    {
        TextView tv1=(TextView)this.findViewById(R.id.text_view1);
        TextView tv2=(TextView)this.findViewById(R.id.text_view2);
        TextView tv3=(TextView)this.findViewById(R.id.text_view3);
        TextView tv4=(TextView)this.findViewById(R.id.text_view4);
        TextView tv5=(TextView)this.findViewById(R.id.text_view5);

        try{

            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection(url,user,pass);

            //String result="Database connection success\n";
            String result1="";
            String result2="";
            String result3="";
            String result4="";
            String result5="";
            Statement st=con.createStatement();

            p=p1.getText().toString();
            d=d1.getText().toString();

            //String qstr="select c.subjectname,c.subjectcode,a.grade from Marks_Table a join Student_Master b on a.prn=b.prn join Subject_Master c on a.subjectcode=c.subjectcode join Grade_Master d on a.grade=d.grade where a.prn='"+p.trim()+"'";
            ResultSet ra=st.executeQuery("select c.subjectname,c.subjectcode,a.grade from Marks_Table a join Student_Master b on a.prn=b.prn join Subject_Master c on a.subjectcode=c.subjectcode join Grade_Master d on a.grade=d.grade where a.prn='"+p.trim()+"' and b.dob='"+d.trim()+"'");
            //ResultSet ra1=st.executeQuery("select e.prn,e.fname,e.mname,e.lname from `Student_Master` e where e.prn='$prn'");

            if(ra==null)
            {
                result4=result4+"Enter correct PRN or DOB";
            }
            else {

                ResultSetMetaData rand = ra.getMetaData();
                //ResultSetMetaData rand1 = ra1.getMetaData();

                //result4=result4+ra1.getString(1);
               // result5=result5+ra1.getString(2)+" "+ra1.getString(3)+" "+ra1.getString(4);

                //result4= result4+rand.getColumnName(1)+": "+ra.getString(1)+"\n";
                //result5= result5+rand.getColumnName(2)+": "+ra.getString(2)+"\n";//+ra.getString(3)+ra.getString(4)+"\n";
                result1 = result1 + "Subject Name"+"\n";//rand.getColumnName(1)+"\n";
                result2 = result2+"Subject Code"+"\n";//rand.getColumnName(2)+"\n";
                result3 = result3+"Grade"+"\n";//rand.getColumnName(3)+"\n";


                while (ra.next()) {

                    result1 = result1 + ra.getString(1)+"\n";
                    result2=result2+ra.getString(2)+"\n";
                    result3=result3+ra.getString(3)+"\n";
                }
                //tv4.setText(result4);
                //tv5.setText(result5);
                tv1.setText(result1);
                tv2.setText(result2);
                tv3.setText(result3);
            }

        }catch(Exception e){

            e.printStackTrace();
            tv1.setText(e.toString());
        }
    }
}
