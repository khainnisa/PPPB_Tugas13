package com.example.tugaspertemuan13

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tugaspertemuan13.databinding.ItemPemilihBinding

class PemilihAdapter(
    private val voterList: List<Pemilih>,
    private val onEditClick: (Pemilih) -> Unit,
    private val onDetailClick: (Pemilih) -> Unit
) : RecyclerView.Adapter<PemilihAdapter.PemilihViewHolder>() {

    inner class PemilihViewHolder(val binding: ItemPemilihBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PemilihViewHolder {
        val binding = ItemPemilihBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PemilihViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PemilihViewHolder, position: Int) {
        val voter = voterList[position]
        holder.binding.pemilihNameTextView.text = voter.name

        holder.binding.editButton.setOnClickListener {
            onEditClick(voter)
        }

        holder.binding.detailButton.setOnClickListener {
            onDetailClick(voter)
        }
    }

    override fun getItemCount(): Int = voterList.size
}
