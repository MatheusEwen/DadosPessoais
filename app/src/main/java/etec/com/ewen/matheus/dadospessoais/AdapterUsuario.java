package etec.com.ewen.matheus.dadospessoais;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterUsuario extends RecyclerView.Adapter<AdapterUsuario.MyViewHolder> {

    private ArrayList<Usuario> mList;
    private Context context;

    public AdapterUsuario(Context context, ArrayList<Usuario> mList) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(context)
                .inflate(R.layout.usuario_card, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUsuario.MyViewHolder holder, int position) {
        Usuario usuario = mList.get(position);
        holder.nome.setText(usuario.getNome());
        holder.nascimento.setText(usuario.getNascimento());
        holder.email.setText(usuario.getEmail());
        holder.telefone.setText(usuario.getTelefone());
        holder.cidade.setText(usuario.getCidade());
        holder.logradouro.setText(usuario.getLogradouro());
        holder.tipo_logradouro.setText(usuario.getTipoLogradouro());
        holder.cep.setText(usuario.getCep());
        holder.bairro.setText(usuario.getBairro());
        holder.uf.setText(usuario.getUf());
        holder.btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:"+usuario.getEmail()));
                context.startActivity(emailIntent);
            }
        });
        holder.btnLigar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("tel:" + usuario.getTelefone());
                Intent ligar = new Intent(Intent.ACTION_DIAL,uri);
                context.startActivity(ligar);
            }
        });
        holder.btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String endereco = usuario.getCidade() + ", " + usuario.getBairro() + ", " + usuario.getLogradouro();
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + endereco);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }
        });





    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView nome;
        private TextView nascimento;
        private TextView email;
        private TextView telefone;
        private TextView cidade;
        private TextView logradouro;
        private TextView tipo_logradouro;
        private TextView cep;
        private TextView bairro;
        private TextView uf;
        private Button btnEmail;
        private Button btnLigar;
        private Button btnMaps;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.nomeCard);
            nascimento = itemView.findViewById(R.id.nascCard);
            email = itemView.findViewById(R.id.emailCard);
            telefone = itemView.findViewById(R.id.telefoneCard);
            cidade = itemView.findViewById(R.id.cidadeCard);
            logradouro = itemView.findViewById(R.id.logCard);
            tipo_logradouro = itemView.findViewById(R.id.tipoLogCard);
            cep = itemView.findViewById(R.id.cepCard);
            bairro = itemView.findViewById(R.id.bairroCard);
            uf = itemView.findViewById(R.id.ufCard);
            btnEmail = itemView.findViewById(R.id.btnEmail);
            btnLigar = itemView.findViewById(R.id.btnLigar);
            btnMaps = itemView.findViewById(R.id.btnMaps);

        }
    }


}
