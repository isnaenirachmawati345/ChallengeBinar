package com.example.challengebinar

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.challengebinar.adapter.AdapterList
import com.example.challengebinar.databinding.FragmentHomeMainBinding
import com.example.challengebinar.room.DatabaseStorange
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class Fragmen_Home_Main : Fragment() {
     private var mYdBm : DatabaseStorange? = null
     private  var _binding : FragmentHomeMainBinding?= null
    private val binding get() = _binding!!
    private lateinit var adapter : AdapterList
    private lateinit var preferences : SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeMainBinding.inflate(inflater,container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = requireContext().getSharedPreferences(Fragmentlogin.USERLOGIN, Context.MODE_PRIVATE)
        binding.tvWelcomeCustomer.text = "${preferences.getString(Fragmentlogin.EMAIL,null)}"

        mYdBm = DatabaseStorange.getInstance(requireContext())
        adapter = AdapterList()
        binding.rvList.adapter = adapter
        mYdBm = DatabaseStorange.getInstance(requireContext())
        fetchData()
        logout()
        binding.fabNewItem.setOnClickListener{
            findNavController().navigate(R.id.action_fragmen_Home_Main_to_fragmentAddListData)
        }
    }

    fun fetchData() {
        //untuk menampilkan data ke home main
        GlobalScope.launch {
            val itemlist = mYdBm?.laundryDao()?.getAllLaundry()
            runBlocking(Dispatchers.Main){
                itemlist?.let {
                    adapter.setData(it)
                }
            }
        }
    }

    private fun logout() {
        binding.tvLogout.setOnClickListener{
            val konfirmDialog = AlertDialog.Builder (requireContext())
            konfirmDialog.apply {
                setTitle("Logout")
                setMessage("Apa anda Ingin log out ?")
                setNegativeButton("Batal"){dialog,which->
                    dialog.dismiss()
                }
                setPositiveButton("Logout"){dialog,which->
                    dialog.dismiss()

                    preferences.edit().clear().apply()
                    findNavController().navigate(R.id.action_fragmen_Home_Main_to_fragmentlogin)
                }
            }
            konfirmDialog.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

}