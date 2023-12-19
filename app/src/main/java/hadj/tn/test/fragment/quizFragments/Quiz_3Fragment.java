package hadj.tn.test.fragment.quizFragments;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import hadj.tn.test.R;
import hadj.tn.test.fragment.leftMenuFragments.ProfileFragment;

public class Quiz_3Fragment extends Fragment {

    Button yes, no;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);






        no = view.findViewById(R.id.btn22);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container,new Quiz_4Fragment()).commit();



            }
        });
        yes = view.findViewById(R.id.btn11);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogEditPhone = new AlertDialog.Builder(getActivity());

                final View customLayout
                        = getLayoutInflater()
                        .inflate(
                                R.layout.age_restriction,
                                null);
                dialogEditPhone.setView(customLayout);
                AlertDialog dialog = dialogEditPhone.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                Button ok = customLayout.findViewById(R.id.ok);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        //fragmentTransaction.replace(R.id.container,new pfragment()).commit();
                        //Intent intent = new Intent(Quiz_2.this, HomeActivity.class );
                        //startActivity(intent);
                        // no.setVisibility(View.GONE);
                        //yes.setVisibility(View.GONE);
                        dialog.cancel();
                        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container,new ProfileFragment()).commit();

                    }
                });
            }
        });
    }
}