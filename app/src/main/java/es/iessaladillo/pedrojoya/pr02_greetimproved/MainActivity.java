package es.iessaladillo.pedrojoya.pr02_greetimproved;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.FocusFinder;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.iessaladillo.pedrojoya.pr02_greetimproved.databinding.MainActivityBinding;

public class MainActivity extends AppCompatActivity {

    private MainActivityBinding binding;
    private boolean premium;
    private String pronoun = "Mr.";
    private boolean polite;
    private int contador = 0;
    private int charName = 0;
    private int charSurname = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.lblNameChar.setTextColor(getResources().getColor(R.color.colorAccent));
        image();
        isPremium();
        isPolite();
        show();
        showNameChar();
        showSurnameChar();
        contador();
        writeNameSurname();
    }

    private void isPremium(){
        binding.swtPremium.setOnClickListener(view -> {
            if(((CompoundButton)view).isChecked()){
                premium=true;
                visibility(8);
            }
            else{
                premium=false;
                visibility(0);
            }
            contador = 0;
        });
    }

    private void visibility(int visibility){
        if(visibility == 8){
            binding.pgbCounter.setVisibility(View.GONE);
            binding.lblCounter.setVisibility(View.GONE);
        }
        else if(visibility == 0){
            binding.pgbCounter.setVisibility(View.VISIBLE);
            binding.lblCounter.setVisibility(View.VISIBLE);
        }
    }

    private void isPolite(){
        binding.chkPolitely.setOnClickListener(view -> {
            if(((CompoundButton)view).isChecked()){
                polite=true;
            }
            else{
                polite=false;
            }
        });

    }

    private boolean nameEmpty(){
        String name = binding.txtName.getText().toString();
        return name.isEmpty();
    }

    private boolean surnameEmpty(){
        String surname = binding.txtSurname.getText().toString();
        return surname.isEmpty();
    }

    private void show(){
        binding.btnGreet.setOnClickListener(view -> {
            if(!nameEmpty() && !surnameEmpty()){
                if(contador == 10){
                    Toast.makeText(this, getString(R.string.premiunMessage), Toast.LENGTH_SHORT).show();
                }
                else {
                    if (polite) {
                        Toast.makeText(this, getString(R.string.politeMessage, pronoun, binding.txtName.getText().toString(), binding.txtSurname.getText().toString()), Toast.LENGTH_SHORT).show();
                        if(!premium){
                            sumarContador();
                        }
                        contador();
                    } else {
                        Toast.makeText(this, getString(R.string.notPoliteMessage, binding.txtName.getText().toString(), binding.txtSurname.getText().toString()), Toast.LENGTH_SHORT).show();
                        if(!premium){
                            sumarContador();
                        }
                        contador();
                    }
                }
            }
            else{
                if (nameEmpty()){
                    binding.txtName.setError(getString(R.string.errorMessage));

                }
                else{
                    binding.txtSurname.setError(getString(R.string.errorMessage));
                }
            }
        });
    }

    private void writeNameSurname(){
        binding.txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                showNameChar();
            }
        });

        binding.txtSurname.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                showSurnameChar();
            }
        });

        binding.txtName.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                binding.lblNameChar.setTextColor(getResources().getColor(R.color.colorAccent));
            } else {
                binding.lblNameChar.setTextColor(getResources().getColor(R.color.textPrimary));
            }
        });

        binding.txtSurname.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                binding.lblSurnameChar.setTextColor(getResources().getColor(R.color.colorAccent));
            } else {
                binding.lblSurnameChar.setTextColor(getResources().getColor(R.color.textPrimary));
            }
        });

    }

    private void sumarContador(){
        if(contador < 10){
            contador++;
        }
    }

    private void contador(){
        binding.pgbCounter.setProgress(contador);
        binding.lblCounter.setText(getString(R.string.contador ,contador, binding.pgbCounter.getMax()));
    }

    private void image(){
        binding.rdbTreatmentMr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.imgTreatment.setImageResource(R.drawable.ic_mr);
                pronoun = "Mr.";
            }
        });

        binding.rdbTreatmentMrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.imgTreatment.setImageResource(R.drawable.ic_mrs);
                pronoun = "Mrs.";
            }
        });

        binding.rdbTreatmentMs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.imgTreatment.setImageResource(R.drawable.ic_ms);
                pronoun = "Ms.";
            }
        });

    }

    private void showNameChar(){
        charName = binding.txtName.getText().length();
        binding.lblNameChar.setText(getResources().getQuantityString(
                R.plurals.charNumber,(getResources().getInteger(R.integer.charLimit)-charName),
                (getResources().getInteger(R.integer.charLimit)-charName)));
    }

    private void showSurnameChar(){
        charSurname = binding.txtSurname.getText().length();
        binding.lblSurnameChar.setText(getResources().getQuantityString(
                R.plurals.charNumber,(getResources().getInteger(R.integer.charLimit)-charSurname),
                (getResources().getInteger(R.integer.charLimit)-charSurname)));
    }

}