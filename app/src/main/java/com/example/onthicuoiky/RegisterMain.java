package com.example.onthicuoiky;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class RegisterMain extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText txtEmail,txtPass,txtPassAgain;
    private Button btnRegister,btnLogin;
    private DatabaseReference mDatabase;
    private int i = 0;
    private UserDao userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_main);
        txtEmail = findViewById(R.id.txtEmailRegister);
        txtPass = findViewById(R.id.txtPassRegister);
        txtPassAgain = findViewById(R.id.txtPassAgainRegister);
        btnRegister = findViewById(R.id.btnRegisterMain);
        btnLogin = findViewById(R.id.btnSignIn_Register);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userDAO = AppdataBase.getDatabase(this).UserDAO();
        i = getLastUserFromFirebase();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString().trim();
                String pass = txtPass.getText().toString().trim();
                String passAgain = txtPassAgain.getText().toString().trim();
                if(pass.equalsIgnoreCase(passAgain)){
                    auth = FirebaseAuth.getInstance();
                    auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                saveUserInFirebase(email);//save email for user
                                Intent intent = new Intent(RegisterMain.this,MainActivity.class);
                                Toast.makeText(RegisterMain.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finishAffinity();
                            }
                            else{
                                Toast.makeText(RegisterMain.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterMain.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }
    private int getLastUserFromFirebase() {
        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userDAO.delete();
                for(DataSnapshot sn : snapshot.getChildren()) {
                    User user = sn.getValue(User.class);
                    userDAO.insert(user);
                }

                if(userDAO.getAll().size() == 0)
                    i = 1;
                else
                    i = userDAO.getAll().get(userDAO.getAll().size() - 1).getId() + 1;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return i;
    }


    private void saveUserInFirebase(String email) {
        // i created by func main
        User user = new User(i, email);
        mDatabase.child("users").child(user.getId() + "").setValue(user);
    }

}