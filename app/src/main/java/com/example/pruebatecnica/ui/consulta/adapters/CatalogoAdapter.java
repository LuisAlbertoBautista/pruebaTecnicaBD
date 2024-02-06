package com.example.pruebatecnica.ui.consulta.adapters;

import static com.example.pruebatecnica.R.id.siRadioButton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pruebatecnica.R;
import com.example.pruebatecnica.ui.consulta.interfaces.EventosClick;
import com.example.pruebatecnica.ui.consulta.models.ItemCatalogo;

import org.json.JSONException;
import java.util.List;

public class CatalogoAdapter extends RecyclerView.Adapter<CatalogoAdapter.ViewHolder> {

    private List<ItemCatalogo> items;
    private Context context;

    private EventosClick.ClickElementInterface clickElementInterface;

    public CatalogoAdapter(Context context, List<ItemCatalogo> items) {
        this.context = context;
        this.items = items;
        EventosClick.ClickElementInterface clickElementInterface = (EventosClick.ClickElementInterface) context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_abastecimiento, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemCatalogo item = items.get(position);

        holder.nombreItemTextView.setText(item.getNombre());

        holder.opcionesRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            String option = "";
            if (siRadioButton == checkedId){
                option = "SI";
            }else if (R.id.naRadioButton == checkedId){
                option = "NO";
            }else {
                option="N/A";
            }
            String opcionSeleccionada = option;
            item.setOpcionSeleccionada(opcionSeleccionada);
            try {
                clickElementInterface.clickElementInterface(option, item);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });

        holder.cameraImageView.setOnClickListener(v -> {
            try {
                clickElementInterface.clickCamera();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });

        Glide.with(context)
                .load(item.getRutaImagen())
                .into(holder.cameraImageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void actualizarLista(List<ItemCatalogo> nuevaLista) {
        this.items = nuevaLista;
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombreItemTextView;
        RadioGroup opcionesRadioGroup;
        ImageView cameraImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            nombreItemTextView = itemView.findViewById(R.id.nombreItemTextView);
            opcionesRadioGroup = itemView.findViewById(R.id.opcionesRadioGroup);
            cameraImageView = itemView.findViewById(R.id.cameraImageView);
        }
    }
}
