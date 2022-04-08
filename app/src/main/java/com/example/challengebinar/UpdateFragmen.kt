package com.example.challengebinar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.challengebinar.databinding.FragmentUpdateBinding
import com.example.challengebinar.room.DatabaseStorange
import com.example.challengebinar.room.Laundry
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking



class UpdateFragmen() : DialogFragment() {
    private var _binding : FragmentUpdateBinding?=null
    private val binding get() = _binding!!
    lateinit var selectedlist : Laundry
    constructor(seletedList: Laundry):this(){
        this.selectedlist=seletedList
    }
    var myDbUp : DatabaseStorange?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       dialog?.window?.setBackgroundDrawableResource(R.drawable.back_dialog)
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        _binding= FragmentUpdateBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myDbUp = DatabaseStorange.getInstance(requireContext())

        if(this :: selectedlist.isInitialized){
            binding.tvItemName.text=" Update Data ${selectedlist.name}"
            binding.etAlamatUpdate.setText(selectedlist.address.toString())
            binding.etJenisUpdate.setText(selectedlist.jenis.toString())
            binding.etJumlahUpdate.setText(selectedlist.jumlah.toString())
            binding.etTanggalUpdate.setText(selectedlist.date)
        }
        binding.btnUpdateBarang.setOnClickListener {
            val name = binding.etNameUpdate.text.toString()
            val alamat = binding.etAlamatUpdate.text.toString()
            val jenis = binding.etJenisUpdate.text.toString()
            val jumlah = binding.etJumlahUpdate.text.toString()
            val tanggal = binding.etTanggalUpdate.text.toString()
            when {
                binding.etAlamatUpdate.text.isNullOrEmpty()->{
                    binding.wrapTvAlamatUpdate.error = "Kamu perlu memasukan alamat kembali"
                }
                binding.etJenisUpdate.text.isNullOrEmpty()->{
                    binding.wrapTvJenisUpdate.error ="Kamu perlu memasukan jenis pesanan kembali"
                }
                binding.etJumlahUpdate.text.isNullOrEmpty()->{
                    binding.wrapTvJumlahUpdate.error="Kamu perlu memasukan jumlah pesanan kembali"
                }
                binding.etTanggalUpdate.text.isNullOrEmpty()->{
                    binding.wrapTvTanggalUpdate.error="Kamu perlu memasukan tanggal pesanan kembali"
                }
                else -> {
                    val itemObject = selectedlist
                    itemObject.name = name
                    itemObject.address=alamat
                    itemObject.jenis=jenis
                    itemObject.jumlah = jumlah
                    itemObject.date = tanggal

                    GlobalScope.async {
                        val result = myDbUp?.laundryDao()?.updateLaundry(itemObject)
                        runBlocking {
                            if (result != 0){
                                Toast.makeText(
                                    it.context,
                                    "sukses diubah", Toast.LENGTH_SHORT,
                                ).show()
                            }else {
                                Toast.makeText(it.context, "Data tidak berhasil diinputkan", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    dialog?.dismiss()
                }
            }
        }
    }
    override fun onDestroy(){
        super.onDestroy()
        _binding=null
    }


}