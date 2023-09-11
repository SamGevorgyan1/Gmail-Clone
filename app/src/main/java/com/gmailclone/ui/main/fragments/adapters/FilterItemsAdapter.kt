package com.gmailclone.ui.main.fragments.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.gmailclone.databinding.BottomSheetDialogBinding
import com.gmailclone.databinding.ItemFilterBinding
import com.gmailclone.ui.main.fragments.FilterOption
import com.google.android.material.bottomsheet.BottomSheetDialog

class FilterItemsAdapter : RecyclerView.Adapter<FilterItemsAdapter.ViewHolder>() {

    private val items: MutableList<FilterOption> = mutableListOf()
    private lateinit var context: Context
    private var isColored = true;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setFilters(items: List<FilterOption>) {
        this.items.apply {
            clear()
            addAll(items)
        }
    }

    fun setItemColor(boolean: Boolean) {
        this.isColored = boolean
    }

    fun setContext(context: Context) {
        this.context = context
    }

    inner class ViewHolder(private val binding: ItemFilterBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind(item: FilterOption) {
            binding.tvItemTitle.text = item.name

            if (absoluteAdapterPosition == 0) {
                if (isColored) {
                    binding.itemBackground.backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor("#C3E7FF"))
                }
                binding.tvItemTitle.setTextColor(Color.BLACK)
            }
        }

        init {
            binding.tvItemTitle.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {

            when (items[absoluteAdapterPosition].name) {
                "From", "To" -> createBottomShitDialog(items[absoluteAdapterPosition].name)
            }
        }

        private fun createBottomShitDialog(bottomSheetTitle: String) {

            val dialog = BottomSheetDialog(context)
            val binding = BottomSheetDialogBinding.inflate(LayoutInflater.from(context))

            dialog.setContentView(binding.root)

            val displayMetrics = context.resources.displayMetrics
            val height = (displayMetrics.heightPixels * 0.75).toInt()
            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, height)

            binding.tvBottomSheet.text = bottomSheetTitle

            dialog.show()
        }
    }
}