package com.ashfaque.demogeminiroomkotlin

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import io.noties.markwon.Markwon

class MyAdapter(private val context: Context,private val dataList: MutableList<ChatModel>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataItem = dataList[position]
        holder.bind(dataItem)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardViewAI:CardView=itemView.findViewById(R.id.cardViewAI)
        private val cardViewUser:CardView=itemView.findViewById(R.id.cardViewUser)

        private val tvAIPrompt: TextView = itemView.findViewById(R.id.tvAIPrompt)
        private val tvAITime: TextView = itemView.findViewById(R.id.tvAITime)
        private val tvUserPrompt: TextView = itemView.findViewById(R.id.tvUserPrompt)
        private val tvUserTime: TextView = itemView.findViewById(R.id.tvUserTime)

        private val markwon = Markwon.create(context)
        private val maxLength = 500
        private val readMore="\n### Read more..."



        fun bind(dataItem: ChatModel) {

            if(dataItem.type==TypeClass.AI)
            {
                cardViewAI.visibility=View.VISIBLE
                cardViewUser.visibility=View.GONE

                if(dataItem.prompt.length>maxLength)
                {
                    val truncatedText = dataItem.prompt.substring(0, maxLength) +readMore
                    markwon.setMarkdown(tvAIPrompt,truncatedText)
                    tvAIPrompt.setOnClickListener {
                        markwon.setMarkdown(tvAIPrompt, dataItem.prompt)
                    }
                }else
                {
                    markwon.setMarkdown(tvAIPrompt, dataItem.prompt)
                }

                tvAITime.text=dataItem.dateTime

            }else
            {
                cardViewUser.visibility=View.VISIBLE
                cardViewAI.visibility=View.GONE

                if(dataItem.prompt.length>maxLength)
                {
                    val truncatedText = dataItem.prompt.substring(0, maxLength) + readMore
                    markwon.setMarkdown(tvUserPrompt,truncatedText)
                    tvUserPrompt.setOnClickListener {
                        markwon.setMarkdown(tvUserPrompt, dataItem.prompt)
                    }
                }else
                {
                    markwon.setMarkdown(tvUserPrompt, dataItem.prompt)
                }

                tvUserTime.text=dataItem.dateTime

            }
        }
    }

    fun addItem(item: ChatModel):Int {
        Log.d("Ashu",item.prompt)
        dataList.add(item)
        notifyItemInserted(dataList.size - 1) // Notify adapter about the new item insertion
        return dataList.size-1
    }


}
