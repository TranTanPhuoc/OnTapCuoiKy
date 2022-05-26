package com.example.onthicuoiky;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onthicuoiky.dao.UserDao;
import com.example.onthicuoiky.database.AppdataBase;
import com.example.onthicuoiky.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText txtEmail,txtPassword;
    private DatabaseReference mDatabase;
    private UserDao userDAO;
    private List<User> users;
    private Button btnLogin,btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtEmail = findViewById(R.id.txtEmailLogin);
        txtPassword = findViewById(R.id.txtPassLogin);
        btnLogin = findViewById(R.id.btnSignIn_Login);
        btnRegister = findViewById(R.id.btnRegister_Login);
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //Room database
        userDAO = AppdataBase.getDatabase(this).UserDAO();
        getAllUserFromFirebase();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email  = txtEmail.getText().toString().trim();
                String passWord = txtPassword.getText().toString().trim();
                auth.signInWithEmailAndPassword(email,passWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = userDAO.findByEmail(email);
                            Intent intent = new Intent(MainActivity.this,ListMonHoc.class);
                            intent.putExtra("userId", user.getId());
                            startActivity(intent);
                            Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            finishAffinity();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterMain.class);
                startActivity(intent);
            }
        });
    }
    private void getAllUserFromFirebase() {

        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userDAO.delete();
                for(DataSnapshot sn : snapshot.getChildren()) {
                    User user = sn.getValue(User.class);
                    userDAO.insert(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}