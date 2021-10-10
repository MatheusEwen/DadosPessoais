package etec.com.ewen.matheus.dadospessoais;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btnLimpar, btnCadastrar, btnConsultar;
    EditText edtNome, edtCep, edtNascimento, edtEmail, edtTelefone;
    String cep;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnLimpar = findViewById(R.id.btnLimpar);
        btnConsultar = findViewById(R.id.btnConsultar);
        edtNome = findViewById(R.id.edtNome);
        edtCep = findViewById(R.id.edtCep);
        edtEmail = findViewById(R.id.edtEmail);
        edtNascimento = findViewById(R.id.edtNascimento);
        edtTelefone = findViewById(R.id.edtTelefone);

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtCep.setText("");
                edtEmail.setText("");
                edtNome.setText("");
                edtNascimento.setText("");
                edtTelefone.setText("");
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cadastrar();
            }
        });

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Consultar.class);
                startActivity(intent);
            }
        });


    }

    private void Cadastrar() {
        Usuario usuario = new Usuario();

        String txtNome = edtNome.getText().toString();
        String txtEmail = edtEmail.getText().toString();
        String txtNascimento = edtNascimento.getText().toString();
        String txtTelefone = edtTelefone.getText().toString();
        String txtCep = edtCep.getText().toString();
        cep = edtCep.getText().toString();
        String endereco =  "http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=json";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest strReq = new StringRequest(Request.Method.GET, endereco,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject rua = null;
                        try {
                            rua = new JSONObject(response);
                            usuario.setLogradouro(rua.getString("logradouro"));
                            usuario.setTipoLogradouro(rua.getString("tipo_logradouro"));
                            usuario.setBairro(rua.getString("bairro"));
                            usuario.setCidade(rua.getString("cidade"));
                            usuario.setUf(rua.getString("uf"));
                            usuario.setNome(txtNome);
                            usuario.setEmail(txtEmail);
                            usuario.setNascimento(txtNascimento);
                            usuario.setTelefone(txtTelefone);
                            usuario.setCep(txtCep);
                            db.child("usuarios").push().setValue(usuario);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        queue.add(strReq);



    }


}