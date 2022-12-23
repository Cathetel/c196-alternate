package com.example.rpettyc196.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rpettyc196.Database.Repository;
import com.example.rpettyc196.Entity.Assessment;
import com.example.rpettyc196.Entity.Course;
import com.example.rpettyc196.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CourseDetail extends AppCompatActivity {
    EditText editName;
    EditText editID;
    int termID;
    int courseID;
    String name;
    String start;
    String end;
    String status;
    String ciName;
    String ciPhone;
    String email;
    String note;
    Course course;
    Course currentCourse;
    int numCourses;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        editName = findViewById(R.id.courseName);
        editID = findViewById(R.id.courseID);
        termID = getIntent().getIntExtra("termID", -1);
        courseID = getIntent().getIntExtra("courseID", -1);
        name = getIntent().getStringExtra("courseName");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        status = getIntent().getStringExtra("status");
        ciName = getIntent().getStringExtra("ciName");
        ciPhone = getIntent().getStringExtra("ciPhone");
        email = getIntent().getStringExtra("email");
        note = getIntent().getStringExtra("note");
        editID.setText(courseID);
        editName.setText(name);

        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository = new Repository(getApplication());
        List<Assessment> filteredAssessments = new ArrayList<>();
        for (Assessment a : repository.getAllAssessments()) {
            if (a.getCourseID() == courseID) filteredAssessments.add(a);
        }
        assessmentAdapter.setAssessment(filteredAssessments);

        Button button = findViewById(R.id.saveCourse);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (courseID == -1) {
                    course = new Course(0, editID.getId(), editName.getText().toString(), "01-01-01", "01-02-01", "Pending", "Bill Jing", "555-555-5555", "email@wgu.edu", "note");
                    repository.insert(course);

                } else {
                    course = new Course(0, editID.getId(), editName.getText().toString(), "01-01-01", "01-02-01", "Pending", "Bill Jing", "555-555-5555", "email@wgu.edu", "note");
                    repository.update(course);

                }
            }
        });
        FloatingActionButton fab = findViewById(R.id.courseActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetail.this, CourseDetail.class);
                intent.putExtra("courseID", courseID);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {

        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> filteredCourses = new ArrayList<>();
        for (Course c : repository.getAllCourses()) {
            if (c.getTermID() == termID) filteredCourses.add(c);
        }
        courseAdapter.setCourse(filteredCourses);

        //Toast.makeText(ProductDetails.this,"refresh list",Toast.LENGTH_LONG).show();
    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.delete, menu);
//        return true;
//    }

//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//
//            case R.id.deleteproduct:
//                for (Product prod : repository.getAllProducts()) {
//                    if (prod.getProductID() == id) currentProduct = prod;
//                }
//
//                numParts = 0;
//                for (Part part : repository.getAllParts()) {
//                    if (part.getProductID() == id) ++numParts;
//                }
//
//                if (numParts == 0) {
//                    repository.delete(currentProduct);
//                    Toast.makeText(ProductDetails.this, currentProduct.getProductName() + " was deleted", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(ProductDetails.this, "Can't delete a product with parts", Toast.LENGTH_LONG).show();
//                }
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//}

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "text from the note field");
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Message Title");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
//            case R.id.notify:
//                String dateFromScreen = editDate.getText().toString();
//                Date myDate = null;
//                try {
//                    myDate = sdf.parse(dateFromScreen);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                Long trigger = myDate.getTime();
                //Intent intent=new Intent(CourseDetail.this, MyReceiver.class);
                //intent.putExtra("key","messageIwantToSend");
                //PendingIntent sender=PendingIntent.getBroadcast(CourseDetail.this, MainActivity.numAlert++, intent,0);
                //AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
                // alarmManager.set(AlarmManager.RTC_WAKEUP,trigger,sender);
                //return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
