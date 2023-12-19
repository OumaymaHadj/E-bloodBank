package hadj.tn.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

import hadj.tn.test.model.Role;
import hadj.tn.test.model.User;
import hadj.tn.test.util.API;
import hadj.tn.test.util.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {


    EditText editTextEmail, editTextPhone, editTextPassword, editTextUsername;
    soup.neumorphism.NeumorphButton signUp;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        initializeComponents();
        backToSignIn();
        register();


    }

    private void initializeComponents() {

        editTextEmail =  findViewById(R.id.email);
        editTextPhone =  findViewById(R.id.phone);
        editTextPassword =  findViewById(R.id.password);
        editTextUsername =  findViewById(R.id.username);
        signUp = findViewById(R.id.signup);

    }

    private void register() {

        RetrofitClient retrofitClient = new RetrofitClient();
        API userApi = retrofitClient.getRetrofit().create(API.class);

        signUp.setOnClickListener(View -> {
            email = String.valueOf(editTextEmail.getText());
            String phone = String.valueOf(editTextPhone.getText());
            String password = String.valueOf(editTextPassword.getText());
            String username = String.valueOf(editTextUsername.getText());

            Boolean check = validationInfo(email, phone, password, username);
            if (check) {

                User user = new User();
                user.setEmail(email);
                user.setPhone(phone);
                user.setPassword(password);
                user.setUsername(username);

                userApi.createUser(user)
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                if (response.code() == 400) {
                                    Toast.makeText(SignUpActivity.this, "Email already taken", Toast.LENGTH_LONG).show();
                                }
                                if (response.code() == 406) {
                                    Toast.makeText(SignUpActivity.this, "Username already taken", Toast.LENGTH_LONG).show();
                                } else if (response.code() == 200) {
                                    Toast.makeText(SignUpActivity.this, "Save successful", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(SignUpActivity.this, CheckEmailActivity.class);
                                    //intent.putExtra("email",email);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(SignUpActivity.this, "Save failed", Toast.LENGTH_LONG).show();

                            }
                        });
            }
        });
    }

    private void backToSignIn() {
        TextView toSignIn = findViewById(R.id.backToSignIn);
        toSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, Sign_InActivity.class);
                startActivity(intent);
            }
        });
    }


    private Boolean validationInfo(String email, String phone, String password, String username) {

        if (username.length() == 0) {
            editTextUsername.requestFocus();
            editTextUsername.setError("Username cannot be empty");
            return false;

        } else if (!username.matches("([a-zA-Z]+(\\s)*(\\s[a-zA-Z]+)*)+")) {
            editTextUsername.requestFocus();
            editTextUsername.setError("Enter only alphabetic characters and spaces");
            return false;
        } else if (email.length() == 0) {
            editTextEmail.requestFocus();
            editTextEmail.setError("Email cannot be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.requestFocus();
            editTextEmail.setError("Enter valid email");
            return false;
        } else if (phone.length() == 0) {
            editTextPhone.requestFocus();
            editTextPhone.setError("Phone cannot be empty");
            return false;
        } else if (!phone.matches("[0-9]{8}$")) {
            editTextPhone.requestFocus();
            editTextPhone.setError("Phone number has 8 digits");
            return false;
        } else if (password.length() <= 5) {
            editTextPassword.requestFocus();
            editTextPassword.setError("Minimum 6 characters required");
            return false;
        } else return true;
    }

}