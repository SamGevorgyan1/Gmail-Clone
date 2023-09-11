package com.gmailclone.ui.main.fragments.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.gmailclone.R
import com.gmailclone.databinding.AdapterEmailContentBinding
import com.gmailclone.model.EmailContent

class SearchItemAdapter : RecyclerView.Adapter<SearchItemAdapter.ViewHolder>(), Filterable {

    private val items: MutableList<EmailContent> = mutableListOf()
    private var filteredItems: MutableList<EmailContent> = mutableListOf()
    private lateinit var context: Context
    private lateinit var recyclerView: RecyclerView
    private var searchQuery: String = ""

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
        holder.bind(filteredItems[position], searchQuery)

    override fun getItemCount() = filteredItems.size

    fun updateData(items: List<EmailContent>) {
        this.items.clear()
        this.items.addAll(items)
        filter.filter(searchQuery)
    }

    fun setupContext(context: Context) {
        this.context = context
    }

    fun setupRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.trim() ?: ""
                val results = FilterResults()
                if (query.isEmpty()) {
                    results.values = mutableListOf<EmailContent>()
                } else {
                    val filteredList = items.filter { item ->
                        item.userName?.contains(query, ignoreCase = true) == true ||
                                item.recipient.contains(query, ignoreCase = true) ||
                                item.subject.substring(0, 25).contains(query, ignoreCase = true) ||
                                item.body.substring(0, 25).contains(query, ignoreCase = true)
                    }
                    results.values = filteredList
                }
                searchQuery = query
                return results
            }

            @SuppressLint("notifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults) {
                @Suppress("UNCHECKED_CAST")
                filteredItems = results.values as MutableList<EmailContent>

                if (filteredItems.isEmpty()) recyclerView.visibility = View.GONE
                else recyclerView.visibility = View.VISIBLE

                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(private val binding: AdapterEmailContentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind(item: EmailContent, searchQuery: String) = with(binding) {
            tvUserName.text = highlightSearchQuery(item.userName ?: item.recipient, searchQuery)
            tvUserImage.text = (item.userName ?: item.recipient)[0].toString()
            tvSubject.text = highlightSearchQuery(item.subject, searchQuery)
            tvMessage.text = highlightSearchQuery(item.body, searchQuery)
            tvUserImage.backgroundTintList =
                ColorStateList.valueOf(item.userImage ?: Color.parseColor("#007d3a"))
            if (item.starred) btnStar.setBackgroundResource(R.drawable.ic_star_dark_blue)
        }

        private fun highlightSearchQuery(fullText: String, query: String): CharSequence {
            val spannable = SpannableString(fullText)
            val words = fullText.split(" ")

            val startIndex = fullText.indexOf(query, ignoreCase = true)
            if (startIndex != -1) {
                val endIndex = startIndex + query.length
                spannable.setSpan(
                    ForegroundColorSpan(Color.parseColor("#FDD835")),
                    startIndex,
                    endIndex,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            return spannable
        }
    }
}