package hadj.tn.test.SlideAnimation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import hadj.tn.test.R;
import hadj.tn.test.Sign_InActivity;

public class FragmentPage5 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fifth_activity, container, false);

        soup.neumorphism.NeumorphButton btnStarted = (soup.neumorphism.NeumorphButton) root.findViewById(R.id.getStarted);
        btnStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Sign_InActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return root;
    }
}
