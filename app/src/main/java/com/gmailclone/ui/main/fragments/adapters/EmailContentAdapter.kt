package com.gmailclone.ui.main.fragments.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.gmailclone.R
import com.gmailclone.databinding.AdapterEmailContentBinding
import com.gmailclone.model.EmailContent

class EmailContentAdapter(
    private val onItemClickListener: ((emailContent: EmailContent) -> Unit)? = null
) : RecyclerView.Adapter<EmailContentAdapter.ViewHolder>() {

    private val items: MutableList<EmailContent> = mutableListOf()

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            AdapterEmailContentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount() = items.size

    @SuppressLint("notifyDataSetChanged")
    fun updateData(items: List<EmailContent>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun setupContext(context: Context) {
        this.context = context
    }


    inner class ViewHolder(private val binding: AdapterEmailContentBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind(item: EmailContent) = with(binding) {
            tvUserName.text = item.userName ?: item.recipient
            tvUserImage.text = (item.userName ?: item.recipient)[0].toString()
            tvSubject.text = item.subject
            tvMessage.text = item.body
            tvUserImage.backgroundTintList =
                ColorStateList.valueOf(item.userImage ?: Color.parseColor("#007d3a"))
            if (item.starred) btnStar.setBackgroundResource(R.drawable.ic_star_dark_blue)
        }

        init {
            with(binding) {
                tvUserImage.setOnClickListener(this@ViewHolder)
                tvUserName.setOnClickListener(this@ViewHolder)
                tvSubject.setOnClickListener(this@ViewHolder)
                tvMessage.setOnClickListener(this@ViewHolder)
                tvTime.setOnClickListener(this@ViewHolder)
                btnStar.setOnClickListener(this@ViewHolder)
            }
        }

        override fun onClick(v: View?) {
            when (v) {
                binding.btnStar -> Toast.makeText(context, "not working", Toast.LENGTH_SHORT)
                    .show()
                else -> items.getOrNull(absoluteAdapterPosition)
                    ?.let { onItemClickListener?.invoke(it) }
            }
        }
    }
}
