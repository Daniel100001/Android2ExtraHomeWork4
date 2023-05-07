package com.example.android2homework2.ui.fragments.note.detail

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.android2homework2.App
import com.example.android2homework2.R
import com.example.android2homework2.databinding.FragmentNoteAppDetailBinding
import com.example.android2homework2.models.NoteModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class NoteAppDetailFragment : Fragment() {

    private lateinit var binding: FragmentNoteAppDetailBinding
    private val timeNow = SimpleDateFormat("HH:mm", Locale.getDefault())
        .format(System.currentTimeMillis())
    private val dataNow = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        .format(System.currentTimeMillis())
    private val todayData = SimpleDateFormat("dd", Locale.getDefault())
        .format(System.currentTimeMillis())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteAppDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListener()
        checkRadioButton()
        danchik()
    }

    private fun checkRadioButton() {
        binding.buttonBlack.setOnClickListener {
            binding.radioButton1.isChecked = true
            binding.radioButton2.isChecked = false
            binding.radioButton3.isChecked = false
        }

        binding.buttonWhite.setOnClickListener {
            binding.radioButton1.isChecked = false
            binding.radioButton2.isChecked = true
            binding.radioButton3.isChecked = false
        }

        binding.button.setOnClickListener {
            binding.radioButton1.isChecked = false
            binding.radioButton2.isChecked = false
            binding.radioButton3.isChecked = true
        }
    }
    private fun danchik() {
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val color = when (checkedId) {
                R.id.radioButton1 -> Color.parseColor("#FFC107") // yellow
                R.id.radioButton2 -> Color.parseColor("#4CAF50") // green
                R.id.radioButton3 -> Color.parseColor("#2196F3") // blue
                else -> Color.TRANSPARENT // no color
            }
            // сохраняем выбранный цвет в SharedPreferences
            val editor = requireActivity().getSharedPreferences("NoteColor", Context.MODE_PRIVATE).edit()
            editor.putInt("color", color)
            editor.apply()
        }
    }

    private fun setUpListener() = with(binding) {
        textBack.setOnClickListener {

            val title = editTextFragmentDetail.text.toString()
            val description = editTextDescriptionFragmentDetail.text.toString()
            val sharedPreferences = requireActivity().getSharedPreferences("NoteColor", Context.MODE_PRIVATE)
            val color = sharedPreferences.getInt("color", Color.TRANSPARENT)

            App.getInstate()?.noteDao()?.insert(
                NoteModel(title, description, timeNow, dataNow,color)
            )
            findNavController().navigateUp()
        }

        binding.day.text = todayData
        binding.time.text = timeNow

        binding.arrowLeft.setOnClickListener{
            findNavController().navigateUp()
        }
    }


}