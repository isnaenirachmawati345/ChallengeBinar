package com.example.challengebinar.adapter

import android.content.ClipData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.challengebinar.MainActivity
import com.example.challengebinar.UpdateFragmen
import com.example.challengebinar.databinding.FragmentListDataPengunjungBinding
import com.example.challengebinar.room.Laundry

class AdapterList : RecyclerView.Adapter<AdapterList.ViewHolder>() {
    private val listDaf = mutableListOf<Laundry>()

    class ViewHolder(val binding: FragmentListDataPengunjungBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentListDataPengunjungBinding.inflate(LayoutInflater.from(parent.context), parent, false )
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            tvNamaPelanggan.text = listDaf[position].name
            tvTanggalPesanan.text = listDaf[position].date
            tvJenis.text = listDaf[position].jenis
            tvJumlahPesanan.text = listDaf[position].jumlah.toString()

            itemParent.setOnClickListener {
                val activity = it.context as MainActivity
                val fragmendialog = UpdateFragmen(listDaf[position])
               fragmendialog.show(activity.supportFragmentManager, null)
            }
        }
    }


    override fun getItemCount(): Int = listDaf.size
        fun setData(itemList : List<Laundry>){
            listDaf.clear()
            listDaf.addAll(itemList)
            notifyDataSetChanged()
        }
    }

