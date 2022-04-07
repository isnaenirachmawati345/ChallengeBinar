package com.example.challengebinar

import android.content.ClipData
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.challengebinar.databinding.FragmenAddListDataBinding
import com.example.challengebinar.room.DatabaseStorange
import com.example.challengebinar.room.Laundry
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class FragmentAddListData : DialogFragment() {

    private var _binding: FragmenAddListDataBinding? = null
    private val binding get() = _binding!!
    var mYaFdB: DatabaseStorange? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_shape)
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        _binding = FragmenAddListDataBinding.inflate(inflater, container, false)
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
        mYaFdB = DatabaseStorange.getInstance(requireContext())
        binding.btnTambah.setOnClickListener {

            when {
                binding.tvNameAdd.text.isNullOrEmpty() -> {
                    binding.etAddOne.error = "Kamu perlu isi nama"
                }
                binding.tvAlamatAdd.text.isNullOrEmpty() -> {
                    binding.etAddTwo.error = "Kamu perlu isi alamat"
                }
                binding.tvJumlahAdd.text.isNullOrEmpty() -> {
                    binding.etAddThree.error = "Kamu perlu isi jumlah"
                }
                binding.tvTanggalAdd.text.isNullOrEmpty() -> {
                    binding.etAddFour.error = "Kamu perlu isi tanggal"
                }
                binding.tvJenisAdd.text.isNullOrEmpty() -> {
                    binding.etAddFive.error = "Kamu perlu isi jenis"
                }
                else -> {
                    val name: String = binding.tvNameAdd.text.toString()
                    val alamat: String = binding.tvAlamatAdd.text.toString()
                    val jumlah: Int = binding.tvJumlahAdd.text.toString().toInt()
                    val tanggal: Int = binding.tvTanggalAdd.text.toString().toInt()
                    val jenis: String = binding.tvJenisAdd.text.toString()
                    val obejctListItem = Laundry(
                        null, binding.tvNameAdd.text.toString(), name, alamat,
                        jumlah.toString(), tanggal, jenis
                    )
                    GlobalScope.async {
                        val result = mYaFdB?.laundryDao()?.insertLaundry(obejctListItem)
                        runBlocking {
                            if (result != 0.toLong()) {
                                Toast.makeText(
                                    requireContext(),
                                    "${obejctListItem.name} berhasil ditambahkan ke daftar",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Gagal menambahkan data ke daftar",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                    dialog?.dismiss()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

