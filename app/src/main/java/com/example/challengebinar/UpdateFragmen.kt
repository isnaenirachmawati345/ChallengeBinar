package com.example.challengebinar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.challengebinar.databinding.FragmentUpdateBinding
import com.example.challengebinar.room.DatabaseStorange
import com.example.challengebinar.room.Laundry
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import androidx.fragment.app.DialogFragment as DialogFragment1


class UpdateFragmen() : DialogFragment1() {
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
        dialog?.window?.attributes?.windowAnimations=R.style.DialogAnimation
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
            binding.etTanggalUpdate.setText(selectedlist.date.toString())
        }
        binding.btnUpdateBarang.setOnClickListener {
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
                    val alamat : String = binding.etAlamatUpdate.text.toString()
                    val jenis : String = binding.etJenisUpdate.text.toString()
                    val jumlah : Int = binding.etJumlahUpdate.text.toString().toInt()
                    val tanggal : Int = binding.etTanggalUpdate.text.toString().toInt()

                    val itemObject = selectedlist
                    itemObject.address=alamat
                    itemObject.jenis=jenis
                    itemObject.jumlah = jumlah
                    itemObject.date = tanggal.toString()

                    GlobalScope.async {
                        val result = myDbUp?.laundryDao()?.updateLaundry(itemObject)
                        runBlocking {
                            if (result != 0){
                                Toast.makeText(
                                    it.context,
                                    "Alamat ${selectedlist.address} sukses diubah", Toast.LENGTH_SHORT,
                                    "Jenis ${selectedlist.jenis} pesanan kamu sukses diubah", Toast.LENGTH_SHORT,
                                    "Jumlah ${selectedlist.jumlah} pesanan kamu sukses diubah", Toast.LENGTH_SHORT,
                                    "Tanggal ${selectedlist.date} pesanan kamu sukses diubah",Toast.LENGTH_SHORT
                                ).show()
                            }else {
                                Toast.makeText(it.context, "Data Gagal diubah", Toast.LENGTH_SHORT )
                                    .show()

                            }
                            activity?.finish()
                        }
                    }
                    dialog?.dismiss()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


}