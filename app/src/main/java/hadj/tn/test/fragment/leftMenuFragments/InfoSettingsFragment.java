package hadj.tn.test.fragment.leftMenuFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import hadj.tn.test.model.User;
import hadj.tn.test.R;
import hadj.tn.test.util.API;
import hadj.tn.test.util.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoSettingsFragment extends Fragment {

    TextView textViewName,textViewPhone,textViewBloodGroup,textViewRegion;
    TextView textViewEmail;
    ImageView imageProfile,imageView;
    String email,token, name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RetrofitClient retrofitClient = new RetrofitClient();
        API userApi = retrofitClient.getRetrofit().create(API.class);

        SharedPreferences preferences = getActivity().getSharedPreferences("AYACHNI", Context.MODE_PRIVATE);
        String retrievedToken  = preferences.getString("TOKEN",null);
        name = preferences.getString("USERNAME",null);
        token = "Bearer "+retrievedToken;


        getInfoUser(view,userApi,name,token);
        editUserName(view,userApi,email,token);
        editEmail(view,userApi,name,token);
        editPhone(view,userApi,name,token);
        editBloodGroup(view,userApi,name,token);
        ediRegion(view,userApi,name,token);
        selectImageFromGallery(view);

        Button deleteImage = view.findViewById(R.id.deleteProfilePhoto);
        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageProfile.setImageDrawable(getResources().getDrawable(R.drawable.profile, getActivity().getTheme()));
            }
        });

    }



    private void editPhone(View view, API userApi, String name, String token) {

            ImageView editPhone = view.findViewById(R.id.editPhone);
            editPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder dialogEditPhone = new AlertDialog.Builder(getActivity());

                    final View customLayout
                            = getLayoutInflater()
                            .inflate(
                                    R.layout.custom_dialog,
                                    null);

                    dialogEditPhone.setView(customLayout);

                    AlertDialog dialog = dialogEditPhone.create();
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();

                    Button ok = customLayout.findViewById(R.id.okEditPhone);
                    Button cancel = customLayout.findViewById(R.id.cancelEditPhone);
                    EditText editTextPhone = customLayout.findViewById(R.id.editTextPhone);

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String myPhone = editTextPhone.getText().toString();

                            if (myPhone.length() == 0) {
                                textViewPhone.setText(textViewPhone.getText());
                            } else if(!myPhone.matches("[0-9]{8}$")) {
                                textViewPhone.setText(textViewPhone.getText());
                                Toast.makeText(getActivity(), "Enter valid phone", Toast.LENGTH_LONG).show();
                            } else {
                                textViewPhone.setText(myPhone);

                                    User user = new User();
                                    user.setUsername(name);
                                    user.setPhone(myPhone);
                                    userApi.updateUser(token,user)
                                            .enqueue(new Callback<ResponseBody>() {
                                                @Override
                                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                    if (response.code() == 200) {
                                                        Toast.makeText(getActivity(), "Phone successfully updated", Toast.LENGTH_LONG).show();
                                                        dialog.cancel();
                                                    }
                                                    else Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
                                                }
                                                @Override
                                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();

                                                }
                                            });
                                }


                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });

                }
            });

    }
    private void ediRegion(View view, API userApi,String name, String token){
        ImageView editRegion = view.findViewById(R.id.editRegion);
        editRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void editEmail(View view, API userApi, String name, String token) {

        ImageView editEmail = view.findViewById(R.id.editEmail);
        editEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogEditEmail = new AlertDialog.Builder(getActivity());

                final View customLayout
                        = getLayoutInflater()
                        .inflate(
                                R.layout.custom_dialog_email,
                                null);

                dialogEditEmail.setView(customLayout);

                AlertDialog dialog = dialogEditEmail.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                Button ok = customLayout.findViewById(R.id.okEditEmail);
                Button cancel = customLayout.findViewById(R.id.cancelEditEmail);
                EditText editTextEmail = customLayout.findViewById(R.id.editTextEmail);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String myEmail = editTextEmail.getText().toString();

                        if (myEmail.length() == 0) {
                            textViewEmail.setText(textViewEmail.getText());
                        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            textViewEmail.setText(textViewEmail.getText());
                            Toast.makeText(getActivity(), "Enter only alphabetic characters and spaces", Toast.LENGTH_LONG).show();
                        } else {
                            textViewEmail.setText(myEmail);

                                User user = new User();
                                user.setUsername(name);
                                user.setEmail(myEmail);
                                userApi.updateUser(token,user)
                                        .enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                if (response.code() == 200) {
                                                    Toast.makeText(getActivity(), "Email successfully updated", Toast.LENGTH_LONG).show();
                                                    dialog.cancel();
                                                }
                                                else Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
                                            }
                                            @Override
                                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();

                                            }
                                        });
                            }
                        }


                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }
        });

}
    private void selectImageFromGallery(View view) {

        Button buttonEditPhoto = view.findViewById(R.id.editProfilePhoto);
        imageProfile = view.findViewById(R.id.profilePhoto);

        buttonEditPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
    }

    private void editUserName(View view,API userApi, String email, String token) {

        ImageView editName = view.findViewById(R.id.editName);
        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogEditUsername = new AlertDialog.Builder(getActivity());

                final View customLayout
                        = getLayoutInflater()
                        .inflate(
                                R.layout.custom_dialog_name,
                                null);

                dialogEditUsername.setView(customLayout);

                AlertDialog dialog = dialogEditUsername.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                Button ok = customLayout.findViewById(R.id.okEditUsername);
                Button cancel = customLayout.findViewById(R.id.cancelEditUsername);
                EditText editTextUsername = customLayout.findViewById(R.id.editTextUsername);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String myUsername = editTextUsername.getText().toString();

                        if (myUsername.length() == 0) {
                            textViewName.setText(textViewName.getText());
                        } else if (!myUsername.matches("([a-zA-Z]+(\\s)*(\\s[a-zA-Z]+)*)+")) {
                            textViewName.setText(textViewName.getText());
                            Toast.makeText(getActivity(), "Enter only alphabetic characters and spaces", Toast.LENGTH_LONG).show();
                        } else {
                            textViewName.setText(myUsername);

                                User user = new User();
                                user.setEmail(email);
                                user.setUsername(myUsername);
                                userApi.updateUser(token,user)
                                        .enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                if (response.code() == 200) {
                                                    Toast.makeText(getActivity(), "Username tbadal", Toast.LENGTH_LONG).show();
                                                    dialog.cancel();
                                                }
                                                else Toast.makeText(getActivity(), "Username matbadalch", Toast.LENGTH_LONG).show();
                                            }
                                            @Override
                                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                Toast.makeText(getActivity(), "cvppppppp", Toast.LENGTH_LONG).show();

                                            }
                                        });
                            }
                        }


                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }
        });
    }

    private void editBloodGroup(View view,API userApi, String name, String token) {

        ImageView editBloodGroup = view.findViewById(R.id.editBloodGroup);
        editBloodGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogEditBloodGroup = new AlertDialog.Builder(getActivity());

                final View customLayout
                        = getLayoutInflater()
                        .inflate(
                                R.layout.custom_dialog_blood_group,
                                null);

                dialogEditBloodGroup.setView(customLayout);

                AlertDialog dialog = dialogEditBloodGroup.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                ImageView a_plus = customLayout.findViewById(R.id.imageViewAplus);
                ImageView a_moins = customLayout.findViewById(R.id.imageViewAmoins);

                ImageView b_plus = customLayout.findViewById(R.id.imageViewBplus);
                ImageView b_moins = customLayout.findViewById(R.id.imageViewBmoins);

                ImageView O_plus = customLayout.findViewById(R.id.imageViewOplus);
                ImageView O_moins = customLayout.findViewById(R.id.imageViewOmoins);

                ImageView ab_plus = customLayout.findViewById(R.id.imageViewABplus);
                ImageView ab_moins = customLayout.findViewById(R.id.imageViewABmoins);

                a_plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        textViewBloodGroup.setText("A+");


                            User user = new User();
                            user.setUsername(name);
                            user.setTypeSang("A+");
                            userApi.updateUser(token,user)
                                    .enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            if (response.code() == 200) {
                                                Toast.makeText(getActivity(), "Blood Group successfully updated", Toast.LENGTH_LONG).show();
                                                dialog.dismiss();

                                            } else
                                                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
                                            dialog.dismiss();

                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();

                                        }
                                    });
                        }



                });
                a_moins.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        textViewBloodGroup.setText("A-");


                            User user = new User();
                            user.setUsername(name);
                            user.setTypeSang("A-");
                            userApi.updateUser(token,user)
                                    .enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            if (response.code() == 200) {
                                                Toast.makeText(getActivity(), "Blood Group successfully updated", Toast.LENGTH_LONG).show();
                                                dialog.dismiss();

                                            } else
                                                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
                                            dialog.dismiss();

                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();

                                        }
                                    });
                        }



                });

                b_plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        textViewBloodGroup.setText("B+");


                            User user = new User();
                            user.setUsername(name);
                            user.setTypeSang("B+");
                            userApi.updateUser(token,user)
                                    .enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            if (response.code() == 200) {
                                                Toast.makeText(getActivity(), "Blood Group successfully updated", Toast.LENGTH_LONG).show();
                                                dialog.dismiss();

                                            } else
                                                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
                                            dialog.dismiss();

                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();

                                        }
                                    });
                        }



                });
                b_moins.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textViewBloodGroup.setText("B-");


                            User user = new User();
                            user.setUsername(name);
                            user.setTypeSang("B-");
                            userApi.updateUser(token,user)
                                    .enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            if (response.code() == 200) {
                                                Toast.makeText(getActivity(), "Blood Group successfully updated", Toast.LENGTH_LONG).show();
                                                dialog.dismiss();

                                            } else
                                                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
                                                dialog.dismiss();

                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();

                                        }
                                    });
                        }



                });

                O_plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  String myUsername = editTextUsername.getText().toString();

                        textViewBloodGroup.setText("O+");


                            User user = new User();
                            user.setUsername(name);
                            user.setTypeSang("O+");
                            userApi.updateUser(token,user)
                                    .enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            if (response.code() == 200) {
                                                Toast.makeText(getActivity(), "Blood Group successfully updated", Toast.LENGTH_LONG).show();
                                                dialog.dismiss();

                                            } else
                                                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
                                            dialog.dismiss();

                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();

                                        }
                                    });
                        }



                });
                O_moins.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  String myUsername = editTextUsername.getText().toString();

                        textViewBloodGroup.setText("O-");


                            User user = new User();
                            user.setUsername(name);
                            user.setTypeSang("O-");
                            userApi.updateUser(token,user)
                                    .enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            if (response.code() == 200) {
                                                Toast.makeText(getActivity(), "Blood Group successfully updated", Toast.LENGTH_LONG).show();
                                                dialog.dismiss();

                                            } else
                                                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
                                            dialog.dismiss();

                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();

                                        }
                                    });
                        }



                });

                ab_plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        textViewBloodGroup.setText("AB+");


                            User user = new User();
                            user.setUsername(name);
                            user.setTypeSang("AB+");
                            userApi.updateUser(token,user)
                                    .enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            if (response.code() == 200) {
                                                Toast.makeText(getActivity(), "Blood Group successfully updated", Toast.LENGTH_LONG).show();
                                                dialog.dismiss();

                                            } else
                                                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
                                            dialog.dismiss();

                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();

                                        }
                                    });
                        }



                });
                ab_moins.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        textViewBloodGroup.setText("AB-");


                            User user = new User();
                            user.setUsername(name);
                            user.setTypeSang("AB-");
                            userApi.updateUser(token,user)
                                    .enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            if (response.code() == 200) {
                                                Toast.makeText(getActivity(), "Blood Group successfully updated", Toast.LENGTH_LONG).show();
                                                dialog.dismiss();

                                            } else
                                                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
                                            dialog.dismiss();

                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();

                                        }
                                    });
                        }



                });



            }

        });

    }

    private void getInfoUser(View view,API userApi, String name,String token) {




            Call<User> call = userApi.findUser(token,name);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    User getUser = response.body();

                    assert getUser != null;
                    String personName = getUser.getUsername();
                    email = getUser.getEmail();
                    String personGroupBlood = getUser.getTypeSang();
                    String personPhone = getUser.getPhone();
                    String personRegion = getUser.getRegion();

                    textViewName = view.findViewById(R.id.textViewName);
                    textViewEmail = view.findViewById(R.id.textViewEmail);
                    textViewPhone = view.findViewById(R.id.textViewPhone);
                    textViewBloodGroup = view.findViewById(R.id.textViewBloodGroup);
                    textViewRegion = view.findViewById(R.id.textViewRegion);
                    imageView = view.findViewById(R.id.profilePhoto);

                    textViewName.setText(personName);
                    textViewEmail.setText(email);
                    textViewPhone.setText(personPhone);
                    textViewRegion.setText(personRegion);
                    textViewBloodGroup.setText(personGroupBlood);


                    switch (personGroupBlood){
                        case "A+" : textViewBloodGroup.setText("A+"); break;
                        case "A-" : textViewBloodGroup.setText("A-"); break;

                        case "O+" : textViewBloodGroup.setText("O+"); break;
                        case "O-" : textViewBloodGroup.setText("O-");; break;

                        case "B+" : textViewBloodGroup.setText("B+"); break;
                        case "B-" : textViewBloodGroup.setText("B-"); break;

                        case "AB+" : textViewBloodGroup.setText("AB+\n"); break;
                        case "AB-" : textViewBloodGroup.setText("AB-\n"); break;

                        default:  textViewBloodGroup.setText("Add your blood type\n"); break;
                    }

                    if (getUser.getImage()!=null){
                        String personImage = getUser.getImage();

                        byte[] bytes = Base64.decode(personImage,Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

                        imageView.setImageBitmap(bitmap);
                    }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    // Log error here since request failed
                }
            });

    }

    private void imageChooser() {
        ImagePicker.with(this)
                .crop()
                .start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Uri selectedImageUri = data.getData();
        imageProfile.setImageURI(selectedImageUri);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            System.out.println(imageString);
            RetrofitClient retrofitClient = new RetrofitClient();
            API userApi = retrofitClient.getRetrofit().create(API.class);

            SharedPreferences preferences = getActivity().getSharedPreferences("AYACHNI", Context.MODE_PRIVATE);
            String retrievedToken = preferences.getString("TOKEN", null);
            String username = preferences.getString("USERNAME", null);
            String  token = "Bearer "+retrievedToken;

            User user = new User();
            user.setUsername(username);
            user.setImage(imageString);

            userApi.updateUser(token, user)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.code() == 200) {
                                Toast.makeText(getActivity(), "Profile photo successfully updated", Toast.LENGTH_LONG).show();
                            } else
                                Toast.makeText(getActivity(), "Something's wrong with this picture choose another one please", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getActivity(), "Request failed", Toast.LENGTH_LONG).show();
                        }
                    });
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
}