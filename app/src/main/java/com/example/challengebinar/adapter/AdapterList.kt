package com.example.challengebinar.adapter

import android.app.AlertDialog
import android.content.ClipData
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Delete
import com.example.challengebinar.Fragmen_Home_Main
import com.example.challengebinar.MainActivity
import com.example.challengebinar.UpdateFragmen
import com.example.challengebinar.databinding.FragmentHomeMainBinding
import com.example.challengebinar.databinding.FragmentListDataPengunjungBinding
import com.example.challengebinar.room.DatabaseStorange
import com.example.challengebinar.room.Laundry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

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
            btnDelete.setOnClickListener{
                val activity = it.context as  MainActivity
                AlertDialog.Builder(activity).setPositiveButton("Ya"){
                     p0,p1 -> val mDb = DatabaseStorange.getInstance(holder.itemView.context)
                     GlobalScope.async {
                            val result = mDb?.laundryDao()?.deleteLaundry(listDaf[position])
                        runBlocking(Dispatchers.Main){
                             if (result !=0){
                                 Fragmen_Home_Main().fetchData()
                                 Toast.makeText(it.context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                             }else{
                                 Toast.makeText(it.context, "Data gagal dihapus", Toast.LENGTH_SHORT).show()
                             }
                         }
                     }
                }.setNegativeButton("Batal"){
                    dialog,_ -> dialog.dismiss()
                }.setTitle("Hapus").setMessage("Itu yakin mau hapus ?").show()
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

