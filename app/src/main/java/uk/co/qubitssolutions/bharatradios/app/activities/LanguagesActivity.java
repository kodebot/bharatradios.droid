package uk.co.qubitssolutions.bharatradios.app.activities;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import uk.co.qubitssolutions.bharatradios.R;
import uk.co.qubitssolutions.bharatradios.app.BharatRadiosApplication;
import uk.co.qubitssolutions.bharatradios.app.adapters.LanguageListAdapter;
import uk.co.qubitssolutions.bharatradios.databinding.ActivityLanguagesBinding;

public class LanguagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLanguagesBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_languages);
        LanguageListAdapter adapter = new LanguageListAdapter((BharatRadiosApplication) getApplication());

        binding.languageListRecyclerView.setAdapter(adapter);
        binding.languageListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
